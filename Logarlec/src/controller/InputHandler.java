package controller;

import modul.*;

import java.security.InvalidParameterException;
import java.util.*;
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
    final private Map<String, BiFunction<ArrayList<String>, Game, String>> commandMap;

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
    public String handleCommand(String input, Game game) throws InvalidParameterException {
        String[] parts = input.split(" ");
        String command = parts[0];
        String[] parameters = new String[parts.length - 1];
        System.arraycopy(parts, 1, parameters, 0, parameters.length);
        ArrayList<String> parameterList = new ArrayList<>(Arrays.asList(parameters));

        BiFunction<ArrayList<String>, Game, String> commandFunction = commandMap.get(command);
        if (commandFunction != null) {
            if (!command.equals("CreateGame") && game == null) throw new InvalidParameterException("Game parameter for was not initialized. First call CreateGame.");
            return commandFunction.apply(parameterList, game);
        } else {
            throw new InvalidParameterException("Given command is not defined: " + command);
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
    public String createGame(ArrayList<String> parameters, Game game) {
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
    public String startGame(ArrayList<String> parameters, Game game) throws InvalidParameterException {
        StringBuilder str = new StringBuilder();
        game.StartGame();
        str.append("message: A játék elindult. A személykre vonatkozó parancsok közül azokra kell meghívni aki a soron következő, majd arra EndTurn paranccsal fejezhető be a kör.");
        IPerson currentTurn = game.GetCurrentTurn(); // ez nem kene h null lehessen, jatekosok nelkul nincs ertelme elinditani a jatekot
        str.append("\ncurrentTurn: ").append(currentTurn.GetId());
        return str.toString();
    }

    /**
     * Kiírja a kimenetre a játék jelenlegi állapotát.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ebben az esetben ures a parameters lista
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String describeGame(ArrayList<String> parameters, Game game) {
        StringBuilder str = new StringBuilder();
        str.append("message: A játék jelenlegi állapotai a többi mezőben.");

        // gameTimer
        str.append("\ngameTimer: ").append(game.GetGameTimer());

        // isEndGame, winSide
        boolean isGameEnded = game.GetIsEndGame();
        str.append("\nisEndGame: ").append(isGameEnded);
        if (isGameEnded){
            str.append("\nwinSide: ").append(game.GetWinSide());
        }

        // currentTurn
        str.append("\ncurrentTurn: ").append(game.GetCurrentTurn().GetId());

        // turnOrder
        ArrayList<IPerson> turnOrder = game.GetTurnOrder();
        turnOrder.sort(Comparator.comparing(IPerson::GetId)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\nturnOrder: [");
        for (IPerson person : turnOrder){
            str.append(person.GetId());
            if(person != turnOrder.get(turnOrder.size()-1)) str.append(", ");
        }
        str.append("]");

        // rooms
        ArrayList<IRoom> rooms = game.GetRooms();
        rooms.sort(Comparator.comparing(IRoom::GetId)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\nrooms: [");
        for (IRoom room : rooms){
            str.append(room.GetId());
            if(room != rooms.get(rooms.size()-1)) str.append(", ");
        }
        str.append("]");

        // doors
        ArrayList<DoorSide> allDoors = new ArrayList<>();
        for (IRoom room : rooms){
            allDoors.addAll(room.GetDoors());
        }
        allDoors.sort(Comparator.comparing(DoorSide::GetId)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\ndoors: [");
        for (DoorSide door : allDoors){
            str.append(door.GetId());
            if(door != allDoors.get(allDoors.size()-1)) str.append(", ");
        }
        str.append("]");

        // items (ennek egyenlonek kene lennie (itemsFromPersons + itemsFromRooms)-val es 2 diszjunkt halmaz)
        ArrayList<Item> allItems = new ArrayList<>();
        //     items from persons
        ArrayList<IPerson> allPerson = game.GetTurnOrder();
        for (IPerson person : allPerson){
            allItems.addAll(person.GetInventory());
        }
        //     items from rooms
        ArrayList<IRoom> allRooms = game.GetRooms();
        for (IRoom room : allRooms){
            allItems.addAll(room.GetItems());
        }
        allItems.sort(Comparator.comparing(Item::GetId)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\nitems: [");
        for (Item item : allItems){
            str.append(item.GetId());
            if(item != allItems.get(allItems.size()-1)) str.append(", ");
        }
        str.append("]");

        // students / instructors / janitors
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Instructor> instructors = new ArrayList<>();
        ArrayList<Janitor> janitors = new ArrayList<>();
        for (IRoom room : rooms){
            students.addAll(room.GetStudents());
            instructors.addAll(room.GetInstructors());
            janitors.addAll(room.GetJanitors());
        }
        //     students
        students.sort(Comparator.comparing(Student::GetId)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\nstudents: [");
        for (Student student : students){
            str.append(student.GetId());
            if(student != students.get(students.size()-1)) str.append(", ");
        }
        str.append("]");
        //     instructors
        instructors.sort(Comparator.comparing(Instructor::GetId));
        str.append("\ninstructors: [");
        for (Instructor instructor : instructors){
            str.append(instructor.GetId());
            if(instructor != instructors.get(instructors.size()-1)) str.append(", ");
        }
        str.append("]");
        //     janitor
        janitors.sort(Comparator.comparing(Janitor::GetId));
        str.append("\njanitors: [");
        for (Janitor janitor : janitors){
            str.append(janitor.GetId());
            if(janitor != janitors.get(janitors.size()-1)) str.append(", ");
        }
        str.append("]");


        return str.toString();
    }

    /**
     * Kilistázza a játékban résztvevő hallgatókat.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ebben az esetben ures a parameters lista
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String listStudents(ArrayList<String> parameters, Game game) {
        StringBuilder str = new StringBuilder();
        str.append("message: A játékban résztvevő hallgatók a többi mezőben.");

        ArrayList<IRoom> rooms = game.GetRooms();
        // students
        ArrayList<Student> students = new ArrayList<>();
        for (IRoom room : rooms){
            students.addAll(room.GetStudents());
        }

        students.sort(Comparator.comparing(Student::GetId)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\nstudents: [");
        for (Student student : students){
            str.append(student.GetId());
            if(student != students.get(students.size()-1)) str.append(", ");
        }
        str.append("]");

        return str.toString();
    }

    /**
     * Kilistázza a játékban résztvevő oktatókat.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ebben az esetben ures a parameters lista
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String listInstructors(ArrayList<String> parameters, Game game) {
        StringBuilder str = new StringBuilder();
        str.append("message: A játékban résztvevő oktatók a többi mezőben.");

        ArrayList<IRoom> rooms = game.GetRooms();
        // instructors
        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Instructor> instructors = new ArrayList<>();
        ArrayList<Janitor> janitors = new ArrayList<>();
        for (IRoom room : rooms){
            students.addAll(room.GetStudents());
            instructors.addAll(room.GetInstructors());
            janitors.addAll(room.GetJanitors());
        }

        instructors.sort(Comparator.comparing(Instructor::GetId));
        str.append("\ninstructors: [");
        for (Instructor instructor : instructors){
            str.append(instructor.GetId());
            if(instructor != instructors.get(instructors.size()-1)) str.append(", ");
        }
        str.append("]");

        return str.toString();
    }

    /**
     * Kilistázza a játékban résztvevő takarítókat.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ebben az esetben ures a parameters lista
     * @param game A jatek amin a parancsot vegrehajta
     * @return Vissza adja a parancs kimenetet
     */
    public String listJanitors(ArrayList<String> parameters, Game game) {
        StringBuilder str = new StringBuilder();
        str.append("message: A játékban résztvevő oktatók a többi mezőben.");

        ArrayList<IRoom> rooms = game.GetRooms();
        // janitors
        ArrayList<Janitor> janitors = new ArrayList<>();
        for (IRoom room : rooms){
            janitors.addAll(room.GetJanitors());
        }

        janitors.sort(Comparator.comparing(Janitor::GetId));
        str.append("\njanitors: [");
        for (Janitor janitor : janitors){
            str.append(janitor.GetId());
            if(janitor != janitors.get(janitors.size()-1)) str.append(", ");
        }
        str.append("]");

        return str.toString();
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
    public String describeRoom(ArrayList<String> parameters, Game game) {
        StringBuilder str = new StringBuilder();
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            throw new InvalidParameterException("DescribeRoom: no parameter was given or it was empty.");
        }
        // TODO
        String roomId = parameters.get(0);
        IRoom paramRoom;
        ArrayList<IRoom> rooms = game.GetRooms();
        for (IRoom room : rooms){
            if (room.GetId().equals(roomId)){
                paramRoom = room;
            }
        }


        str.append("\nrooms: [");
        for (IRoom room : rooms){
            str.append(room.GetId());
            if(room != rooms.get(rooms.size()-1)) str.append(", ");
        }
        str.append("message: A megadott ").append("szoba részletei a többi mezőben.");




        return str.toString();
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
    public String describePerson(ArrayList<String> parameters, Game game) {
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
    public String describeItem(ArrayList<String> parameters, Game game) {
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
    public String describeDoor(ArrayList<String> parameters, Game game) {
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
    public String pickup(ArrayList<String> parameters, Game game) {
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
    public String throwItem(ArrayList<String> parameters, Game game) {
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
    public String move(ArrayList<String> parameters, Game game) {
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
    public String useItem(ArrayList<String> parameters, Game game) {
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
    public String connectTransistors(ArrayList<String> parameters, Game game) {
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
    public String endTurn(ArrayList<String> parameters, Game game) {
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
    public String setCursed(ArrayList<String> parameters, Game game) {
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
    public String mergeRooms(ArrayList<String> parameters, Game game) {
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
    public String separateRoom(ArrayList<String> parameters, Game game) {
        return "";
    }
}
