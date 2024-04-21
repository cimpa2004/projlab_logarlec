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
        String roomId = parameters.get(0);

        IRoom paramRoom = game.findRoomById(roomId);
        if (paramRoom == null){
            return "message: A megadott szoba " + roomId + " nem létezik.";
        }

        str.append("message: A megadott ").append(roomId).append(" szoba részletei a többi mezőben.");
        // items
        ArrayList<Item> items = paramRoom.GetItems();
        items.sort(Comparator.comparing(Item::GetId));
        str.append("\nitems: [");
        for (Item item : items){
            str.append(item.GetId());
            if(item != items.get(items.size()-1)) str.append(", ");
        }
        str.append("]");

        // doors
        ArrayList<DoorSide> doors = paramRoom.GetDoors();
        doors.sort(Comparator.comparing(DoorSide::GetId));
        str.append("\ndoors: [");
        for (DoorSide door : doors){
            str.append(door.GetId());
            if(door != doors.get(doors.size()-1)) str.append(", ");
        }
        str.append("]");

        // neighbors
        ArrayList<Room> neighbors = paramRoom.GetNeighbors();
        neighbors.sort(Comparator.comparing(Room::GetId));
        str.append("\nneighbors: [");
        for (Room neighbor : neighbors){
            str.append(neighbor.GetId());
            if(neighbor != neighbors.get(neighbors.size()-1)) str.append(", ");
        }
        str.append("]");

        // isCursed
        String isCursed = paramRoom.GetIsCursed() ? "true" : "false";
        str.append("\nisCursed: ").append(isCursed);

        // isSticky
        String isSticky = paramRoom.GetIsSticky() ? "true" : "false";
        str.append("\nisSticky: ").append(isSticky);

        // isGaseous
        String isGaseous = paramRoom.GetPoisonDuration() > 0 ? "true" : "false";
        str.append("\nisGaseous: ").append(isGaseous);

        // currentCapacity
        str.append("\ncurrentCapacity: ").append(paramRoom.GetCurrentCapacity());

        // maxCapacity
        str.append("\nmaxCapacity: ").append(paramRoom.GetMaxCapacity());

        // numberOfPeopleBeenToRoom
        str.append("\nnumberOfPeopleBeenToRoom: ").append(paramRoom.GetNumberOfPeopleBeenToRoom());

        // students / instructors / janitors
        ArrayList<Instructor> instructors = new ArrayList<>(paramRoom.GetInstructors());
        ArrayList<Janitor> janitors = new ArrayList<>(paramRoom.GetJanitors());
        ArrayList<Student> students = new ArrayList<>(paramRoom.GetStudents());

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
        StringBuilder str = new StringBuilder();
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            throw new InvalidParameterException("DescribePerson: no parameter was given or it was empty.");
        }
        String personId = parameters.get(0);

        IPerson paramPerson = game.findPersonById(personId);
        if (paramPerson == null) {
            return "message: A megadott hallgató/oktató/takarító " + personId + " nem létezik.";
        }

        // describe person
        str.append("message: A megadott ").append(personId).append(" hallgató részletei a többi mezőben.");

        // room
        str.append("\nroom: ").append(paramPerson.GetRoom().GetId());

        // inventory
        ArrayList<Item> inventory = new ArrayList<>(paramPerson.GetInventory());
        inventory.sort(Comparator.comparing(Item::GetId));
        str.append("\ninventory: [");
        for (Item item : inventory){
            str.append(item.GetId());
            if(item != inventory.get(inventory.size()-1)) str.append(", ");
        }
        str.append("]");

        // wetTableClothes
        ArrayList<Item> wetTableClothes = new ArrayList<>();
        for (Defendable wetTableCloth : paramPerson.GetWetTableClothes()){
            wetTableClothes.add((Item)wetTableCloth);
        }
        wetTableClothes.sort(Comparator.comparing(Item::GetId));
        str.append("\nwetTableClothes: [");
        for (Item item : wetTableClothes){
            str.append(item.GetId());
            if(item != wetTableClothes.get(wetTableClothes.size()-1)) str.append(", ");
        }
        str.append("]");

        // tvszs
        ArrayList<Item> tvszs = new ArrayList<>();
        for (Defendable tvsz : paramPerson.GetTVSZs()){
            tvszs.add((Item)tvsz);
        }
        tvszs.sort(Comparator.comparing(Item::GetId));
        str.append("\ntvszs: [");
        for (Item item : tvszs){
            str.append(item.GetId());
            if(item != tvszs.get(tvszs.size()-1)) str.append(", ");
        }
        str.append("]");

        // holyBeerCups
        ArrayList<Item> holyBeerCups = new ArrayList<>();
        for (Defendable holyBeerCup : paramPerson.GetHolyBeerCups()){
            holyBeerCups.add((Item)holyBeerCup);
        }
        holyBeerCups.sort(Comparator.comparing(Item::GetId));
        str.append("\nholyBeerCups: [");
        for (Item item : holyBeerCups){
            str.append(item.GetId());
            if(item != holyBeerCups.get(holyBeerCups.size()-1)) str.append(", ");
        }
        str.append("]");

        // ffp2Masks
        ArrayList<Item> ffp2Masks = new ArrayList<>();
        for (Defendable ffp2Mask : paramPerson.GetFFP2Masks()){
            ffp2Masks.add((Item)ffp2Mask);
        }
        ffp2Masks.sort(Comparator.comparing(Item::GetId));
        str.append("\nffp2Masks: [");
        for (Item item : ffp2Masks){
            str.append(item.GetId());
            if(item != ffp2Masks.get(ffp2Masks.size()-1)) str.append(", ");
        }
        str.append("]");

        // isAlive
        String isAlive = paramPerson.GetIsAlive() ? "true" : "false";
        str.append("\nisAlive: ").append(isAlive);

        // isFainted
        String isFainted = paramPerson.GetIsFainted() ? "true" : "false";
        str.append("\nisFainted: ").append(isFainted);

        // isStunned
        String isStunned = paramPerson.GetIsStunned() ? "true" : "false";
        str.append("\nisStunned: ").append(isStunned);

        // activeTurn
        str.append("\nactiveTurn: ").append(paramPerson.GetIsActiveTurn());

        return str.toString();
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
        StringBuilder str = new StringBuilder();
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            throw new InvalidParameterException("DescribeItem: no parameter was given or it was empty.");
        }
        String itemId = parameters.get(0);

        Item paramItem = game.findItemById(itemId);
        if (paramItem == null) {
            return "message: A megadott item " + itemId + " nem létezik.";
        }

        // describe item
        str.append("message: A megadott ").append(itemId).append(" részletei a többi mezőben.");

        // owner
        Person ownerObj = paramItem.GetOwner();
        String owner = ownerObj == null ? "None" : ownerObj.GetId();
        str.append("\nowner: ").append(owner);

        // room
        Room roomObj = paramItem.GetRoom();
        String room = roomObj == null ? "None" : roomObj.GetId();
        str.append("\nroom: ").append(room);

        // isActive
        String isActive = paramItem.GetIsActive() ? "true" : "false";
        str.append("\nisActive: ").append(isActive);

        // pair
        Transistor pairObj = paramItem.GetPair();
        String pair = pairObj == null ? "None" : pairObj.GetId();
        str.append("\npair: ").append(pair);

        // isFake
        String isFake = paramItem.GetIsFake() ? "true" : "false";
        str.append("\nisFake: ").append(isFake);

        return str.toString();
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
        StringBuilder str = new StringBuilder();
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            throw new InvalidParameterException("DescribeDoor: no parameter was given or it was empty.");
        }
        String doorId = parameters.get(0);

        // find door in game
        DoorSide paramDoor = game.findDoorById(doorId);
        if (paramDoor == null) {
            return "message: A megadott ajtó " + doorId + " nem létezik.";
        }

        // describe door
        str.append("message: A megadott ").append(doorId).append(" ajtó részletei a többi mezőben.");

        // isVisible
        String isVisible = paramDoor.GetIsVisible() ? "true" : "false";
        str.append("\nisVisible: ").append(isVisible);

        // canBeOpened
        String canBeOpened = paramDoor.GetCanBeOpened() ? "true" : "false";
        str.append("\ncanBeOpened: ").append(canBeOpened);

        // pair
        str.append("\npair: ").append(paramDoor.GetPair().GetId());

        // room
        str.append("\nroom: ").append(paramDoor.GetRoom().GetId());

        return str.toString();
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
        if (!(parameters.size() > 1) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty()){
            throw new InvalidParameterException("Pickup: not enough parameter was given or one of them was empty.");
        }
        String personId = parameters.get(0);
        String itemId = parameters.get(1);

        // find person in game
        IPerson paramPerson = game.findPersonById(personId);

        //find item in game
        Item paramItem = game.findItemById(itemId);

        if (paramPerson == null || paramItem == null) {
            return "message: A tárgyat nem sikerült felvenni, a megadott személy " + personId + " vagy item " + itemId + " nem létezik.";
        }

        // try to pick up
        if (paramPerson.GetRoom() != paramItem.GetRoom()){
            return "message: A tárgyat nem sikerült felvenni, a megadott item " + itemId + " nem abban a szobában van mint a megadott személy.";
        }
        boolean isPickedUp = paramPerson.Pickup(paramItem);
        if (!isPickedUp){
            return "message: A tárgyat nem sikerült felvenni, nincs több hely a személy inventoryjában.";
        }
        return "message: A személynek sikerült felvenni a tárgyat amely már megtálható az inventoryjában";
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
        if (!(parameters.size() > 1) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty()){
            throw new InvalidParameterException("ThrowItem: not enough parameter was given or one of them was empty.");
        }
        String personId = parameters.get(0);
        String itemId = parameters.get(1);

        // find person in game
        IPerson paramPerson = game.findPersonById(personId);

       // find item in game
        Item paramItem = game.findItemById(itemId);

        if (paramPerson == null || paramItem == null) {
            return "message: A tárgyat nem sikerült eldobni, a megadott személy " + personId + " vagy item " + itemId + " nem létezik.";
        }

        // try to throw
        if (!paramPerson.GetInventory().contains(paramItem)){
            return "message: A tárgyat nem sikerült eldobni, a megadott item " + itemId + " nincs benne a személy inventoryjában.";
        }
        paramPerson.Throw(paramItem);

        return "message: A személynek sikerült felvenni a tárgyat amely már megtálható az inventoryjában";
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
        if (!(parameters.size() > 1) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty()){
            throw new InvalidParameterException("Move: not enough parameter was given or one of them was empty.");
        }
        String personId = parameters.get(0);
        String doorId = parameters.get(1);

        // find person in game
        IPerson paramPerson = game.findPersonById(personId);

        //find door in game
        DoorSide paramDoor = game.findDoorById(doorId);

        if (paramPerson == null || paramDoor == null) {
            return "message: A személynek nem sikerült belépnie a másik szobába, mert a személy " + personId + " vagy ajtó " + doorId + " nem létezik.";
        }

        // try to move
        boolean isAppeared = paramPerson.Move(paramDoor);
        if (!isAppeared) return "message: A személynek nem sikerült belépnie a másik szobába.";

        return "message: A személynek sikerült belépnie az ajtón és megjelennie a másik szobában.";
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
        if (!(parameters.size() > 1) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty()){
            throw new InvalidParameterException("UseItem: not enough parameter was given or one of them was empty.");
        }
        String personId = parameters.get(0);
        String itemId = parameters.get(1);

        // find person in game
        IPerson paramPerson = game.findPersonById(personId);

        //find door in game
        Item paramItem = game.findItemById(itemId);

        if (paramPerson == null || paramItem == null) {
            return "message: A személynek nem sikerült használnia a tárgyat, mert a személy " + personId + " vagy tárgy " + itemId + " nem létezik.";
        }

        // try to use
        if (!paramPerson.GetInventory().contains(paramItem)){
            return  "message: A személynek nem sikerült használnia a tárgyat, mert a tárgy nincs benne az inventoryjában.";
        }
        paramPerson.UseItem(paramItem);

        return "message: A személynek sikerült használnia a tárgyat.";
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
        if (!(parameters.size() > 2) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty() || parameters.get(2).isEmpty()){
            throw new InvalidParameterException("ConnectTransistors: not enough parameter was given or one of them was empty.");
        }
        String personId = parameters.get(0);
        String transistor1 = parameters.get(1);
        String transistor2 = parameters.get(2);

        IPerson paramPerson = game.findPersonById(personId);
        Item paramT1 = game.findItemById(transistor1);
        Item paramT2 = game.findItemById(transistor2);
        if (paramPerson == null || paramT1 == null || paramT2 == null) {
            return "message: A hallgatónak nem sikerült párosítani a tranzisztorokat, mert a személy " + personId + " vagy tranzisztor " + transistor1 + ", " + transistor2 + "  nem létezik.";
        }
        paramPerson.ConnectTransistors((Transistor) paramT1, (Transistor) paramT2);

        return "message: A hallgatónak sikeresen sikerült párosítani a tranzisztorokat.";
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
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            throw new InvalidParameterException("EndTurn: no parameter was given or it was empty.");
        }
        String personId = parameters.get(0);
        IPerson paramPerson = game.findPersonById(personId);

        // check it chosen person has active turn
        if (!paramPerson.GetIsActiveTurn()) return "message: Nem az adott személynek van jelenleg köre.";
        paramPerson.EndTurn();

        return "message: Az adott személy sikeresen befejezte a körét.";
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
        if (!(parameters.size() > 1) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty()){
            throw new InvalidParameterException("SetCursed: not enough parameter was given or one of them was empty.");
        }
        String roomId = parameters.get(0);
        String isCursed = parameters.get(1);

        IRoom paramRoom = game.findRoomById(roomId);
        boolean paramIsCursed = Boolean.parseBoolean(isCursed);

        if (paramRoom == null){
            return "message: Nem sikerült beállítani a szoba elátkozottságát, mert a szoba " + roomId + " nem található.";
        }
        paramRoom.SetIsCursed(paramIsCursed);

        if (paramIsCursed){
            return 	"message: A megadott szoba jelenleg elátkozott.";
        } else{
            return 	"message: A megadott szoba jelenleg nem elátkozott.";
        }
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
        if (!(parameters.size() > 1) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty()){
            throw new InvalidParameterException("MergeRooms: not enough parameter was given or one of them was empty.");
        }
        String room1Id = parameters.get(0);
        String room2Id = parameters.get(1);

        IRoom paramRoom1 = game.findRoomById(room1Id);
        IRoom paramRoom2 = game.findRoomById(room2Id);
        if (paramRoom1 == null || paramRoom2 == null){
            return "message: A megadott szobákat nem sikerült összeolvasztani, mert a megadott szoba " + room1Id + " vagy szoba " + room2Id + " nem található.";
        }
        boolean isMerged = paramRoom1.MergeRooms(paramRoom2);

        if (isMerged){
            return "message: A két megadott szoba sikeresen összeolvadt.";
        }else{
            return "message: A megadott szobákat nem sikerült összeolvasztani.";
        }
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
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            throw new InvalidParameterException("SeparateRoom: no parameter was given or it was empty.");
        }
        String roomId = parameters.get(0);
        IRoom paramRoom = game.findRoomById(roomId);

        if (paramRoom == null){
            return "message: A megadott szobát nem sikerült szétválasztani, mert a megadott szoba " + roomId + " nem található.";
        }
        paramRoom.SeparateRoom();
        return "message: A megadott szobát sikeresen sikerült szétválasztani.";
    }


}

