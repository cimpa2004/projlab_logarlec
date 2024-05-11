package Skeleton;

import controller.Game;
import model.*;

public class GasClearsFromRoom {
    private static Room r;
    private static Game g;
    private static Student s;
    private static Instructor i;

    private static void initTest(){
        r = new Room();
        g = new Game(true, 1);
        s = new Student(g);
        i = new Instructor(g);

        //Személyek és tárgyak elhelyezése
        r.SetPoisonDuration(1);
        g.AddRoom(r);
        s.AppearInRoom(r);
        i.AppearInRoom(r);
        s.SetIsFainted(true);
        i.SetIsFainted(true);
    }

    public static void Run(){
        System.out.println("Init");
        initTest();
        System.out.println("--------");
        System.out.println("Run");

        //Függvényhívások a szekvencia diagramon
        r.DecrementPoison();
        i.StartTurn();
        g.NextTurn();

        System.out.println("--------");
    }
}

