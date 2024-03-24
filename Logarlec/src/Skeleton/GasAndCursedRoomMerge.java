package Skeleton;

import modul.Camembert;
import modul.DoorSide;
import modul.Room;

public class GasAndCursedRoomMerge {
    
    private static Room r1;
    private static Room r2;
    private static Camembert c;
    private static DoorSide d1;
    private static DoorSide d2;

    public static void Init(){

        r1 = new Room();
        r1.SetPoisonDuration(100);

        r2 = new Room();
        r2.SetIsCursed(true);

        c = new Camembert();
        r1.AddItem(c);

        d1 = new DoorSide();
        d1.SetRoom(r1);

        d2 = new DoorSide();
        d2.SetRoom(r2);

        d1.SetPair(d2);
        d2.SetPair(d1);
    }

    public static void Run(){
        System.out.println("Init");
        Init();
        System.out.println("--------");
        System.out.println("Run");

        r1.MergeRooms(r2);

        System.out.println("--------");
    }
}
