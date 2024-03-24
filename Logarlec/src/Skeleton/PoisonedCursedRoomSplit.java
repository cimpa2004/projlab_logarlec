package Skeleton;

import modul.*;

public class PoisonedCursedRoomSplit {

    private static Room r;
    private static TVSZ t;

    private static void initTest(){
        r = new Room();
        t = new TVSZ();

        r.SetIsCursed(true);
        r.SetPoisonDuration(10);
        t.SetRoom(r);
        r.AddItem(t);
    }

    public static void Run() {
        System.out.println("Init");
        initTest();
        System.out.println("--------");
        System.out.println("Run");

        //Függvényhívások a szekvencia diagramon
        r.SeparateRoom();

        System.out.println("--------");
    }
}
