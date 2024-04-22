package Skeleton;

import controller.Game;
import modul.*;

public class StudentTriesUsingUsedItems {
    private static Student s;
    private static Camembert c;
    private static FFP2Mask f;
    private static WetTableCloth w;
    private static HolyBeerCup h;
    private static TVSZ t;
    private static Room r;
    private static Game game;


    private static void initTest(){
        game = new Game();
        s = new Student(game);
        c = new Camembert();
        f = new FFP2Mask();
        w = new WetTableCloth();
        h = new HolyBeerCup();
        t = new TVSZ();
        r = new Room();

        // TVSZ
        t.Decrement();
        t.SetRoom(r);
        r.AddItem(t);

        // Hallgato, targyak hozzadasa
        s.SetRoom(r);
        r.AddStudent(s);
        s.AddFFP2Mask(f);
        s.AddToInventory(f);
        s.AddWetTableCloth(w);
        s.AddToInventory(w);
        s.AddHolyBeerCup(h);
        s.AddToInventory(h);
        s.AddToInventory(c);
    }

    public static void Run(){
        System.out.println("Init");
        initTest();
        System.out.println("--------");
        System.out.println("Run");

        s.UseItem(c);
        s.UseItem(f);
        s.UseItem(w);
        s.UseItem(h);
        s.Pickup(t);


        System.out.println("--------");
    }
}
