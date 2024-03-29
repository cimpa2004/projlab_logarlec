package Skeleton;

import modul.Instructor;
import modul.Camembert;
import modul.Room;
import modul.WetTableCloth;
import modul.TVSZ;

public class InstructorFaintedByCamembert {
    private static Instructor i;
    private static Camembert c;
    private static Room r;
    private static WetTableCloth w;
    private static TVSZ tvsz;

    private static void initTest(){
        i = new Instructor();
        c = new Camembert();
        r = new Room();
        w = new WetTableCloth();
        tvsz = new TVSZ();

        r.AddInstructor(i);
        r.AddItem(c);
        r.AddItem(w);
        r.AddItem(tvsz);

        i.SetRoom(r);

    }

    public static void Run(){
        System.out.println("Init");
        initTest();
        System.out.println("--------");
        System.out.println("Run");

        i.Pickup(c);
        i.Pickup(w);
        i.Pickup(tvsz);

        i.UseItem(c);

        c.UsedByInstructor(i);
        i.DefendFromGas();
        i.SetIsFainted(true);
        i.ThrowAllItems();


        System.out.println("--------");
    }

}
