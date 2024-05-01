package Skeleton;

import controller.Game;
import modul.DoorSide;
import modul.FFP2Mask;
import modul.Student;
import modul.Room;

public class StudentSavedByFFP2Mask {
    private static Student s;
    private static Room r1, r2;
    private static DoorSide d1, d2;
    private static FFP2Mask f;

    private static Game g;


    private static void initTest(){
        g = new Game();
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
        s = new Student(g);
        r1.AddStudent(s);
        s.SetRoom(r1);
    }

    public static void Run(){
        System.out.println("Init");
        initTest();
        System.out.println("--------");
        System.out.println("Run");

        s.Pickup(f);
        s.UseItem(f);
        s.Move(d1);
        System.out.println("--------");
    }
}
