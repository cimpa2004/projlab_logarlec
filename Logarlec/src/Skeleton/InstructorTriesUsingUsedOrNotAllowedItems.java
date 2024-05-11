package Skeleton;

import controller.Game;
import model.*;

public class InstructorTriesUsingUsedOrNotAllowedItems {
    private static Instructor i;
    private static Camembert c;
    private static FFP2Mask f;
    private static WetTableCloth w;
    private static HolyBeerCup h;
    private static TVSZ t;
    private static Room r;


    private static void initTest(){
        i = new Instructor(new Game());
        c = new Camembert();
        f = new FFP2Mask();
        w = new WetTableCloth();
        h = new HolyBeerCup();
        t = new TVSZ();
        r = new Room();

        // TVSZ
        t.Decrement();
        r.AddItem(t);

        // Hallgato, targyak hozzadasa
        i.SetRoom(r);
        r.AddInstructor(i);
        i.AddFFP2Mask(f);
        i.AddToInventory(f);
        i.AddWetTableCloth(w);
        i.AddToInventory(w);
        i.AddHolyBeerCup(h);
        i.AddToInventory(h);
        i.AddToInventory(c);
    }

    public static void Run(){
        System.out.println("Init");
        initTest();
        System.out.println("--------");
        System.out.println("Run");

        i.UseItem(f);
        i.UseItem(c);
        i.UseItem(w);
        i.UseItem(h);
        i.Pickup(t);


        System.out.println("--------");
    }
}
