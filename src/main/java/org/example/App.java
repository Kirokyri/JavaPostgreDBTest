package org.example;

import GuestPack.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;

import java.sql.*;
import java.time.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DatabaseConnector {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/JavaDB1";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASSWORD = "1234";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
}

class DatabaseInitializer {
    public static void dropTables()
    {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String dropTableQuery = "DROP TABLE IF EXISTS guest CASCADE;" +
                    "DROP TABLE IF EXISTS passport CASCADE;" +
                    "DROP TABLE IF EXISTS accessperiod CASCADE;" +
                    "DROP TABLE IF EXISTS accesslist CASCADE;";
            statement.executeUpdate(dropTableQuery);
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }
    public static void createTables()
    {
        DatabaseInitializer.createTableAccessPeriod();
        DatabaseInitializer.createTablePassport();
        DatabaseInitializer.createTableAccessList();
        DatabaseInitializer.createTableGuest();
    }
    public static void createTablePassport() {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Passport (" +
                    "id SERIAL PRIMARY KEY," +
                    "name VARCHAR(255) NOT NULL," +
                    "surname VARCHAR(255) NOT NULL," +
                    "patronymic VARCHAR(255) NOT NULL," +
                    "number VARCHAR(255) NOT NULL," +
                    "code VARCHAR(255) NOT NULL," +
                    "birthDate DATE NOT NULL," +
                    "birthPlace VARCHAR(255) NOT NULL," +
                    "issueDate DATE NOT NULL)";
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createTableAccessPeriod() {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS AccessPeriod (" +
                    "id SERIAL PRIMARY KEY," +
                    "start VARCHAR(255)," +
                    "\"end\" VARCHAR(255))";
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createTableAccessList() {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS AccessList (" +
                    "id SERIAL PRIMARY KEY," +
                    "accessList JSONB NOT NULL)";
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void createTableGuest() {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Guest (" +
                    "id SERIAL PRIMARY KEY," +
                    "accessPeriodId INTEGER REFERENCES AccessPeriod(id)," +
                    "accessListId INTEGER REFERENCES AccessList(id)," +
                    "passNumber VARCHAR(255)," +
                    "passportId INTEGER REFERENCES Passport(id))";
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
class DatabaseFiller {
    public static void addPassport(Passport passport, Integer id)
    {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()){
            String query = "INSERT INTO passport (id, name, surname, patronymic, " +
                    "number, code, birthDate, birthPlace, issueDate) " +
                    "VALUES " +
                    "('" + id + "','" + passport.getName() + "','" + passport.getSurname() + "','" +
                    passport.getPatronymic() + "','" + passport.getNumber() + "','" +
                    passport.getCode() + "','" + passport.getBirthDate() + "','" +
                    passport.getBirthPlace() + "','" + passport.getIssueDate() + "')";
            statement.executeUpdate(query);
            //System.out.println(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addAccessPeriod(AccessPeriod accessPeriod, Integer id)
    {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()){
            String query = "INSERT INTO accessperiod (id, start, \"end\") " +
                    "VALUES " +
                    "('" + id + "','" + accessPeriod.getStart() + "','" +
                    accessPeriod.getEnd() + "')";
            statement.executeUpdate(query);
            //System.out.println(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addAccessList(AccessList accessList, Integer id)
    {
        ObjectMapper objectMapper = JSONWriter.objectMapper;
        try (Connection connection = DatabaseConnector.getConnection();
             PreparedStatement preparedStatement  = connection.prepareStatement(
                     "INSERT INTO accesslist (id, accesslist) " +
                             "VALUES (?, ?)"
             )){
            String jsonString = "";
            try{
                jsonString = objectMapper.writeValueAsString(accessList.accessList);
            }catch (JsonProcessingException e)
            {
                e.printStackTrace();
            }
            PGobject jsonObject = new PGobject();
            jsonObject.setType("json");
            jsonObject.setValue(jsonString);
            preparedStatement.setInt(1, id);
            preparedStatement.setObject(2, jsonObject);
            preparedStatement.executeUpdate();
            //System.out.println(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addGuest(Guest guest, Integer id)
    {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()){
            String query = "INSERT INTO guest (id, accessperiodid, " +
                    "accesslistid, passportid, passnumber) " +
                    "VALUES " +
                    "('" + id + "','" + id + "','" + id + "','" + id + "','" +
                    guest.getPassNumber() + "')";
            statement.executeUpdate(query);
            //System.out.println(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class DatabaseQueries{
    public static void guestsWithExactBirthday(Integer day, Integer month)
    {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT Guest.id as gid, " +
                    "Guest.passnumber as ps, passport.name as name, " +
                    "passport.surname as surname," +
                    "passport.birthdate as bd " +
                    "FROM guest, passport " +
                    "WHERE guest.passportid = passport.id " +
                    "AND EXTRACT(DAY from passport.birthdate) = " + day.toString() + " " +
                    "AND EXTRACT(MONTH from passport.birthdate) = " + month.toString();
            ResultSet rs = statement.executeQuery(query);
            System.out.println("_____________________________________________________________________________");
            System.out.println("Гости с днем рождения в заданный день.");
            while(rs.next())
            {
                String id = rs.getString("gid");
                String passnumber = rs.getString("ps");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                LocalDate birthdate = rs.getDate("bd").toLocalDate();
                System.out.println("ID: " + id + " Passnumber: " + passnumber);
                System.out.println(name + " " + surname);
                System.out.println(birthdate);
                System.out.println("______________");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void guestsBornExactYear(Integer startYear, Integer endYear)
    {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT Guest.id as gid, " +
                    "Guest.passnumber as ps, passport.name as name, " +
                    "passport.surname as surname," +
                    "passport.birthdate as bd " +
                    "FROM guest, passport " +
                    "WHERE guest.passportid = passport.id " +
                    "AND (EXTRACT(YEAR from passport.birthdate) = " + startYear.toString() + " " +
                    "OR EXTRACT(YEAR from passport.birthdate) BETWEEN " + startYear.toString() + " AND " +
                    endYear.toString() + ")";
            ResultSet rs = statement.executeQuery(query);
            System.out.println("_____________________________________________________________________________");
            System.out.println("Гости, рожденные в заданный год или диапазон лет.");
            while(rs.next())
            {
                String id = rs.getString("gid");
                String passnumber = rs.getString("ps");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                LocalDate birthdate = rs.getDate("bd").toLocalDate();
                System.out.println("ID: " + id + " Passnumber: " + passnumber);
                System.out.println(name + " " + surname);
                System.out.println(birthdate);
                System.out.println("______________");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void guestsWithAccessAtExactDate(String date)
    {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT Guest.id as gid, " +
                    "Guest.passnumber as ps, passport.name as name, " +
                    "passport.surname as surname," +
                    "accessperiod.start as st, " +
                    "accessperiod.end as ed " +
                    "FROM guest, passport, accessperiod " +
                    "WHERE guest.passportid = passport.id " +
                    "AND guest.accessperiodid = accessperiod.id " +
                    "AND '" + date + "'::date >= accessperiod.start::date " +
                    "AND '" + date + "'::date <= accessperiod.end::date";
            //System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            System.out.println("_____________________________________________________________________________");
            System.out.println("Гости, имеющие право посещения в конкретный день.");
            while(rs.next())
            {
                String id = rs.getString("gid");
                String passnumber = rs.getString("ps");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String start = rs.getString("st");
                String end = rs.getString("ed");
                System.out.println("ID: " + id + " Passnumber: " + passnumber);
                System.out.println(name + " " + surname);
                System.out.println(start + " - " + end);
                System.out.println("______________");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void guestsFromExactCity(String city)
    {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT Guest.id as gid, " +
                    "Guest.passnumber as ps, passport.name as name, " +
                    "passport.surname as surname, " +
                    "passport.birthplace as bt " +
                    "FROM guest, passport " +
                    "WHERE guest.passportid = passport.id " +
                    "AND passport.birthplace = '" + city + "'";
            //System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            System.out.println("_____________________________________________________________________________");
            System.out.println("Гости, рожденные в заданном городе.");
            while(rs.next())
            {
                String id = rs.getString("gid");
                String passnumber = rs.getString("ps");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String place = rs.getString("bt");
                System.out.println("ID: " + id + " Passnumber: " + passnumber);
                System.out.println(name + " " + surname);
                System.out.println(place);
                System.out.println("______________");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void guestsVIP()
    {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT Guest.id as gid, " +
                    "Guest.passnumber as ps, passport.name as name, " +
                    "passport.surname as surname, " +
                    "accesslist.accesslist as al " +
                    "FROM guest, passport, accesslist " +
                    "WHERE guest.passportid = passport.id " +
                    "AND guest.accesslistid = accesslist.id";
            //System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            System.out.println("_____________________________________________________________________________");
            System.out.println("ВИП гости.");
            ObjectMapper objectMapper = JSONWriter.objectMapper;
            while(rs.next())
            {
                String id = rs.getString("gid");
                String passnumber = rs.getString("ps");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                PGobject jsonObject = rs.getObject("al", PGobject.class);
                Map<String, List<Schedule>> accessMap = objectMapper.readValue(jsonObject.toString(), new TypeReference<Map<String, List<Schedule>>>(){});
                AccessList accessList = new AccessList(accessMap);
                //accessList.print();
                if(accessList.equals(GuestListFiller.VIPAccessList))
                {
                    System.out.println("ID: " + id + " Passnumber: " + passnumber);
                    System.out.println(name + " " + surname);
                    System.out.println("______________");
                }
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void guestsStandard()
    {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT Guest.id as gid, " +
                    "Guest.passnumber as ps, passport.name as name, " +
                    "passport.surname as surname, " +
                    "accesslist.accesslist as al " +
                    "FROM guest, passport, accesslist " +
                    "WHERE guest.passportid = passport.id " +
                    "AND guest.accesslistid = accesslist.id";
            //System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            System.out.println("_____________________________________________________________________________");
            System.out.println("Гости с обычным типом допуска.");
            ObjectMapper objectMapper = JSONWriter.objectMapper;
            while(rs.next())
            {
                String id = rs.getString("gid");
                String passnumber = rs.getString("ps");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                PGobject jsonObject = rs.getObject("al", PGobject.class);
                Map<String, List<Schedule>> accessMap = objectMapper.readValue(jsonObject.toString(), new TypeReference<Map<String, List<Schedule>>>(){});
                AccessList accessList = new AccessList(accessMap);
                //accessList.print();
                if(accessList.equals(GuestListFiller.standardAccessList))
                {
                    System.out.println("ID: " + id + " Passnumber: " + passnumber);
                    System.out.println(name + " " + surname);
                    System.out.println("______________");
                }
            }
        } catch (SQLException | JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public static void guestsWithSoonAccessExpiration()
    {
        LocalDate date = LocalDate.now();
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT Guest.id as gid, " +
                    "Guest.passnumber as ps, passport.name as name, " +
                    "passport.surname as surname," +
                    "accessperiod.start as st, " +
                    "accessperiod.end as ed " +
                    "FROM guest, passport, accessperiod " +
                    "WHERE guest.passportid = passport.id " +
                    "AND guest.accessperiodid = accessperiod.id " +
                    "AND '" + date + "'::date >= accessperiod.start::date " +
                    "AND '" + date + "'::date <= accessperiod.end::date";
            //System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            System.out.println("_____________________________________________________________________________");
            System.out.println("Гости, у которых скоро закончится пропуск.");
            while(rs.next())
            {
                String id = rs.getString("gid");
                String passnumber = rs.getString("ps");
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                LocalDate start = LocalDateTime.parse(rs.getString("st")).toLocalDate();
                LocalDate end = LocalDateTime.parse(rs.getString("ed")).toLocalDate();

                if(date.plusDays(1).equals(end) || date.plusDays(2).equals(end))
                {
                    System.out.println("ID: " + id + " Passnumber: " + passnumber);
                    System.out.println(name + " " + surname);
                    System.out.println(start + " - " + end);
                    System.out.println("______________");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void placesToVisitAtExactDayTime(Integer id, DayOfWeek day, LocalTime time) {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT accesslist.accesslist as al " +
                    "FROM guest, accesslist " +
                    "WHERE guest.id = " + id + " " +
                    "AND guest.accesslistid = accesslist.id";
            //System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            System.out.println("_____________________________________________________________________________");
            System.out.println("Места для посещения.");
            ObjectMapper objectMapper = JSONWriter.objectMapper;
            while (rs.next()) {
                PGobject jsonObject = rs.getObject("al", PGobject.class);
                Map<String, List<Schedule>> accessMap = objectMapper.readValue(jsonObject.toString(), new TypeReference<>() {
                });
                AccessList accessList = new AccessList(accessMap);
                Map<String, LocalTime> result = new HashMap<>();
                for (String key : accessMap.keySet()) {
                    List<Schedule> scheduleList = accessMap.get(key);
                    for (Schedule schedule : scheduleList) {
                        if (!schedule.getAvailability())
                            continue;

                        if (schedule.getDay().equals(day))
                            if (!schedule.getEndTime().isBefore(schedule.getStartTime())) {
                                if (time.isAfter(schedule.getStartTime()) && time.isBefore(schedule.getEndTime()))
                                    result.put(key, schedule.getEndTime());
                            } else {
                                if (time.isAfter(schedule.getStartTime()) && time.isBefore(LocalTime.parse("23:59"))
                                        || time.isAfter(LocalTime.parse("00:00")) && time.isBefore(schedule.getEndTime()))
                                    result.put(key, schedule.getEndTime());
                            }
                    }
                }

                System.out.println("Вы можете попасть в следующие места:");
                for (String key : result.keySet()) {
                    System.out.println(key + " до " + result.get(key));
                }
                System.out.println("______________");
            }
        }
         catch (SQLException | JsonProcessingException e) {
             e.printStackTrace();
         }
    }

    public static void getGuestByCredentials(Integer id, String passNumber)
    {
        try (Connection connection = DatabaseConnector.getConnection();
             Statement statement = connection.createStatement()) {
            String query = "SELECT passport.name as name, " +
                    "passport.surname as surname, " +
                    "passport.patronymic as patronymic, " +
                    "passport.birthdate as bd, " +
                    "passport.number as number, " +
                    "passport.code as code," +
                    "passport.issuedate as date, " +
                    "accessperiod.start as st, " +
                    "accessperiod.end as ed " +
                    "FROM guest, passport, accessperiod " +
                    "WHERE guest.id = " + id + " AND guest.passnumber = '" + passNumber + "' " +
                    "AND guest.passportid = passport.id " +
                    "AND guest.accessperiodid = accessperiod.id";
            //System.out.println(query);
            ResultSet rs = statement.executeQuery(query);
            System.out.println("_____________________________________________________________________________");
            System.out.println("Данные о госте с id = " + id + " и passNumber = " + passNumber);
            while(rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                String patronymic = rs.getString("patronymic");
                String birthdate = rs.getString("bd");
                String number = rs.getString("number");
                String issueDate = rs.getString("date");
                LocalDate start = LocalDateTime.parse(rs.getString("st")).toLocalDate();
                LocalDate end = LocalDateTime.parse(rs.getString("ed")).toLocalDate();

                System.out.println("ID: " + id + " Passnumber: " + passNumber);
                System.out.println(surname + " " + name + " " + patronymic);
                System.out.println("Born: " + birthdate);
                System.out.println("Passport number: " + number);
                System.out.println("Passport issue date: " + issueDate);
                System.out.println("Visiting: " + start + " - " + end);
                System.out.println("______________");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
public class App 
{
    public static void createTables()
    {
        DatabaseInitializer.dropTables();
        DatabaseInitializer.createTables();
    }
    public static void fillTables(List<Guest> guestList)
    {
        int id = 1;
        for(Guest guest : guestList)
        {
            Passport passport = guest.getPassport();
            AccessPeriod accessPeriod = guest.getAccessPeriod();
            AccessList accessList = guest.getAccessList();
            DatabaseFiller.addPassport(passport, id);
            DatabaseFiller.addAccessPeriod(accessPeriod, id);
            DatabaseFiller.addAccessList(accessList, id);
            DatabaseFiller.addGuest(guest, id);
            id += 1;
        }
    }

    public static void makeQueries()
    {
        DatabaseQueries.guestsWithExactBirthday(1, 1);
        DatabaseQueries.guestsBornExactYear(1990, 2001);
        DatabaseQueries.guestsBornExactYear(1999, 0);
        DatabaseQueries.guestsWithAccessAtExactDate("2023-05-29");
        DatabaseQueries.guestsFromExactCity("Saratov");
        DatabaseQueries.guestsStandard();
        DatabaseQueries.guestsVIP();
        DatabaseQueries.guestsWithSoonAccessExpiration();
        DatabaseQueries.placesToVisitAtExactDayTime(3, DayOfWeek.FRIDAY, LocalTime.parse("21:00"));
        DatabaseQueries.getGuestByCredentials(2, "134");
    }
}
