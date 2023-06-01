package GuestPack;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Passport
{
    private String number;
    //@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate issueDate;
    private String code;
    private String surname;
    private String name;
    private String patronymic;
    //@JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate birthDate;
    private String birthPlace;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public Passport()
    {
        super();
    }
    public Passport(String number, String issueDate, String code,
                    String surname, String name, String patronymic,
                    String birthDate, String birthPlace)
    {
        this.number = number;
        this.issueDate = LocalDate.parse(issueDate, dtf);
        this.code = code;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthDate = LocalDate.parse(birthDate, dtf);
        this.birthPlace = birthPlace;
    }

    public void print()
    {
        System.out.println("Passport number: " + number + "\nCode: " + code
                            + "\nIssue Date: " + issueDate
                            + "\nSurname: " + surname + "\nName: " + name
                            + "\nPatronymic: " + patronymic
                            + "\nBirthday: " + birthDate + "\nBirth place: " + birthPlace);
    }
    public String getNumber()
    {
        return number;
    }
    public void setNumber(String number)
    {
        this.number = number;
    }
    public LocalDate getIssueDate()
    {
        return this.issueDate;
    }
    public void setIssueDate(String issueDate)
    {
        this.issueDate = LocalDate.parse(issueDate, dtf);
    }
    public String getCode()
    {
        return this.code;
    }
    public void setCode(String code)
    {
        this.code = code;
    }
    public String getSurname()
    {
        return this.surname;
    }
    public void setSurname(String surname)
    {
        this.surname = surname;
    }
    public String getName()
    {
        return this.name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getPatronymic()
    {
        return this.patronymic;
    }
    public void setPatronymic(String patronymic)
    {
        this.patronymic = patronymic;
    }
    public LocalDate getBirthDate()
    {
        return this.birthDate;
    }
    public void setBirthDate(String birthDate)
    {
        this.birthDate = LocalDate.parse(birthDate, dtf);
    }
    public String getBirthPlace()
    {
        return this.birthPlace;
    }
    public void setBirthPlace(String birthPlace)
    {
        this.birthPlace = birthPlace;
    }

}
