package GuestPack;

import org.example.*;
import java.util.List;

public class Main {
    public static void main(String[] args)
    {
        List<Guest> guestList = GuestListFiller.fill();
        //GuestListFiller.initAccessLists();
        JSONWriter.objectMapperSetup();
        //JSONWriter.writeJSON(guestList);
        App.createTables();
        App.fillTables(guestList);
        App.makeQueries();
    }
}
