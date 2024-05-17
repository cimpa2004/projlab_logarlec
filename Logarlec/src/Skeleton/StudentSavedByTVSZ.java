package Skeleton;

import controller.Game;
import model.*;
import util.Logger;

public class StudentSavedByTVSZ {
    private static TVSZ t;
    private static Student s;
    private static Room r1;
    private static Room r2;
    private static DoorSide d1;
    private static DoorSide d2;
    private static Instructor i;
    private static Game g = new Game(true, Logger.LogLevel.CALL_FLOWS_MODEL);

    private static void initTest(){
        t = new TVSZ();
        s = new Student(g);
        r1 = new Room();
        r2 = new Room();
        d1 = new DoorSide();
        d2 = new DoorSide();
        i = new Instructor(g);

        //Szobák összekötése
        d1.SetPair(d2);
        d2.SetPair(d1);
        r1.AddDoor(d1);
        r2.AddDoor(d2);
        d1.SetRoom(r1);
        d2.SetRoom(r2);

        //Személyek és tárgyak elhelyezése
        t.SetRoom(r1);
        r1.AddItem(t);
        s.AppearInRoom(r1);
        i.AppearInRoom(r2);
    }

    public static void Run(){
        System.out.println("Init");
        initTest();
        System.out.println("--------");
        System.out.println("Run");

        //Függvényhívások a szekvencia diagramon
        s.Pickup(t);
        s.Move(d1);

        System.out.println("--------");
    }
}
