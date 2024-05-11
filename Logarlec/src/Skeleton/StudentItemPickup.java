package Skeleton;

import controller.Game;
import model.*;

public class StudentItemPickup {
    private static Student st;
    private static Camembert c;
    private static HolyBeerCup hbc;
    private static WetTableCloth wtc;
    private static TVSZ tvsz1;
    private static FFP2Mask ffp2;
    private static TVSZ tvsz2;
    private static Room r;
    private static Game game = new Game(true, 1);

    public static void Init(){
        r = new Room();

        st = new Student(game);
        r.AddStudent(st);

        c = new Camembert();
        r.AddItem(c);

        hbc = new HolyBeerCup();
        r.AddItem(hbc);

        wtc = new WetTableCloth();
        r.AddItem(wtc);

        tvsz1 = new TVSZ();
        r.AddItem(tvsz1);

        ffp2 = new FFP2Mask();
        r.AddItem(ffp2);

        tvsz2 = new TVSZ();
        r.AddItem(tvsz2);
    }

    public static void Run(){
        System.out.println("Init");
        Init();
        System.out.println("--------");
        System.out.println("Run");

        st.Pickup(c);
        st.Pickup(hbc);
        st.Pickup(wtc);
        st.Pickup(tvsz1);
        st.Pickup(ffp2);
        st.Pickup(tvsz2);

        System.out.println("--------");
    }
}
