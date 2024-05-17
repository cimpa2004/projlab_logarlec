package controller;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import util.Logger;
import view.VItem;
import viewmodel.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.function.Function;


/**
 * Ez képes beolvasni az előre definált parancsokat átalános bemenetről vagy fájlból. A parancsokat a megadott jatekon vegrehajtja,
 * majd a parancsok kimenetet vissza adja.
 */
public class InputHandler implements ICInput {

    /**
     * Egy map ami az előre definiált parancsokhoz (Stringek) mappolja hozzá a végrehajtadó parancsok metódusait, melyek
     * egy Stringek listáját amik a parancs paraméterei, illetve egy Gamet varnak bemenetkent amelyen a parancs végrehajtódik
     * és visszatérnek egy String-el ami a parancs kimenete.
     */
    final private Map<String, Function<ArrayList<String>, String>> commandMap;
    private Game game;
    private ICInit icInit;
    private boolean isTesting;

    // Itt letreheozzuk es belerakjuk a mapba es konstruktorban nem kell kod ismetelni mindig
    {
        commandMap = new HashMap<>();
        commandMap.put("creategame", this::createGame);
        commandMap.put("startgame", this::startGame);
        commandMap.put("describegame", this::describeGame);
        commandMap.put("liststudents", this::listStudents);
        commandMap.put("listonstructors", this::listInstructors);
        commandMap.put("listkanitors", this::listJanitors);
        commandMap.put("describeroom", this::describeRoom);
        commandMap.put("describeperson", this::describePerson);
        commandMap.put("describeitem", this::describeItem);
        commandMap.put("describedoor", this::describeDoor);
        commandMap.put("pickup", this::pickup);
        commandMap.put("throw", this::throwItem);
        commandMap.put("move", this::move);
        commandMap.put("useitem", this::useItem);
        commandMap.put("connecttransistors", this::connectTransistors);
        commandMap.put("endturn", this::endTurn);
        commandMap.put("setcursed", this::setCursed);
        commandMap.put("mergerooms", this::mergeRooms);
        commandMap.put("separateroom", this::separateRoom);
    }

    public InputHandler(){
        isTesting = true;
    }
    public InputHandler(Game game) {
        isTesting = false;
        this.game = game;
    }


    /**
     * Az inputként megadott parancsot végrehajta a megadott játékon. Ha parancs játék létrehozása, akkor a paraméterként
     * megadott játékot hozza létre.
     * @param input A bemenet ami egy parancs megfelelő paraméterekkel, ha van
     * @return Vissza adja a parancs kimenetet
     */
    public String handleCommand(String input, boolean toLog) throws InvalidParameterException {
        String[] parts = input.split(" ");
        String command = parts[0].toLowerCase();
        String[] parameters = new String[parts.length - 1];
        System.arraycopy(parts, 1, parameters, 0, parameters.length);
        ArrayList<String> parameterList = new ArrayList<>(Arrays.asList(parameters));

        Function<ArrayList<String>, String> commandFunction = commandMap.get(command);
        if (commandFunction != null) {
            // Check if game is null, and if so, use the supplier to get a new Game object
            if (!command.equals("creategame") && this.game == null) {
                return "message: A parancs nem volt sikeres mert meg nem lett letrehozva jatek CreateGame paranccsal.";
            }
            String commandOutput = commandFunction.apply(parameterList);
            if (toLog) Logger.commandLog(commandOutput+"\n");
            return commandOutput;
        } else {
            return "message: A megadott parancs helytelen, nem szerepel a parancsok kozott.";
        }
    }

    public String handleCommand(String input) throws InvalidParameterException {
        String[] parts = input.split(" ");
        String command = parts[0].toLowerCase();
        String[] parameters = new String[parts.length - 1];
        System.arraycopy(parts, 1, parameters, 0, parameters.length);
        ArrayList<String> parameterList = new ArrayList<>(Arrays.asList(parameters));

        Function<ArrayList<String>, String> commandFunction = commandMap.get(command);
        if (commandFunction != null) {
            // Check if game is null, and if so, use the supplier to get a new Game object
            if (!command.equals("creategame") && this.game == null) {
                return "message: A parancs nem volt sikeres mert meg nem lett letrehozva jatek CreateGame paranccsal.";
            }
            String commandOutput = commandFunction.apply(parameterList);
            Logger.commandLog(commandOutput+"\n");
            return commandOutput;
        } else {
            return "message: A megadott parancs helytelen, nem szerepel a parancsok kozott.";
        }
    }

    public String handleCommand(String input, ICInit icInit) throws InvalidParameterException {
        this.icInit = icInit;
        return handleCommand(input);
    }


    /** Ez a metodus inicializalja a parameterkent megadott jatekot. Beallithato hogy a jatek determinisztikus legyen,
     * illetve megadhato egy elore definialt jatekterkep. Amennyiben nincs megadva jatekterkep akkor egy alap terkepet
     * general.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs (jatek nem jon letre,
     * vagyis null lesz).
     *
     * @param parameters Lehetseges parameterek: isDeterministic, gameMapPath (optional)
     * @return Vissza adja a parancs kimenetet
     */
    public String createGame(ArrayList<String> parameters) {
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            return "message: Kötelező paraméter hiányzik: isDeterministic";
        }
        String mapPath;
        if (parameters.size() > 1 && !parameters.get(1).isEmpty()){
            mapPath = parameters.get(1);
        } else{
            mapPath = "Tests/Test16/Map.json";
        }

        if (parameters.size() > 2 && !parameters.get(2).isEmpty()){
            String logLevel = parameters.get(2).toUpperCase();
            Logger.setLogLevel(logLevel);
        } else{
            Logger.setLogLevel(Logger.LogLevel.INPUT_HANDLER);
        }

        String defaultPath = new File(".").getAbsolutePath();
        defaultPath = defaultPath.substring(0, defaultPath.length() - 1);
        mapPath = defaultPath.contains("Logarlec") ? defaultPath  + mapPath :  defaultPath + "Logarlec/" + mapPath;

        boolean isDeterministic = Boolean.parseBoolean(parameters.get(0));

        if (isTesting) this.game = new Game(isDeterministic);

        try {
            String contents = new String(Files.readAllBytes(Paths.get(mapPath)));
            JSONObject gameJson = new JSONObject(contents);

            //Setup doors
            ArrayList<DoorSide> doorsList = new ArrayList<DoorSide>();
            ArrayList<String> doorSideIDs = new ArrayList<String>();
            if (gameJson.has("doors")) {
                JSONArray doors = gameJson.getJSONArray("doors");
                for (int i = 0; i < doors.length(); i++) {
                    JSONObject d = doors.getJSONObject(i);
                    DoorSide door = new DoorSide(d.getString("id"));
                    door.SetCanBeOpened(d.getBoolean("canBeOpened"));
                    doorsList.add(door);
                    doorSideIDs.add(d.getString("id"));
                }

                //Set doorside pairs
                for (int i = 0; i < doorsList.size(); i++) {
                    String doorPairId = doors.getJSONObject(i).getString("pair");
                    int indexOfPair = doorSideIDs.indexOf(doorPairId);
                    if (indexOfPair == -1) {
                        doorsList.get(i).SetPair(null);
                    } else{
                        DoorSide doorPair = doorsList.get(indexOfPair);
                        doorsList.get(i).SetPair(doorPair);
                    }
                }
            }

            //Setup rooms
            if (gameJson.has("rooms")) {
                JSONArray rooms = gameJson.getJSONArray("rooms");
                for (int i = 0; i < rooms.length(); i++) {
                    JSONObject r = rooms.getJSONObject(i);
                    Room room = new Room(r.getString("id"));
                    if (icInit != null) icInit.CreateVRoom(room);
                    if (r.has("poisonDuration")) room.SetPoisonDuration(r.getInt("poisonDuration"));
                    if (r.has("isCursed")) room.SetIsCursed(r.getBoolean("isCursed"));
                    if (r.has("isSticky")) room.SetIsSticky(r.getBoolean("isSticky"));
                    if (r.has("numberOfPeopleToRoom"))
                        room.SetNumberOfPeopleBeenToRoom(r.getInt("numberOfPeopleToRoom"));
                    if (r.has("maxCapacity")) room.SetMaxCapacity(r.getInt("maxCapacity"));
                    if (r.has("currentCapacity")) room.SetCurrentCapacity(r.getInt("currentCapacity"));

                    //Students
                    if (r.has("students")) {
                        JSONArray students = r.getJSONArray("students");
                        for (int j = 0; j < students.length(); j++) {
                            Student st = new Student(students.getJSONObject(j).getString("id"), game);
                            game.AddToGame(st);
                            room.AddStudent(st);

                            if (icInit != null) icInit.CreateVStudent(st,this);
                        }
                    }

                    //Instructors
                    if (r.has("instructors")) {
                        JSONArray instructors = r.getJSONArray("instructors");
                        for (int j = 0; j < instructors.length(); j++) {
                            Instructor in = new Instructor(instructors.getJSONObject(j).getString("id"), this.game);
                            game.AddToGame(in);
                            room.AddInstructor(in);
                            if (icInit != null) icInit.CreateVInstructor(in);
                        }
                    }

                    //Janitors
                    if (r.has("janitors")) {
                        JSONArray janitors = r.getJSONArray("janitors");
                        for (int j = 0; j < janitors.length(); j++) {
                            Janitor jan = new Janitor(janitors.getJSONObject(j).getString("id"),this.game);
                            game.AddToGame(jan);
                            room.AddJanitor(jan);
                            if (icInit != null) icInit.CreateVJanitor(jan);
                        }
                    }

                    //Items

                    //SlideRule
                    if (r.has("slideRules")) {
                        JSONArray slideRules = r.getJSONArray("slideRules");
                        for (int j = 0; j < slideRules.length(); j++) {
                            SlideRule sl = new SlideRule(slideRules.getJSONObject(j).getString("id"), game);
                            if (slideRules.getJSONObject(j).has("fake")){
                                sl.SetIsFake(slideRules.getJSONObject(j).getBoolean("fake"));
                            }
                            room.AddItem(sl);
                            if (icInit != null) icInit.CreateVSlideRule(sl);
                        }
                    }

                    //TVSZ
                    if (r.has("tvszs")) {
                        JSONArray tvszs = r.getJSONArray("tvszs");
                        for (int j = 0; j < tvszs.length(); j++) {
                            TVSZ t = new TVSZ(tvszs.getJSONObject(j).getString("id"));
                            if (tvszs.getJSONObject(j).has("durability")) {
                                t.SetDurability(tvszs.getJSONObject(j).getInt("durability"));
                            }
                            if (tvszs.getJSONObject(j).has("fake")) {
                                t.SetIsFake(tvszs.getJSONObject(j).getBoolean("fake"));
                            }
                            room.AddItem(t);
                            if (icInit != null) icInit.CreateVTVSZ(t);
                        }
                    }

                    //FFP2Mask
                    if (r.has("ffp2Masks")) {
                        JSONArray ffp2masks = r.getJSONArray("ffp2Masks");
                        for (int j = 0; j < ffp2masks.length(); j++) {
                            FFP2Mask fp = new FFP2Mask(ffp2masks.getJSONObject(j).getString("id"));
                            if (ffp2masks.getJSONObject(j).has("isActivated")) {
                                fp.SetIsActivated(ffp2masks.getJSONObject(j).getBoolean("isActivated"));
                            }
                            if (ffp2masks.getJSONObject(j).has("durability")) {
                                fp.SetDurability(ffp2masks.getJSONObject(j).getInt("durability"));
                            }
                            if (ffp2masks.getJSONObject(j).has("fake")) {
                                fp.SetIsFake(ffp2masks.getJSONObject(j).getBoolean("fake"));
                            }
                            room.AddItem(fp);
                            if (icInit != null) icInit.CreateVFFP2Mask(fp);
                        }
                    }

                    //WetTableClothes
                    if (r.has("wetTableClothes")) {
                        JSONArray wetTableClothes = r.getJSONArray("wetTableClothes");
                        for (int j = 0; j < wetTableClothes.length(); j++) {
                            WetTableCloth wt = new WetTableCloth(wetTableClothes.getJSONObject(j).getString("id"));
                            if (wetTableClothes.getJSONObject(j).has("isActivated")) {
                                wt.SetIsActivated(wetTableClothes.getJSONObject(j).getBoolean("isActivated"));
                            }
                            if (wetTableClothes.getJSONObject(j).has("durability")) {
                                wt.SetDurability(wetTableClothes.getJSONObject(j).getInt("durability"));
                            }
                            room.AddItem(wt);
                            if (icInit != null) icInit.CreateVWetTableCloth(wt);
                        }
                    }

                    //HolyBeerCup
                    if (r.has("holyBeerCups")) {
                        JSONArray holyBeerCups = r.getJSONArray("holyBeerCups");
                        for (int j = 0; j < holyBeerCups.length(); j++) {
                            HolyBeerCup hb = new HolyBeerCup(holyBeerCups.getJSONObject(j).getString("id"));
                            if (holyBeerCups.getJSONObject(j).has("isActivated")) {
                                hb.SetIsActivated(holyBeerCups.getJSONObject(j).getBoolean("isActivated"));
                            }
                            if (holyBeerCups.getJSONObject(j).has("durability")) {
                                hb.SetDurability(holyBeerCups.getJSONObject(j).getInt("durability"));
                            }
                            room.AddItem(hb);
                            if (icInit != null) icInit.CreateVHolyBeerCup(hb);
                        }
                    }

                    //AirFresheners
                    if (r.has("airFresheners")) {
                        JSONArray airFresheners = r.getJSONArray("airFresheners");
                        for (int j = 0; j < airFresheners.length(); j++) {
                            AirFreshener af = new AirFreshener(airFresheners.getJSONObject(j).getString("id"));
                            if (airFresheners.getJSONObject(j).has("isActivated")) {
                                if (airFresheners.getJSONObject(j).getBoolean("isActivated")) af.Activate();
                            }
                            room.AddItem(af);
                            if (icInit != null) icInit.CreateVAirFreshener(af);
                        }
                    }

                    //Camemberts
                    if (r.has("camemberts")) {
                        JSONArray camemberts = r.getJSONArray("camemberts");
                        for (int j = 0; j < camemberts.length(); j++) {
                            Camembert cb = new Camembert(camemberts.getJSONObject(j).getString("id"));
                            if (camemberts.getJSONObject(j).has("isActivated")) {
                                cb.SetIsActivated(camemberts.getJSONObject(j).getBoolean("isActivated"));
                            }
                            if (camemberts.getJSONObject(j).has("isActivated")) {
                                if (camemberts.getJSONObject(j).getBoolean("isActivated")) cb.Activate();
                            }
                            room.AddItem(cb);
                            if (icInit != null) icInit.CreateVCamembert(cb);
                        }
                    }

                    //Transistors
                    if (r.has("transistors")) {
                        JSONArray transistors = r.getJSONArray("transistors");
                        for (int j = 0; j < transistors.length(); j++) {
                            Transistor tr = new Transistor(transistors.getJSONObject(j).getString("id"));
                            room.AddItem(tr);
                            if (icInit != null) icInit.CreateVTransistor(tr);
                        }
                    }

                    //Set Doors to Rooms and Rooms to Doors
                    if (r.has("doors")) {
                        JSONArray roomDoors = r.getJSONArray("doors");
                        for (int j = 0; j < roomDoors.length(); j++) {
                            int doorIndex = doorSideIDs.indexOf(roomDoors.getString(j));
                            DoorSide dPair = doorsList.get(doorIndex);
                            dPair.SetRoom(room);
                            if (icInit != null) icInit.CreateVDoorSide(dPair);
                        }
                    }
                    game.AddRoom(room);
                }
            }
            // Set the neighbors of each Room
            for(IRoom room : game.GetRooms()){
                for(DoorSide door : room.GetDoors()){ // We iterate through every door of every room
                    DoorSide pair = door.GetPair();
                    if(pair != null){ // if the door has a pair
                        if(!room.GetNeighbors().contains(pair.GetRoom())){
                            // and if the other room isn't already in the neighbor list
                            room.GetNeighbors().add(pair.GetRoom());
                        }

                    }
                }
            }

        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return describeGame(new ArrayList<>());
    }

    /**
     * Elindítja a játékot, és jelzi, hogy ki a soron következő.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ebben az esetben ures a parameters lista
     * @return Vissza adja a parancs kimenetet
     */
    public String startGame(ArrayList<String> parameters) {
        if (game.GetTurnOrder().isEmpty()) return "message: Nincs személy a játékban";
        StringBuilder str = new StringBuilder();
        str.append("message: A játék elindult. A személykre vonatkozó parancsok közül azokra kell meghívni aki a soron következő, majd arra EndTurn paranccsal fejezhető be a kör.");
        str.append("\ncurrentTurn: ").append(game.GetCurrentTurn().GetID());
        game.StartGame();
        return str.toString();
    }

    /**
     * Kiírja a kimenetre a játék jelenlegi állapotát.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ebben az esetben ures a parameters lista
     * @return Vissza adja a parancs kimenetet
     */
    public String describeGame(ArrayList<String> parameters) {
        StringBuilder str = new StringBuilder();
        str.append("message: A letrejott jatek állapotai a többi mezőben.");

        // gameTimer
        str.append("\ngameTimer: ").append(game.GetGameTimer());

        // isEndGame, winSide
        boolean isGameEnded = game.GetIsEndGame();
        str.append("\nisEndGame: ").append(isGameEnded);
        if (isGameEnded){
            str.append("\nwinSide: ").append(game.GetWinSide());
        }

        // currentTurn
        IPerson currentTurn = game.GetCurrentTurn();
        if (currentTurn != null) str.append("\ncurrentTurn: ").append(currentTurn.GetID());
        else str.append("\ncurrentTurn: ").append("None");



        // turnOrder
        ArrayList<IPerson> turnOrder = game.GetTurnOrder();
        turnOrder.sort(Comparator.comparing(IPerson::GetID)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\nturnOrder: [");
        for (IPerson person : turnOrder){
            str.append(person.GetID());
            if(person != turnOrder.get(turnOrder.size()-1)) str.append(", ");
        }
        str.append("]");

        // rooms
        ArrayList<IRoom> rooms = game.GetRooms();
        rooms.sort(Comparator.comparing(IRoom::GetID)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\nrooms: [");
        for (IRoom room : rooms){
            str.append(room.GetID());
            if(room != rooms.get(rooms.size()-1)) str.append(", ");
        }
        str.append("]");

        // doors
        ArrayList<DoorSide> allDoors = new ArrayList<>();
        for (IRoom room : rooms){
            allDoors.addAll(room.GetDoors());
        }
        allDoors.sort(Comparator.comparing(DoorSide::GetID)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\ndoors: [");
        for (DoorSide door : allDoors){
            str.append(door.GetID());
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
        allItems.sort(Comparator.comparing(Item::GetID)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\nitems: [");
        for (Item item : allItems){
            str.append(item.GetID());
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
        students.sort(Comparator.comparing(Student::GetID)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\nstudents: [");
        for (Student student : students){
            str.append(student.GetID());
            if(student != students.get(students.size()-1)) str.append(", ");
        }
        str.append("]");
        //     instructors
        instructors.sort(Comparator.comparing(Instructor::GetID));
        str.append("\ninstructors: [");
        for (Instructor instructor : instructors){
            str.append(instructor.GetID());
            if(instructor != instructors.get(instructors.size()-1)) str.append(", ");
        }
        str.append("]");
        //     janitor
        janitors.sort(Comparator.comparing(Janitor::GetID));
        str.append("\njanitors: [");
        for (Janitor janitor : janitors){
            str.append(janitor.GetID());
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
     * @return Vissza adja a parancs kimenetet
     */
    public String listStudents(ArrayList<String> parameters) {
        StringBuilder str = new StringBuilder();
        str.append("message: A játékban résztvevő hallgatók a többi mezőben.");

        ArrayList<IRoom> rooms = game.GetRooms();
        // students
        ArrayList<Student> students = new ArrayList<>();
        for (IRoom room : rooms){
            students.addAll(room.GetStudents());
        }

        students.sort(Comparator.comparing(Student::GetID)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\nstudents: [");
        for (Student student : students){
            str.append(student.GetID());
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
     * @return Vissza adja a parancs kimenetet
     */
    public String listInstructors(ArrayList<String> parameters) {
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

        instructors.sort(Comparator.comparing(Instructor::GetID));
        str.append("\ninstructors: [");
        for (Instructor instructor : instructors){
            str.append(instructor.GetID());
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
     * @return Vissza adja a parancs kimenetet
     */
    public String listJanitors(ArrayList<String> parameters) {
        StringBuilder str = new StringBuilder();
        str.append("message: A játékban résztvevő oktatók a többi mezőben.");

        ArrayList<IRoom> rooms = game.GetRooms();
        // janitors
        ArrayList<Janitor> janitors = new ArrayList<>();
        for (IRoom room : rooms){
            janitors.addAll(room.GetJanitors());
        }

        janitors.sort(Comparator.comparing(Janitor::GetID));
        str.append("\njanitors: [");
        for (Janitor janitor : janitors){
            str.append(janitor.GetID());
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
     * @return Vissza adja a parancs kimenetet
     */
    public String describeRoom(ArrayList<String> parameters) {
        StringBuilder str = new StringBuilder();
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
        }
        String roomId = parameters.get(0);

        IRoom paramRoom = game.findRoomById(roomId);
        if (paramRoom == null){
            return "message: A megadott szoba " + roomId + " nem létezik.";
        }

        str.append("message: A megadott ").append(roomId).append(" szoba részletei a többi mezőben.");
        // items
        ArrayList<Item> items = paramRoom.GetItems();
        items.sort(Comparator.comparing(Item::GetID));
        str.append("\nitems: [");
        for (Item item : items){
            str.append(item.GetID());
            if(item != items.get(items.size()-1)) str.append(", ");
        }
        str.append("]");

        // doors
        ArrayList<DoorSide> doors = paramRoom.GetDoors();
        doors.sort(Comparator.comparing(DoorSide::GetID));
        str.append("\ndoors: [");
        for (DoorSide door : doors){
            str.append(door.GetID());
            if(door != doors.get(doors.size()-1)) str.append(", ");
        }
        str.append("]");

        // neighbors
        ArrayList<Room> neighbors = paramRoom.GetNeighbors();
        neighbors.sort(Comparator.comparing(Room::GetID));
        str.append("\nneighbors: [");
        if (!neighbors.isEmpty()){
            for (Room neighbor : neighbors){
                if (neighbor == null) continue;
                str.append(neighbor.GetID());
                if(neighbor != neighbors.get(neighbors.size()-1)) str.append(", ");
            }
        }

        str.append("]");

        // isCursed
        String isCursed = paramRoom.GetIsCursed() ? "true" : "false";
        str.append("\nisCursed: ").append(isCursed);

        // isSticky
        String isSticky = paramRoom.GetIsSticky() ? "true" : "false";
        str.append("\nisSticky: ").append(isSticky);

        // isGaseous
        int poisonDuration = paramRoom.GetPoisonDuration();
        str.append("\npoisonDuration: ").append(poisonDuration);

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
        students.sort(Comparator.comparing(Student::GetID)); // rendezes, hogy kiirasnal nem szamitson, kesobb egyszerubb stringkent osszehasonlitani
        str.append("\nstudents: [");
        for (Student student : students){
            str.append(student.GetID());
            if(student != students.get(students.size()-1)) str.append(", ");
        }
        str.append("]");
        //     instructors
        instructors.sort(Comparator.comparing(Instructor::GetID));
        str.append("\ninstructors: [");
        for (Instructor instructor : instructors){
            str.append(instructor.GetID());
            if(instructor != instructors.get(instructors.size()-1)) str.append(", ");
        }
        str.append("]");
        //     janitor
        janitors.sort(Comparator.comparing(Janitor::GetID));
        str.append("\njanitors: [");
        for (Janitor janitor : janitors){
            str.append(janitor.GetID());
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
     * @return Vissza adja a parancs kimenetet
     *
     */
    public String describePerson(ArrayList<String> parameters) {
        StringBuilder str = new StringBuilder();
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
        }
        String personId = parameters.get(0);

        IPerson paramPerson = game.findPersonById(personId);
        if (paramPerson == null) {
            return "message: A megadott hallgató/oktató/takarító " + personId + " nem létezik.";
        }

        // describe person
        str.append("message: A megadott ").append(personId).append(" személy részletei a többi mezőben.");

        // room
        if (paramPerson.GetRoom() == null) str.append("\nroom: ").append("None");
        else str.append("\nroom: ").append(paramPerson.GetRoom().GetID());

        // inventory
        ArrayList<Item> inventory = new ArrayList<>(paramPerson.GetInventory());
        inventory.sort(Comparator.comparing(Item::GetID));
        str.append("\ninventory: [");
        for (Item item : inventory){
            str.append(item.GetID());
            if(item != inventory.get(inventory.size()-1)) str.append(", ");
        }
        str.append("]");

        // wetTableClothes
        ArrayList<Item> wetTableClothes = new ArrayList<>();
        for (Defendable wetTableCloth : paramPerson.GetWetTableClothes()){
            wetTableClothes.add((Item)wetTableCloth);
        }
        wetTableClothes.sort(Comparator.comparing(Item::GetID));
        str.append("\nwetTableClothes: [");
        for (Item item : wetTableClothes){
            str.append(item.GetID());
            if(item != wetTableClothes.get(wetTableClothes.size()-1)) str.append(", ");
        }
        str.append("]");

        // tvszs
        ArrayList<Item> tvszs = new ArrayList<>();
        for (Defendable tvsz : paramPerson.GetTVSZs()){
            tvszs.add((Item)tvsz);
        }
        tvszs.sort(Comparator.comparing(Item::GetID));
        str.append("\ntvszs: [");
        for (Item item : tvszs){
            str.append(item.GetID());
            if(item != tvszs.get(tvszs.size()-1)) str.append(", ");
        }
        str.append("]");

        // holyBeerCups
        ArrayList<Item> holyBeerCups = new ArrayList<>();
        for (Defendable holyBeerCup : paramPerson.GetHolyBeerCups()){
            holyBeerCups.add((Item)holyBeerCup);
        }
        holyBeerCups.sort(Comparator.comparing(Item::GetID));
        str.append("\nholyBeerCups: [");
        for (Item item : holyBeerCups){
            str.append(item.GetID());
            if(item != holyBeerCups.get(holyBeerCups.size()-1)) str.append(", ");
        }
        str.append("]");

        // ffp2Masks
        ArrayList<Item> ffp2Masks = new ArrayList<>();
        for (Defendable ffp2Mask : paramPerson.GetFFP2Masks()){
            ffp2Masks.add((Item)ffp2Mask);
        }
        ffp2Masks.sort(Comparator.comparing(Item::GetID));
        str.append("\nffp2Masks: [");
        for (Item item : ffp2Masks){
            str.append(item.GetID());
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
     * @return Vissza adja a parancs kimenetet
     */
    public String describeItem(ArrayList<String> parameters) {
        StringBuilder str = new StringBuilder();
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
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
        String owner = ownerObj == null ? "None" : ownerObj.GetID();
        str.append("\nowner: ").append(owner);

        // room
        Room roomObj = paramItem.GetRoom();
        String room = roomObj == null ? "None" : roomObj.GetID();
        str.append("\nroom: ").append(room);

        // isActive
        String isActive = paramItem.GetIsActive() ? "true" : "false";
        str.append("\nisActivated: ").append(isActive);

        //durability
        if(paramItem instanceof Defendable) {
            Defendable d = (Defendable) paramItem;
            if (d.CanDefend() || !d.CanDefend()) {
                int dur = d.GetDurability();
                if(dur>0)
                    str.append("\ndurability: ").append(dur);
            }
        }

        // pair
        Transistor pairObj = paramItem.GetPair();
        String pair = pairObj == null ? "None" : pairObj.GetID();
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
     * @return Vissza adja a parancs kimenetet
     */
    public String describeDoor(ArrayList<String> parameters) {
        StringBuilder str = new StringBuilder();
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
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
        str.append("\npair: ").append(paramDoor.GetPair().GetID());

        // room
        str.append("\nroom: ").append(paramDoor.GetRoom().GetID());

        // neighbor
        str.append("\nneighbor: ").append(paramDoor.GetPair().GetRoom().GetID());

        return str.toString();
    }

    /**
     * Az adott személy a megadott tárgyat felveszi a szobából.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ket Stringet tartalmaz a lista: a személy aki felveszi a tárgyat, illetve maga a tárgy.
     * @return Vissza adja a parancs kimenetet
     */
    public String pickup(ArrayList<String> parameters) {
        if (!(parameters.size() > 1) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
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
            return "message: A tárgyat nem sikerült felvenni, nincs több hely a személy inventoryjában vagy ragacsos a szoba.";
        }
        return "message: A személynek sikerült felvenni a tárgyat amely már megtalálható az inventoryjában";
    }

    /**
     * Az adott személy a megadott tárgyat eldobja az inventoryjából a szobába ahol tartózkodik.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ket Stringet tartalmaz a lista: a személy aki eldobja a tárgyat, illetve maga a tárgy.
     * @return Vissza adja a parancs kimenetet
     */
    public String throwItem(ArrayList<String> parameters) {
        if (!(parameters.size() > 1) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
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

        return "message: A személynek sikerült eldobnia a tárgyat az inventoryjából, amely már megtálható a szobában.";
    }

    /**
     * A megadott személlyel a szobában található ajtóba megpróbál belépni.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Ket Stringet tartalmaz a lista: a személy amely lépni szeretne a szobák között, illetve az ajtó amin belépne.
     * @return Vissza adja a parancs kimenetet
     */
    public String move(ArrayList<String> parameters) {
        if (!(parameters.size() > 1) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
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
     * @return Vissza adja a parancs kimenetet
     */
    public String useItem(ArrayList<String> parameters) {
        if (!(parameters.size() > 1) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
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
        if (paramItem.GetIsActive()){
            return  "message: A személynek nem sikerült használnia a tárgyat, mert a tárgy már el van használva.";
        }
        paramPerson.UseItem(paramItem);

        //if its fake
        if(paramItem.GetIsFake()){
            return "message: A személynek nem sikerült használnia a tárgyat.";

        }

        return "message: A személynek sikerült használnia a tárgyat.";
    }

    /**
     * Összekapcsol két tranzisztort.
     * Mindig ellenorzni, hogy a megadott parameterket String listaja megfelelo szamu parametereket tartalmaz, illetve azt
     * hogy ezek a parameterek megfeleloen definialtak-e, amennyiben nem ugy nem hajtodik vegre a parancs.
     * @param parameters Harom Stringet tartalmaz a lista: a hallgató aki a tranzisztorokat kapcsolja össze, illetve a két tranzisztor melyet összekapcsolna.
     * @return Vissza adja a parancs kimenetet
     */
    public String connectTransistors(ArrayList<String> parameters) {
        if (!(parameters.size() > 2) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty() || parameters.get(2).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
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
     * @return Vissza adja a parancs kimenetet
     */
    public String endTurn(ArrayList<String> parameters) {
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
        }
        String personId = parameters.get(0);
        IPerson paramPerson = game.findPersonById(personId);

        // check if person exists in game
        if (paramPerson == null) return "message: A szemely " + personId + " nem letezik a jatekban.";
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
     * @return Vissza adja a parancs kimenetet
     */
    public String setCursed(ArrayList<String> parameters) {
        if (!(parameters.size() > 1) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
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
     * @return Vissza adja a parancs kimenetet
     */
    public String mergeRooms(ArrayList<String> parameters) {
        if (!(parameters.size() > 1) || parameters.get(0).isEmpty() || parameters.get(1).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
        }
        String room1Id = parameters.get(0);
        String room2Id = parameters.get(1);

        IRoom paramRoom1 = game.findRoomById(room1Id);
        IRoom paramRoom2 = game.findRoomById(room2Id);
        if (paramRoom1 == null || paramRoom2 == null){
            return "message: A megadott szobákat nem sikerült összeolvasztani, mert a megadott szoba " + room1Id + " vagy szoba " + room2Id + " nem található.";
        }
        boolean isMerged = game.MergeRooms((Room)paramRoom1, (Room)paramRoom2);

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

     * @return Vissza adja a parancs kimenetet
     */
    public String separateRoom(ArrayList<String> parameters) {
        if (parameters.isEmpty() || parameters.get(0).isEmpty()){
            return "message: Nem volt megadva elegendő paraméter.";
        }
        String roomId = parameters.get(0);
        IRoom paramRoom = game.findRoomById(roomId);

        if (paramRoom == null){
            return "message: A megadott szobát nem sikerült szétválasztani, mert a megadott szoba " + roomId + " nem található.";
        }
        game.SeparateRoom((Room)paramRoom);
        return "message: A megadott szobát sikeresen sikerült szétválasztani.";
    }


    @Override
    public void ThrowItem(String personID, IVItem item) {
        ArrayList<String> command = new ArrayList<>(Arrays.asList(personID, item.GetID()));
        throwItem(command);
    }

    @Override
    public void PickupItem(String personID, IVItem item) {
        ArrayList<String> command = new ArrayList<>(Arrays.asList(personID, item.GetID()));
        pickup(command);
    }

    @Override
    public void Move(String personID, IVDoorSide doorSide) {
        ArrayList<String> command = new ArrayList<>(Arrays.asList(personID, doorSide.GetID()));
        move(command);
    }

    @Override
    public void EndTurn(String personID) {
        ArrayList<String> command = new ArrayList<>(Arrays.asList(personID));
        endTurn(command);
    }

    @Override
    public void UseItem(String personID, IVItem item) {
        ArrayList<String> command = new ArrayList<>(Arrays.asList(personID, item.GetID()));
        useItem(command);
    }

    @Override
    public void Connect(String personID, IVTransistor t1, IVTransistor t2){
        ArrayList<String> command = new ArrayList<>(Arrays.asList(personID, t1.GetID(), t2.GetID()));
        connectTransistors(command);
    }

    /**
     * @return
     */
    @Override
    public List<IVItem> GetInventory(String personId) {

        IPerson paramPerson = game.findPersonById(personId);

        // check if person exists in game
        if (paramPerson == null) {
            throw new IllegalArgumentException("message: A szemely " + personId + " nem letezik a jatekban.");
        }

        return paramPerson.GetIVItems();
    }
}

