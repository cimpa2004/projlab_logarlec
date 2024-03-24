package Skeleton;

import modul.*;

public class StudentSelfGas {

    private static Student st;
    private static Camembert c;
    private static HolyBeerCup hbc;
    private static WetTableCloth wtc;
    private static TVSZ tvsz;
    private static FFP2Mask ffp2;
    private static Room r;
    public static void Init(){

        r = new Room();

        st = new Student();
        r.AddStudent(st);

        c = new Camembert();
        st.AddToInventory(c);

        ffp2 = new FFP2Mask();
        st.AddToInventory(ffp2);
        st.AddFFP2Mask(ffp2);

        tvsz = new TVSZ();
        st.AddToInventory(tvsz);
        st.AddTVSZ(tvsz);

        wtc = new WetTableCloth();
        st.AddToInventory(wtc);
        st.AddWetTableCloth(wtc);

        hbc = new HolyBeerCup();
        st.AddToInventory(hbc);
        st.AddHolyBeerCup(hbc);
    }

    public static void Run(){
        System.out.println("Init");
        Init();
        System.out.println("--------");
        System.out.println("Run");

        st.UseItem(c);

        System.out.println("--------");

    }
}
