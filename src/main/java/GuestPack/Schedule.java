package GuestPack;

import java.time.LocalTime;
import java.time.DayOfWeek;

import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.annotation.JsonInclude;

import static java.util.Objects.isNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Schedule
{
    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean availability = true;
    private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");

    public Schedule()
    {
        super();
    }
    //public Schedule(){}
    public Schedule(DayOfWeek day)
    {
        this.day = day;
        startTime = null;
        endTime = null;
    }
    public Schedule(DayOfWeek day, String startTime, String endTime)
    {
        this.day = day;
        this.startTime = LocalTime.parse(startTime, dtf);
        this.endTime = LocalTime.parse(endTime, dtf);
    }
    public Schedule(DayOfWeek day, boolean availability)
    {
        this.availability = availability;
        this.day = day;
        startTime = null;
        endTime = null;
    }

    public DayOfWeek getDay()
    {
        return day;
    }
    public void setDay(DayOfWeek day)
    {
        this.day = day;
    }
    public LocalTime getStartTime()
    {
        return startTime;
    }
    public void setStartTime(String startTime)
    {
        this.startTime = LocalTime.parse(startTime, dtf);
    }
    public LocalTime getEndTime()
    {
        return endTime;
    }
    public void setEndTime(String endTime)
    {
        this.endTime = LocalTime.parse(endTime, dtf);
    }
    public boolean getAvailability()
    {
        return availability;
    }
    public void setAvailability(boolean availability)
    {
        this.availability = availability;
    }
/*
    public boolean isNightShift()
    {
        return this.getEndTime().isBefore(this.getStartTime());
    }
*/
    @Override
    public boolean equals(Object obj)
    {
        Schedule schedule = (Schedule) obj;
        return schedule.day.equals(this.day);
    }

    public boolean myEquals(Object obj)
    {
        Schedule schedule = (Schedule) obj;
        LocalTime start = this.getStartTime();
        LocalTime end = this.getEndTime();
        LocalTime otherStart = schedule.getStartTime();
        LocalTime otherEnd = schedule.getEndTime();
        if(isNull(start) && isNull(end) && isNull(otherStart) && isNull(otherEnd))
            return schedule.getDay().equals(this.getDay());
        return schedule.getDay().equals(this.getDay()) && schedule.getStartTime().equals(this.getStartTime())
                && schedule.getEndTime().equals(this.getEndTime());
    }

}
