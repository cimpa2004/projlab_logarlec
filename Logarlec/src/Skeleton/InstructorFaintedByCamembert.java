package Skeleton;

import controller.Game;
import model.Instructor;
import model.Camembert;
import model.Room;
import model.WetTableCloth;
import model.TVSZ;

public class InstructorFaintedByCamembert {
    private static Instructor i;
    private static Camembert c;
    private static Room r;
    private static WetTableCloth w;
    private static TVSZ tvsz;
    private static Game g = new Game(true, 1);

    private static void initTest(){
        i = new Instructor(new Game());
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
