package viewmodel;

import model.*;

import java.util.List;

public interface IPerson {

    /**
     * Ez a metódus a paraméterként kapott értékre állítja az activeTurn változót
     *
     * @param b Az érték amire az activeTurn változó értékét állítja a metódus
     */
    void SetActiveTurn(boolean b);

    /**
     * A Person felszed egy tárgyat. Ez egy abstract függvény, mely a származott osztályokban van implementálva úgy,
     * ha az adott Person képes felvenni a tárgyat, van hely az inventoryjában, akkor ez belekerül az inventoryjába.
     * Előfordulhat, hogy nem kerül benne az inventoryjába, ha nincs több helye, ekkor false-al tér vissza más esetben true-val.
     *
     * @param  i  Az az Item, amelyet a Person fel szeretne venni.
     * @return    Visszatérési érték egy boolean, ami jelzi, hogy sikerült-e felvenni az i Itemet vagy sem
     */
    boolean Pickup(Item i);

    /**
     * Ennek segítségével lehet az adott Person-nal a szobák között mozogni. A kiválaszott ajtóba megpróbál belépni.
     * Lehetséges, hogy az adott ajtón nem lehet belépni valamiért, de amennyiben tud, akkor a Person megjelenhet
     * az ajtó által definiált másik szobában.
     *
     *  @param  d  Egy ajtó, amelyen a Person megpróbál átlépni egy másik szobába
     *  @return  Egy boolean érték ami megmondja, hogy sikerült e átlépni a másik szobába.
     */
    boolean Move(DoorSide d);


    /**
     * A Person a kiválasztott Item-et eldobja. Ha a paraméterként megadott Item nem szerepel a Person inventoryjában,
     * akkor nem történik semmi. Ellenkező esetben az Item törlődik a Person inventoryjából és abba a szobába kerül ahol
     * épp tartózkodik. Az itemen minden esetben meghívódik az Item Thrown függvénye, mely az Itemtől függően törli a Person
     * védelmi Itemjei közül is a megadott Itemet.
     *
     *  @param  i  A válaszott Item, melyet a Person eldob
     */
    void Throw(Item i);


    /**
     * Egy absract függvény, mely a kiválasztott Itemet aktiválja amennyiben elhetseges. Ez a származott osztályokban van leimplementálva, mert
     * attól függően változik egy Usable használata, hogy ki használja.
     */
    void UseItem(Item i);


    /**
     * Ez az a függvény amelyet a Game hív meg, és ezen belül implementálhatja az adott származott, hogy milyen lépéseket
     * tesz az aktív körében. Ekkor az activeTurn true értékre is változik.
     */
    void StartTurn();


    /**
     * Egy származott osztály implementálja, és ezzel jelzi, hogy az adott köre véget ért. Ekkor az activeTurn értékre false
     * lesz.
     */
    void EndTurn();

    /**
     * Vissza adja azt a szobát amiben a Person jelenleg tartózkodik.
     *
     *  @return  Az a szoba ahol jenleg tartózkodik
     */
    Room GetRoom();


    /**
     * Vissza adja a Persont egyertelmuen azonosito id-t
     * @return Persont egyertelmuen azonosito id
     */
    String GetID();

    /**
     * Vissza adja a szemelynel talalhato targyakat.
     *
     *  @return  Egy lista ami tartalmazza a szemelynel talahato targyakat
     */
    List<Item> GetInventory();

    /**
     * Vissza adja a szemelynel talalhato WetTableClothesokat.
     *
     *  @return  Egy lista ami tartalmazza a szemelynel talahato WetTableClothesokat
     */
     List<Defendable> GetWetTableClothes();
    /**
     * Vissza adja a szemelynel talalhato TVSZeket.
     *
     *  @return  Egy lista ami tartalmazza a szemelynel talahato TVSZeket
     */
     List<Defendable> GetTVSZs();

    /**
     * Vissza adja a szemelynel talalhato HolyBeerCupsokat.
     *
     *  @return  Egy lista ami tartalmazza a szemelynel talahato HolyBeerCupsokat
     */
     List<Defendable> GetHolyBeerCups();

    /**
     * Vissza adja a szemelynel talalhato FFP2Maskokat.
     *
     *  @return  Egy lista ami tartalmazza a szemelynel talahato FFP2Maskokat
     */
     List<Defendable> GetFFP2Masks();

    /**
     * Vissza adja hogy az adott szemely el van-e ajulva.
     *
     *  @return  Egy boolean ertek ami jelzi hogy ajult e a szemely
     */
    boolean GetIsFainted();

    /**
     * Vissza adja hogy az adott szemely el van-e kabulva.
     *
     *  @return  Egy boolean ertek ami jelzi hogy kabult e a szemely
     */
    boolean GetIsStunned();

    /**
     * Vissza adja hogy az adott szemelynek aktiv kore van e
     *
     *  @return  Egy boolean ertek ami jelzi hogy aktiv kore van-e a szemelynek
     */
    boolean GetIsActiveTurn();

    /**
     * Vissz adja, hogy az adott Person el e. Csak hallgato eseteben lehet ez false
     * @return
     */
    boolean GetIsAlive();

    /**
     * Az IPerson a két paraméterként megadott Transistor-t összekapcsolja. Ez után már képes lesz használni a
     * két Transistor-t arra, hogy szobák között teleportáljon.
     *
     *  @param t1  Az egyik transzisztor amit csatlakoztatni akar a másikhoz
     *  @param t2  A másik tranzisztor amit a t1-hez szeretne csatlakoztatni
     */
    void ConnectTransistors(Transistor t1, Transistor t2);

}
