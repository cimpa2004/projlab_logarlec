package Skeleton;

import modul.DoorSide;
import modul.FFP2Mask;
import modul.Instructor;
import modul.Room;

public class InstructorSavedByFFP2Mask {
    private static Instructor i;
    private static Room r1, r2;
    private static DoorSide d1, d2;
    private static FFP2Mask f;


    private static void initTest(){
        r1 = new Room();
        r2 = new Room();
        r2.SetPoisonDuration(10);

        d1 = new DoorSide();
        d2 = new DoorSide();
        d1.SetRoom(r1);
        d2.SetRoom(r2);
        d2.SetPair(d1);
        d1.SetPair(d2);


        f = new FFP2Mask();
        f.SetRoom(r1);
        r1.AddItem(f);
        i = new Instructor();
        r1.AddInstructor(i);
        i.SetRoom(r1);
    }

    public static void Run(){
        System.out.println("Init");
        initTest();
        System.out.println("--------");
        System.out.println("Run");

        // Fuggvenyhivasok a szekvencia diagrammon
        i.Pickup(f);
        i.UseItem(f);
        i.Move(d1);
        System.out.println("--------");
    }
}
