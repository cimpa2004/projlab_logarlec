package Skeleton;
import modul.*;

/**
 * 13-as use case teszt esete
 */
public class StudentGetsStuckInARoom {
    private static Student st;
    private static DoorSide invis;
    private static DoorSide oneWay;
    private static DoorSide fulld1;
    private static DoorSide fulld2;
    private static Room full;
    private static Game g;
    private static TVSZ tv;
    private static Room r0;


    private static void Init(){
        st = new Student();
        invis = new DoorSide();
        oneWay = new DoorSide();
        fulld1 = new DoorSide();
        fulld2 = new DoorSide();
        full = new Room();
        g = new Game();
        tv = new TVSZ();
        r0 = new Room();

        st.AddTVSZ(tv);
        r0.AddStudent(st);

        fulld1.SetRoom(r0);
        fulld2.SetRoom(full);
        full.SetMaxCapacity(0);
        fulld1.SetPair(fulld2);
        fulld2.SetPair(fulld1);

        invis.SetIsVisible(false);
        invis.SetRoom(r0);

        oneWay.SetRoom(r0);
        oneWay.SetCanBeOpened(false);
        g.AddRoom(r0);
        g.StartGame();
    }

    public static void Run(){
        System.out.println("Init");
        Init();
        System.out.println("Futás:");
        st.Move(fulld1);
        st.Move(invis);
        st.Move(oneWay);
        System.out.println("A hallgató szobája: " +st.GetRoom());
        st.EndTurn();
        g.NextTurn();
    }

}
