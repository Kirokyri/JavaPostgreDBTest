package GuestPack;

import java.util.*;
import java.time.DayOfWeek;

public class AccessList
{
    public Map<String, List<Schedule>> accessList = new HashMap<>();

    public AccessList()
    {
        super();
    }
    //public AccessList(){}
    public AccessList(Map<String, List<Schedule>> accessList)
    {
        this.accessList = accessList;
    }

    public void print()
    {
        System.out.println("Access schedule:");
        for(String place : accessList.keySet())
        {
            System.out.println(place);
            List<Schedule> scheduleList = accessList.get(place);
            for(Schedule schedule : scheduleList)
            {
                System.out.println("\t" + schedule.getDay() + " " + schedule.getStartTime() + " - " + schedule.getEndTime());
            }
        }
    }

    public void add(String place, List<Schedule> scheduleList)
    {
        accessList.put(place, scheduleList);
    }
    public void add(String place, Schedule schedule)
    {
        List<Schedule> scheduleList = accessList.get(place);
        if(Objects.isNull(scheduleList))
        {
            scheduleList = new ArrayList<Schedule>();
            scheduleList.add(schedule);
            accessList.put(place, scheduleList);
            return;
        }
        scheduleList.add(schedule);
        accessList.put(place, scheduleList);
    }
    public void setSchedule(String place, DayOfWeek day, String startTime, String endTime)
    {
        List<Schedule> scheduleList = accessList.get(place);
        int index = scheduleList.indexOf(new Schedule(day));
        Schedule schedule = new Schedule(day, startTime, endTime);

        scheduleList.set(index, schedule);
        accessList.put(place, scheduleList);
    }
    public Schedule getSchedule(String place, DayOfWeek day)
    {
        List<Schedule> scheduleList = accessList.get(place);
        return scheduleList.get(scheduleList.indexOf(new Schedule(day)));
    }
    public List<Schedule> getSchedule(String place)
    {
        return accessList.get(place);
    }

    @Override
    public boolean equals(Object obj)
    {
        AccessList accessList1 = (AccessList) obj;
        for(String place : this.accessList.keySet())
        {
            List<Schedule> scheduleList = this.accessList.get(place);
            List<Schedule> otherScheduleList = accessList1.accessList.get(place);
            for(int i = 0; i < scheduleList.size(); i++)
            {
                if(!(scheduleList.get(i).myEquals(otherScheduleList.get(i))))
                    return false;
            }
        }
        return true;
    }
}
