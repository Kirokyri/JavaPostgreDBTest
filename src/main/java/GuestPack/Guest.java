package GuestPack;

public class Guest
{
    private String id;
    private String passNumber;
    private Passport passport;
    private AccessList accessList;
    private AccessPeriod accessPeriod;

    public Guest()
    {
        super();
    }
    //public Guest(){}
    public Guest(String id, String passNumber, Passport passport, AccessList accessList, AccessPeriod accessPeriod)
    {
        this.id = id;
        this.passNumber = passNumber;
        this.passport = passport;
        this.accessList = accessList;
        this.accessPeriod = accessPeriod;
    }

    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }
    public String getPassNumber()
    {
        return passNumber;
    }
    public void setPassNumber(String passNumber)
    {
        this.passNumber = passNumber;
    }
    public Passport getPassport()
    {
        return passport;
    }
    public void setPassport(Passport passport)
    {
        this.passport = passport;
    }
    public AccessList getAccessList()
    {
        return accessList;
    }
    public void setAccessList(AccessList accessList)
    {
        this.accessList = accessList;
    }
    public AccessPeriod getAccessPeriod()
    {
        return accessPeriod;
    }
    public void setAccessPeriod(AccessPeriod accessPeriod)
    {
        this.accessPeriod = accessPeriod;
    }
    public void print()
    {
        System.out.println("ID: " + id + "\nPass Number: " + passNumber);
        accessPeriod.print();
        passport.print();
        accessList.print();
    }

    public void shortPrint()
    {
        System.out.println("ID: " + id + "\nPass Number: " + passNumber);
        System.out.println(getPassport().getSurname() + " " + getPassport().getName());
    }
}
