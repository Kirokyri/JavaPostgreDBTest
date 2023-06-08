package GuestPack;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class GuestListFiller
{
    public static AccessList standardAccessList = new AccessList();
    public static AccessList VIPAccessList = new AccessList();
    public static void initAccessLists()
    {
        standardAccessList.add("Canteen", new Schedule(DayOfWeek.MONDAY, "08:00", "20:00"));
        standardAccessList.add("Canteen", new Schedule(DayOfWeek.TUESDAY, "08:00", "20:00"));
        standardAccessList.add("Canteen", new Schedule(DayOfWeek.WEDNESDAY, "08:00", "20:00"));
        standardAccessList.add("Canteen", new Schedule(DayOfWeek.THURSDAY, "08:00", "20:00"));
        standardAccessList.add("Canteen", new Schedule(DayOfWeek.FRIDAY, "08:00", "20:00"));
        standardAccessList.add("Canteen", new Schedule(DayOfWeek.SATURDAY, "08:00", "20:00"));
        standardAccessList.add("Canteen", new Schedule(DayOfWeek.SUNDAY, "08:00", "20:00"));

        standardAccessList.add("Bar", new Schedule(DayOfWeek.MONDAY, "18:00", "01:00"));
        standardAccessList.add("Bar", new Schedule(DayOfWeek.TUESDAY, "18:00", "01:00"));
        standardAccessList.add("Bar", new Schedule(DayOfWeek.WEDNESDAY, "18:00", "01:00"));
        standardAccessList.add("Bar", new Schedule(DayOfWeek.THURSDAY, "18:00", "01:00"));
        standardAccessList.add("Bar", new Schedule(DayOfWeek.FRIDAY, "18:00", "04:00"));
        standardAccessList.add("Bar", new Schedule(DayOfWeek.SATURDAY, "18:00", "04:00"));
        standardAccessList.add("Bar", new Schedule(DayOfWeek.SUNDAY, "18:00", "04:00"));

        standardAccessList.add("SPA", new Schedule(DayOfWeek.MONDAY, "12:00", "20:00"));
        standardAccessList.add("SPA", new Schedule(DayOfWeek.TUESDAY, "12:00", "20:00"));
        standardAccessList.add("SPA", new Schedule(DayOfWeek.WEDNESDAY, "12:00", "20:00"));
        standardAccessList.add("SPA", new Schedule(DayOfWeek.THURSDAY, "12:00", "20:00"));
        standardAccessList.add("SPA", new Schedule(DayOfWeek.FRIDAY, "12:00", "20:00"));
        standardAccessList.add("SPA", new Schedule(DayOfWeek.SATURDAY, "12:00", "20:00"));
        standardAccessList.add("SPA", new Schedule(DayOfWeek.SUNDAY, "12:00", "20:00"));

        standardAccessList.add("Pool", new Schedule(DayOfWeek.MONDAY, false));
        standardAccessList.add("Pool", new Schedule(DayOfWeek.TUESDAY, "08:00", "21:00"));
        standardAccessList.add("Pool", new Schedule(DayOfWeek.WEDNESDAY, "08:00", "21:00"));
        standardAccessList.add("Pool", new Schedule(DayOfWeek.THURSDAY, "08:00", "21:00"));
        standardAccessList.add("Pool", new Schedule(DayOfWeek.FRIDAY, "08:00", "21:00"));
        standardAccessList.add("Pool", new Schedule(DayOfWeek.SATURDAY, "08:00", "21:00"));
        standardAccessList.add("Pool", new Schedule(DayOfWeek.SUNDAY, "08:00", "21:00"));

        //AccessList VIPAccessList = new AccessList();
        VIPAccessList.add("Canteen", new Schedule(DayOfWeek.MONDAY, "08:00", "20:00"));
        VIPAccessList.add("Canteen", new Schedule(DayOfWeek.TUESDAY, "08:00", "20:00"));
        VIPAccessList.add("Canteen", new Schedule(DayOfWeek.WEDNESDAY, "08:00", "20:00"));
        VIPAccessList.add("Canteen", new Schedule(DayOfWeek.THURSDAY, "08:00", "20:00"));
        VIPAccessList.add("Canteen", new Schedule(DayOfWeek.FRIDAY, "08:00", "20:00"));
        VIPAccessList.add("Canteen", new Schedule(DayOfWeek.SATURDAY, "08:00", "20:00"));
        VIPAccessList.add("Canteen", new Schedule(DayOfWeek.SUNDAY, "08:00", "20:00"));

        VIPAccessList.add("Bar", new Schedule(DayOfWeek.MONDAY, "16:00", "03:00"));
        VIPAccessList.add("Bar", new Schedule(DayOfWeek.TUESDAY, "16:00", "03:00"));
        VIPAccessList.add("Bar", new Schedule(DayOfWeek.WEDNESDAY, "16:00", "03:00"));
        VIPAccessList.add("Bar", new Schedule(DayOfWeek.THURSDAY, "16:00", "03:00"));
        VIPAccessList.add("Bar", new Schedule(DayOfWeek.FRIDAY, "16:00", "05:00"));
        VIPAccessList.add("Bar", new Schedule(DayOfWeek.SATURDAY, "16:00", "05:00"));
        VIPAccessList.add("Bar", new Schedule(DayOfWeek.SUNDAY, "16:00", "05:00"));

        VIPAccessList.add("SPA", new Schedule(DayOfWeek.MONDAY, "09:00", "21:00"));
        VIPAccessList.add("SPA", new Schedule(DayOfWeek.TUESDAY, "09:00", "21:00"));
        VIPAccessList.add("SPA", new Schedule(DayOfWeek.WEDNESDAY, "09:00", "21:00"));
        VIPAccessList.add("SPA", new Schedule(DayOfWeek.THURSDAY, "09:00", "21:00"));
        VIPAccessList.add("SPA", new Schedule(DayOfWeek.FRIDAY, "09:00", "21:00"));
        VIPAccessList.add("SPA", new Schedule(DayOfWeek.SATURDAY, "09:00", "21:00"));
        VIPAccessList.add("SPA", new Schedule(DayOfWeek.SUNDAY, "09:00", "21:00"));

        VIPAccessList.add("Pool", new Schedule(DayOfWeek.MONDAY, false));
        VIPAccessList.add("Pool", new Schedule(DayOfWeek.TUESDAY, "06:00", "00:00"));
        VIPAccessList.add("Pool", new Schedule(DayOfWeek.WEDNESDAY, "06:00", "00:00"));
        VIPAccessList.add("Pool", new Schedule(DayOfWeek.THURSDAY, "06:00", "00:00"));
        VIPAccessList.add("Pool", new Schedule(DayOfWeek.FRIDAY, "06:00", "00:00"));
        VIPAccessList.add("Pool", new Schedule(DayOfWeek.SATURDAY, "06:00", "00:00"));
        VIPAccessList.add("Pool", new Schedule(DayOfWeek.SUNDAY, "06:00", "00:00"));
    }
    public static List<Guest> fill()
    {
        initAccessLists();
        Passport passport = new Passport("17340001", "01-01-2021", "31",
                "Ivanov", "Ivan", "Ivanovich", "01-01-1993", "Moskva");
        Passport passport1 = new Passport("3213001", "05-07-2018", "11",
                "Shmigor", "Igor", "Vasilievich", "05-06-1999", "Engels");
        Passport passport2 = new Passport("56784", "11-12-2012", "12",
                "Andreev", "Dmitry", "Dmitrievich", "31-03-1985", "Saratov");
        Passport passport3 = new Passport("989898", "02-02-2022", "31",
                "Olegov", "Oleg", "Olegovich", "01-05-1976", "Moskva");
        Passport passport4 = new Passport("5432", "06-06-2016", "44",
                "Olegov1", "Oleg1", "Olegovich1", "03-08-2000", "Tula");
        Passport passport5 = new Passport("6984354", "07-07-2017", "45",
                "Olegov2", "Oleg2", "Olegovich2", "01-01-2001", "Tula");
        Passport passport6 = new Passport("754541", "08-08-2018", "48",
                "Olegov3", "Oleg3", "Olegovich3", "05-10-2002", "Tula");
        Passport passport7 = new Passport("123456", "09-09-2019", "49",
                "Olegov4", "Oleg4", "Olegovich4", "06-11-2003", "Tula");
        Passport passport8 = new Passport("4487", "10-10-2020", "50",
                "Olegov5", "Oleg5", "Olegovich5", "07-12-2004", "Tula");
        Passport passport9 = new Passport("65671", "11-11-2021", "51",
                "Olegov6", "Oleg6", "Olegovich6", "08-01-2005", "Tula");
        Passport passport10 = new Passport("21472", "12-12-2022", "52",
                "Olegov7", "Oleg7", "Olegovich7", "09-02-2006", "Tula");
        Passport passport11 = new Passport("545523", "01-01-2023", "53",
                "Olegov8", "Oleg8", "Olegovich8", "10-03-2007", "Tula");

        AccessPeriod accessPeriod1 = new AccessPeriod("01-07-2023 12:00", "14-07-2023 12:00");
        AccessPeriod accessPeriod2 = new AccessPeriod("04-07-2023 12:00", "09-07-2023 12:00");
        AccessPeriod accessPeriod3 = new AccessPeriod("05-06-2023 12:00", "05-07-2023 12:00");
        AccessPeriod accessPeriod4 = new AccessPeriod("01-05-2023 00:00", "09-06-2023 00:00");
        Guest guest1 = new Guest("1", "137", passport, standardAccessList, accessPeriod1);
        Guest guest2 = new Guest("2", "134", passport1, standardAccessList, accessPeriod2);
        Guest guest3 = new Guest("3", "135", passport2, VIPAccessList, accessPeriod3);
        Guest guest4 = new Guest("4", "136", passport3, standardAccessList, accessPeriod4);
        Guest guest5 = new Guest("5", "138", passport4, standardAccessList, accessPeriod1);
        Guest guest6 = new Guest("6", "139", passport5, standardAccessList, accessPeriod2);
        Guest guest7 = new Guest("7", "140", passport6, VIPAccessList, accessPeriod3);
        Guest guest8 = new Guest("8", "141", passport7, standardAccessList, accessPeriod4);
        Guest guest9 = new Guest("9", "142", passport8, standardAccessList, accessPeriod1);
        Guest guest10 = new Guest("10", "143", passport9, VIPAccessList, accessPeriod4);
        Guest guest11 = new Guest("11", "144", passport10, standardAccessList, accessPeriod2);
        Guest guest12 = new Guest("12", "145", passport11, standardAccessList, accessPeriod3);


        List<Guest> guestList = new ArrayList<Guest>();
        guestList.add(guest1);
        guestList.add(guest2);
        guestList.add(guest3);
        guestList.add(guest4);
        guestList.add(guest5);
        guestList.add(guest6);
        guestList.add(guest7);
        guestList.add(guest8);
        guestList.add(guest9);
        guestList.add(guest10);
        guestList.add(guest11);
        guestList.add(guest12);

        return guestList;
    }
}
