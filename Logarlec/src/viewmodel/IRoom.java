package viewmodel;

import modul.*;
import util.Logger;
import util.Reader;

import java.util.ArrayList;
import java.util.Iterator;

public interface IRoom {


    /**
     * Generál véletlenszerűen egy 'true' vagy 'false' bool értéket. A szobák osztódásánál kell ahhoz, hogy
     * a tárgyak véletlenszerűen meg tudjanak feleződni a szobák között.
     * Ha a játék determinisztikus akkor mindig igazat ad vissza.
     *
     * @return Egy random boolean érték.
     * */
    boolean RandomBool();

    /**
     * Elvesz egy tárgyat a szobából (amikor például egy tárgyat felvesznek).
     * A Room items listájából eltávolítja a tárgyta és a tárgy room változóját nullára állítja.
     *
     * @param i Az eltávolítandó tárgy
     * */
    public void RemoveItem(Item i);

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
     * Visszaadja, hogy hány ember volt már a szobában az utolso takaritas ota
     * */
    int GetNumberOfPeopleBeenToRoom();


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
