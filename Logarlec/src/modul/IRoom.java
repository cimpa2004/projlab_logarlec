package modul;

import util.Logger;
import util.Reader;

import java.util.ArrayList;
import java.util.Iterator;

public interface IRoom {

    /**
     * Összeolvasztja a szobát a paraméterben kapott másik szobával. A maximum kapacitása a nagyobb szoba befogadó-
     * képességével lesz egyenlő, a mérgesgáz ideje a hosszabb ideig tartó idő lesz, valamint elátkozott lesz, ha
     * legalább az egyik szoba elátkozott. Az új szoba szomszédjai a régi két szoba szomszédjainak uniója.
     * Csak akkor hívódik meg, ha mindkét szoba aktuális kapacitása jelenleg 0.
     * A szobákban lévő tárgyak az új szobába kerülnek.
     * */
    boolean MergeRooms(Room r2);

    /**
     * A szoba két szobára válik. Mindkét szoba maximum kapacitása egyenlő lesz az eredeti befogadóképességével, valamint
     * a mérgesgáz- és elátkozottság attribútumok és a szobák szomszédjai is lemásolódnak. Az két új szoba közés is
     * kerül egy ajtó. Csak akkor hívódik meg, ha a szoba aktuális kapacitása jelenleg 0.
     * A szobában lévő tárgyak a két szoba között véletlenszerűen lesznek elszórva.
     * */
    boolean SeparateRoom();


    /**
     * Véletlenszerűen kiválaszt egy szobához tartozó ajtót, majd ha láthatatlan volt, akkor láthatóvá teszi,
     * és fordítva (csak elátkozott szobákban fut le, minden kör végén egyszer)
     * */
    void ToggleDoorsVisible();

    /**
     * Beállítja, hogy a szoba elátkozott legyen, azaz az ajtajai véletlenszerűen megjelenjenek/eltűnjenek.
     * */
    void SetIsCursed(boolean isc);

    /**
     * Visszaadja a szobából kivezető ajtók szoba felé néző oldalait.
     * */
    ArrayList<DoorSide> GetDoors();

    /**
     * Vissza adja a Roomot egyertelmuen azonosito id-t
     * @return Roomot egyertelmuen azonosito id
     */
    String GetId();

    /**
     * Visszaadja a szobában található tárgyakat.
     * */
     ArrayList<Item> GetItems();


    /**
     * Visszaadja a szoba szomszédait, azaz minden olyan szobát, amelybe innen ajtó vezet.
     * */
     ArrayList<Room> GetNeighbors();

    /**
     * Visszaadja, hogy a szoba elátkozott-e.
     * */
     boolean GetIsCursed();

    /**
     * Vissza adja, hogy az adott szoba ragacsos-e.
     * @return Az érték, ami jelzi, hogy ragacsos-e a szoba
     */
     boolean GetIsSticky();

    /**
     * Visszaadja azt, hogy a szobában hány körön keresztül tart még a mérgesgáz hatása.
     * */
     int GetPoisonDuration();

    /**
     * Visszaadja a szoba jelenlegi kapacitását, azaz hogy jelenleg hány személy tartózkodik a szobában.
     * */
     int GetCurrentCapacity();

    /**
     * Visszaadja a szoba maximum kapacitását, azaz hogy egyszerre legfeljebb hány személy lehet a szobában.
     * */
     int GetMaxCapacity();

    /**
     * Visszaadja a szobában tartózkodó hallgatókat.
     * */
     ArrayList<Student> GetStudents();

    /**
     * Visszaadja a szobában tartózkodó oktatokat.
     * */
     ArrayList<Instructor> GetInstructors();

    /**
     * Visszaadja a szobában tartózkodó takaritokat.
     * */
    ArrayList<Janitor> GetJanitors();
}
