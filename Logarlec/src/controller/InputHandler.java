package controller;

import modul.Room;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;



/**
 * Ez képes beolvasni az előre definált parancsokat átalános bemenetről vagy fájlból. A parancsokat a megadott jatekon vegrehajtja,
 * majd a parancsok kimenetet vissza adja.
 */
public class InputHandler {

    /**
     * Egy map ami az előre definiált parancsokhoz (Stringek) mappolja hozzá a végrehajtadó parancsok metódusait, melyek
     * egy Stringek listáját amik a parancs paraméterei, illetve egy Gamet varnak bemenetkent amelyen a parancs végrehajtódik
     * és visszatérnek egy String-el ami a parancs kimenete.
     */
    private Map<String, BiFunction<String[], Game, String>> commandMap;

    public InputHandler() {
        commandMap = new HashMap<>();
        commandMap.put("CreateGame", this::createGame);
        commandMap.put("StartGame", this::startGame);
        commandMap.put("DescribeGame", this::describeGame);
        commandMap.put("ListStudents", this::listStudents);
        commandMap.put("ListInstructors", this::listInstructors);
        commandMap.put("ListJanitors", this::listJanitors);
        commandMap.put("DescribeRoom", this::describeRoom);
        commandMap.put("DescribePerson", this::describePerson);
        commandMap.put("DescribeItem", this::describeItem);
        commandMap.put("DescribeDoor", this::describeDoor);
        commandMap.put("Pickup", this::pickup);
        commandMap.put("Throw", this::throwItem);
        commandMap.put("Move", this::move);
        commandMap.put("UseItem", this::useItem);
        commandMap.put("ConnectTransistors", this::connectTransistors);
        commandMap.put("EndTurn", this::endTurn);
        commandMap.put("SetCursed", this::setCursed);
        commandMap.put("MergeRooms", this::mergeRooms);
        commandMap.put("SeperateRoom", this::separateRoom);
    }

    /**
     * Az inputként megadott parancsot végrehajta a megadott játékon. Ha parancs játék létrehozása, akkor a paraméterként
     * megadott játékot hozza létre.
     * @param input A bemenet ami egy parancs megfelelő paraméterekkel, ha van
     * @param game A játék amin a parancs végrehajtódik
     * @return Vissza adja a parancs kimenetet
     */
    public String handleCommand(String input, Game game) {
        String[] parts = input.split(" ");
        String command = parts[0];
        String[] parameters = new String[parts.length - 1];
        System.arraycopy(parts, 1, parameters, 0, parameters.length);

        BiFunction<String[], Game, String> commandFunction = commandMap.get(command);
        if (commandFunction != null) {
            return commandFunction.apply(parameters, game);
        } else {
            return "A megadott parancs nem talalhato meg a definialt parancsok kozott!";
        }
    }

    /** Ez a metodus inicializalja a parameterkent megadott jatekot. Beallithato hogy a jatek determinisztikus legyen,
     * illetve megadhato egy elore definialt jatekterkep. Amennyiben nincs megadva jatekterkep akkor egy alap terkepet
     * general.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs (jatek nem jon letre,
     * vagyis null lesz).
     *
     * @param parameters Lehetseges parameterek: isDeterministic, gameMapPath (optional)
     * @param game A jatek parameter amit inicializal
     * @return Vissza adja a parancs kimenetet
     */
    public String createGame(String[] parameters, Game game) {
        return "";
    }

    /**
     * Elindítja a játékot, és jelzi, hogy ki a soron következő.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ebben az esetben ures a parameters lista
     * @param game A jatek amit elindit
     * @return Vissza adja a parancs kimenetet
     */
    public String startGame(String[] parameters, Game game) {
        return "";
    }

    /**
     * Kiírja a kimenetre a játék jelenlegi állapotát.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ebben az esetben ures a parameters lista
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String describeGame(String[] parameters, Game game) {
        return "";
    }

    /**
     * Kilistázza a játékban résztvevő hallgatókat.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ebben az esetben ures a parameters lista
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String listStudents(String[] parameters, Game game) {
        return "";
    }

    /**
     * Kilistázza a játékban résztvevő oktatókat.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ebben az esetben ures a parameters lista
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String listInstructors(String[] parameters, Game game) {
        return "";
    }

    /**
     * Kilistázza a játékban résztvevő takarítókat.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ebben az esetben ures a parameters lista
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String listJanitors(String[] parameters, Game game) {
        return "";
    }

    /**
     * Kiírja a megadott szoba részleteit: itemek a szobában, ajtók, szomszédok, átkozott-e, ragadós-e, gázos-e,
     * jelenlegi/maximum kapacitás, mennyi ember volt a szobában takarítás óta, kik tartózkodnak a szobában.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Egy Stringet tartalmaz a lista, a szoba amelynek a reszleteit kiirja
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String describeRoom(String[] parameters, Game game) {
        return "";
    }

    /**
     * A megadott személy részleteit írja ki: szoba amelyben van, inventoryjában lévő tárgyak, olyan tárgyak melyek
     * megvédhetik, bénult-e, kábult-e (csak oktatónak lehet ilyen), jelenleg aktív köre van-e.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Egy Stringet tartalmaz a lista: egy hallgató, oktató vagy takarító.
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     *
     */
    public String describePerson(String[] parameters, Game game) {
        return "";
    }

    /**
     * Részletezi a megadott tárgy adatait. Tárgytól függően több adatot is kiírhat.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Egy Stringet tartalmaz a lista: a tárgy melynek részleteit kiírja.
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String describeItem(String[] parameters, Game game) {
        return "";
    }

    /**
     * Kiírja a megadott ajtó részleteit.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Egy Stringet tartalmaz a lista: az ajtó melynek részleteit kiírja.
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String describeDoor(String[] parameters, Game game) {
        return "";
    }

    /**
     * Az adott személy a megadott tárgyat felveszi a szobából.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ket Stringet tartalmaz a lista: a személy aki felveszi a tárgyat, illetve maga a tárgy.
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String pickup(String[] parameters, Game game) {
        return "";
    }

    /**
     * Az adott személy a megadott tárgyat eldobja az inventoryjából a szobába ahol tartózkodik.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ket Stringet tartalmaz a lista: a személy aki eldobja a tárgyat, illetve maga a tárgy.
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String throwItem(String[] parameters, Game game) {
        return "";
    }

    /**
     * A megadott személlyel a szobában található ajtóba megpróbál belépni.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ket Stringet tartalmaz a lista: a személy amely lépni szeretne a szobák között, illetve az ajtó amin belépne.
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String move(String[] parameters, Game game) {
        return "";
    }

    /**
     * A személy megpróbálja használni a megadott tárgyat (előfordulhat, hogy a hallgatók, oktatók különböző tárgyakat használhatnak).
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ket Stringet tartalmaz a lista: a személy amely a tárgyat használná, illetve maga a tárgy.
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String useItem(String[] parameters, Game game) {
        return "";
    }

    /**
     * Összekapcsol két tranzisztort.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Harom Stringet tartalmaz a lista: a hallgató aki a tranzisztorokat kapcsolja össze, illetve a két tranzisztor melyet összekapcsolna.
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String connectTransistors(String[] parameters, Game game) {
        return "";
    }

    /**
     * Ennek hatására az adott személy végez a körével. Mindig csak azon a személyen lehet ezt meghívni akinek éppen a köre van.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Egy Stringet tartalmaz a lista: a személy aki a körével végez.
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String endTurn(String[] parameters, Game game) {
        return "";
    }

    /**
     * A megadott szoba elátkozottsága állítható be.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ket Stringet tartalmaz a lista: a szoba melynek elátkozottsága állítódik, maga az érték ami jelzi, hogy elátkozott legyen vagy sem.
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String setCursed(String[] parameters, Game game) {
        return "";
    }

    /**
     * A megadott szobák összeolvadnak.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ket Stringet tartalmaz a lista: a két szoba melyek összeolvadnak.
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String mergeRooms(String[] parameters, Game game) {
        return "";
    }

    /**
     * A megadott szobát szétválasztja két szobára.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Egy Stringet tartalmaz a lista: A szoba melyet szétválaszt.
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String separateRoom(String[] parameters, Game game) {
        return "";
    }
}
