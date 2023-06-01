package GuestPack;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class AccessPeriod
{
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime start;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime end;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public AccessPeriod()
    {
        super();
    }
    public AccessPeriod(String start, String end)
    {
        this.start = LocalDateTime.parse(start, dtf);
        this.end = LocalDateTime.parse(end, dtf);
    }

    public LocalDateTime getStart()
    {
        return start;
    }
    public void setStart(String start)
    {
        this.start = LocalDateTime.parse(start, dtf);
    }

    public LocalDateTime getEnd()
    {
        return end;
    }
    public void setEnd(String end)
    {
        this.end = LocalDateTime.parse(end, dtf);
    }

    public void print()
    {
        System.out.println(start + " - " + end);
    }
}
