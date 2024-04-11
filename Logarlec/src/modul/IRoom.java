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
}
