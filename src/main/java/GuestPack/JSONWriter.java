package GuestPack;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class JSONWriter {
    public static ObjectMapper objectMapper = new ObjectMapper();
    public static void objectMapperSetup()
    {
        SimpleModule module = new SimpleModule();

        // Настройка сериализатора для типа LocalTime
        JsonSerializer<LocalTime> localTimeSerializer = new JsonSerializer<LocalTime>() {
            @Override
            public void serialize(LocalTime localTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                String formattedTime = localTime.format(DateTimeFormatter.ofPattern("HH:mm"));
                jsonGenerator.writeString(formattedTime);
            }
        };
        module.addSerializer(LocalTime.class, localTimeSerializer);

        // Настройка десериализатора для типа LocalTime
        JsonDeserializer<LocalTime> localTimeDeserializer = new JsonDeserializer<LocalTime>() {
            @Override
            public LocalTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException{
                String timeString = jsonParser.getValueAsString();
                return LocalTime.parse(timeString, DateTimeFormatter.ofPattern("HH:mm"));
            }
        };
        module.addDeserializer(LocalTime.class, localTimeDeserializer);

        //ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        objectMapper.registerModule(module);
    }
    public static void writeJSON(List<Guest> guestList)
    {
        try {
            objectMapper.writeValue(new File("guests.json"), guestList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Guest> newGuestList = null;
        try {
            newGuestList = objectMapper.readValue(new File("guests.json"), new TypeReference<List<Guest>>(){});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Guest guest : newGuestList) {
            guest.print();
            System.out.println();
        }

        System.out.println("________________________________________");
        // Гости с заданной датой рождения
        LocalDate targetDate = LocalDate.of(1999, 1, 1);
        List<Guest> guestsWithBirthday = newGuestList.stream()
                .filter(guest -> guest.getPassport().getBirthDate().getDayOfMonth() == targetDate.getDayOfMonth()
                        && guest.getPassport().getBirthDate().getMonth() == targetDate.getMonth())
                .collect(Collectors.toList());

        for(Guest guest : guestsWithBirthday)
        {
            guest.shortPrint();
        }

        System.out.println("________________________________________");
        int startYear = 1990;
        int endYear = 2001;
        List<Guest> guestsByYear = newGuestList.stream()
                .filter(guest -> guest.getPassport().getBirthDate().getYear() == startYear
                        || (guest.getPassport().getBirthDate().getYear() >= startYear
                        && guest.getPassport().getBirthDate().getYear() <= endYear))
                .collect(Collectors.toList());

        for(Guest guest : guestsWithBirthday)
        {
            guest.shortPrint();
            System.out.println(guest.getPassport().getBirthDate());
        }

        System.out.println("________________________________________");
        LocalDate targetDate1 = LocalDate.of(2023, 5, 29); // Заданный день
        List<Guest> guestsByAccessDate = newGuestList.stream()
                .filter(guest -> targetDate1.isAfter(guest.getAccessPeriod().getStart().toLocalDate())
                        && targetDate1.isBefore(guest.getAccessPeriod().getEnd().toLocalDate()))
                .collect(Collectors.toList());

        for(Guest guest : guestsWithBirthday)
        {
            guest.shortPrint();
            guest.getAccessPeriod().print();
        }
    }
}

class LocalDateSerializer extends StdSerializer<LocalDate> {
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public LocalDateSerializer() {
        this(null);
    }
    public LocalDateSerializer(Class<LocalDate> t) {
        super(t);
    }
    @Override
    public void serialize(LocalDate value, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        generator.writeString(dtf.format(value));
    }
}

class LocalDateTimeSerializer extends StdSerializer<LocalDateTime> {
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
    public LocalDateTimeSerializer() {
        this(null);
    }
    public LocalDateTimeSerializer(Class<LocalDateTime> t) {
        super(t);
    }
    @Override
    public void serialize(LocalDateTime value, JsonGenerator generator, SerializerProvider provider)
            throws IOException {
        generator.writeString(dtf.format(value));
    }
}
/*
class LocalDateDeserializer extends StdDeserializer<LocalDate> {
    protected LocalDateDeserializer() {
        super(LocalDate.class);
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        return LocalDate.parse(parser.readValueAs(String.class));
    }
}
*/
