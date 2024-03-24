package Skeleton;
import modul.*;

/**
 * Teszt eset az oktató inveroryának figyelésére és a játék végére
 */
public class InstructosInventoryGetsFullTheGameEnds {
    private static Instructor i;
    private static SlideRule sd;
    private static WetTableCloth wtc;
    private static HolyBeerCup hbc;
    private static Transistor t1;
    private static Transistor t2;
    private static TVSZ tv1;
    private static TVSZ tv2;
    private static Game g;
    private static Room r;

    private static void Init(){
        i = new Instructor();
        sd = new SlideRule();
        wtc = new WetTableCloth();
        hbc = new HolyBeerCup();
        t1 = new Transistor();
        t2 = new Transistor();
        tv1 = new TVSZ();
        tv2 = new TVSZ();
        g = new Game();
        r = new Room();

        g.AddRoom(r);
        i.SetRoom(r);
        r.AddInstructor(i);
        r.AddItem(sd);
        r.AddItem(wtc);
        r.AddItem(hbc);
        r.AddItem(t1);
        r.AddItem(t2);
        r.AddItem(tv1);
        r.AddItem(tv2);


        g.StartGame();
    }
    public static void Run(){
        System.out.println("Init:");
        Init();
        System.out.println("Futás:");
        i.Pickup(sd);
        i.Pickup(wtc);
        i.Pickup(hbc);
        i.Pickup(t1);
        i.Pickup(t2);
        i.Pickup(tv1);
        i.Pickup(tv2);
        i.EndTurn();
        g.AnyStudentsAlive();
    }


}
