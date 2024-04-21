package Skeleton;

import controller.Game;
import modul.*;

public class UseTransistor {
    private static Transistor t1;
    private static Transistor t2;
    private static Student s;
    private static Room r1;
    private static Room r2;
    private static DoorSide d1;
    private static DoorSide d2;
    private static Game game;

    private static void initTest(){
        game = new Game();
        t1 = new Transistor();
        t2 = new Transistor();
        s = new Student(game);
        r1 = new Room();
        r2 = new Room();
        d1 = new DoorSide();
        d2 = new DoorSide();

        //Szobák összekötése
        r1.AddNeighbor(r2);
        r2.AddNeighbor(r1);
        d1.SetPair(d2);
        d2.SetPair(d1);
        r1.AddDoor(d1);
        r2.AddDoor(d2);
        d1.SetRoom(r1);
        d2.SetRoom(r2);

        //Személyek és tárgyak elhelyezése
        t1.SetRoom(r1);
        r1.AddItem(t1);
        t2.SetRoom(r1);
        r1.AddItem(t2);
        s.AppearInRoom(r1);
    }

    public static void Run(){
        System.out.println("Init");
        initTest();
        System.out.println("--------");
        System.out.println("Run");

        //Függvényhívások a szekvencia diagramon
        s.Pickup(t1);
        s.Pickup(t2);
        t1.SetPairs(t2);
        s.UseItem(t1);
        s.Throw(t1);
        s.Move(d1);
        s.UseItem(t2);
        s.Throw(t2);

        System.out.println("--------");
    }
}
