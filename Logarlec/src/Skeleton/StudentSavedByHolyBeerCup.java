package Skeleton;

import modul.*;

public class StudentSavedByHolyBeerCup {
    private static Student st;
    private static HolyBeerCup hbc;
    private static DoorSide d1;
    private static DoorSide d2;
    private static Room r0;
    private static Room r1;
    private static Game g;
    private static Instructor i;

    private static void Init(){
        st = new Student();
        hbc = new HolyBeerCup();
        d1 = new DoorSide();
        d2 = new DoorSide();
        r0 = new Room();
        r1 = new Room();
        g = new Game();
        i = new Instructor();

        r0.AddStudent(st);
        r0.AddItem(hbc);
        r0.AddDoor(d1);
        d1.SetRoom(r0);
        d1.SetPair(d2);
        d2.SetRoom(r1);
        d2.SetPair(d1);
        r1.AddInstructor(i);
        g.StartGame();

    }

    public static void Run(){
        System.out.println("Init:");
        Init();
        System.out.println("Futás:");
        st.Pickup(hbc);
        st.UseItem(hbc);
        st.Move(d1);
        if (st.GetRoom().GetInstructors() != null)
            st.GetRoom().GetInstructors().get(0).StealSoul(st);
        st.EndTurn();

    }

}
