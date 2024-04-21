package Skeleton;

import modul.Student;
import modul.WetTableCloth;
import modul.DoorSide;
import modul.Room;
import modul.Instructor;
import controller.Game;

public class StudentUsesWetTableClothOnInstructor {
    private static Student s;
    private static WetTableCloth w;
    private static DoorSide d1, d2;
    private static Room r1, r2;
    private static Instructor i;
    private static Game g;


    private static void initTest(){
        r1 = new Room();
        r2 = new Room();

        d1 = new DoorSide();
        d2 = new DoorSide();
		
        d1.SetRoom(r1);
        d2.SetRoom(r2);
		
        d2.SetPair(d1);
        d1.SetPair(d2);


        w = new WetTableCloth();
        r1.AddItem(w);

        s = new Student(g);
        r1.AddStudent(s);

        i = new Instructor();
        r2.AddInstructor(i);
    }

    public static void Run(){
        System.out.println("Init");
        initTest();
        System.out.println("--------");
        System.out.println("Run");

        s.Pickup(w);
        s.UseItem(w);
        s.Move(d1);

        i.Stun(3);

        g.NextTurn();

        i.DecrementStun();
        w.Decrement();

        System.out.println("--------");
    }
}
