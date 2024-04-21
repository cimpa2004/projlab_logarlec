package controller;

import modul.*;
import util.Logger;
import util.Reader;

import java.lang.reflect.Array;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.*;
import java.nio.file.*;

/** */
public class Game {
	/**
	 * Tárolja a hátralévő körök számát.
	 * */
	private int gameTimer;

	/**
	 * Tarolja, hogy milyen szintu legyen a logolas. Jelenleg ket szint van: 0 ha nincs, 1 ha van logolas
	 */
	private int logLevel;

	/**
	 * Tárolja, hogy a játék végetért-e.
	 * */
	private boolean isEndGame;

	/**
	 * Tárolja, hogy a játék determinisztikus-e (azaz történnek-e véletlenszerűen dolgok)
	 * */
	private boolean isGameDeterministic;

	/**
	 * A játékban szereplő Hallgatók és Oktatók sorrendjét tároló attribútum.
	 * */
	private ArrayList<IPerson> turnOrder;
	
	/**
	 * A soronkövetkező Személy található meg benne.
	 * */
	private IPerson currentTurn;

	/**
	 * Ha a játék véget ért (isEndGame == true), akkor jelzi, hogy a játékot melyik fél
	 * nyerte (false: oktatók, true: hallgatók)
	 * */
	private boolean winSide;

	/**
	 * Itt vannak eltárolva a játékban található szobák.
	 * */
	private ArrayList<IRoom> rooms = new ArrayList<>();


	public Game(){
		isGameDeterministic = false;
		this.logLevel = 1;
		Logger.setLogLevel(this.logLevel);

	}
	public Game(boolean isGameDeterministic, int logLevel){
		this.isGameDeterministic = isGameDeterministic;
		this.logLevel = logLevel;
		Logger.setLogLevel(this.logLevel);
	}

	/**
	 * Vissza adja az IPerson listat ami tartalmazza a jatekban levo szemelyeket
	 * @return A jatekot jatszo szemelyek
	 */
	public ArrayList<IPerson> GetTurnOrder() {
		return turnOrder;
	}

	/**
	 * Vissza adja azt IPersont akinek eppen a kore van
	 * @return A jelenleg aktiv korrel rendelkezo IPerson
	 */
	public IPerson GetCurrentTurn() {
		return currentTurn;
	}

	/**
	 * Vissza tudja adni a jatek idozitojet, vagyis hany kor van meg hatra a jatekbol
	 * @return Ahany kor meg hatra van a jatekbol
	 */
	public int GetGameTimer(){
		return gameTimer;
	}

	/**
	 * Vissza tudja adni, hogy a jatek veget ert e mar
	 * @return Ertek ami jelzi, hogy vege van-e mar a jateknak
	 */
	public boolean GetIsEndGame(){
		return isEndGame;
	}

	/**
	 * Ha vege van a jateknak, akkor ez vissza tudja adni azt, hogy ki nyert
	 * @return Ertek ami jelzi, hogy ki nyert, ha true akkor a hallgatok, egyebkent az oktatok
	 */
	public boolean GetWinSide(){
		return winSide;
	}

	/**
	 * Vissz adja a jatekokat tartalmazo szobakat
	 * @return Egy List<IRoom> ami tartalmazza a jatekban levo szobakat
	 */
	public ArrayList<IRoom> GetRooms(){
		return rooms;
	}

	/**
	 * Elindítja a játékot, incializálja a játékmenetet.
	 * */
	public void StartGame() {
		Logger.started(this, "StartGame");
		isEndGame = false;
		gameTimer = 10;
		NextTurn();
		Logger.finished(this, "StartGame");
	}

	public void CreateGame(boolean isDeterministic, String mapPath) {
		isGameDeterministic = isDeterministic;
		try {
			String contents = new String(Files.readAllBytes(Paths.get(mapPath)));
			JSONObject game = new JSONObject(contents);

			//Setup doors
			JSONArray doors = game.getJSONArray("doors");
			ArrayList<DoorSide> doorsList = new ArrayList<DoorSide>();
			ArrayList<String> doorSideIDs = new ArrayList<String>();
			for(int i = 0; i < doors.length(); i++) {
				JSONObject d = doors.getJSONObject(i);
				DoorSide door = new DoorSide();
				door.SetCanBeOpened(d.getBoolean("canBeOpened"));
				doorsList.add(door);
				doorSideIDs.add(d.getString("id"));
			}

			//Set doorside pairs
			for(int i = 0; i < doorsList.size(); i++) {
				doorsList.get(i).SetPair(doorsList.get(doorSideIDs.indexOf(doors.getJSONObject(i).getString("pair"))));
			}

			//Setup rooms
			JSONArray rooms = game.getJSONArray("rooms");
			for(int i = 0; i < rooms.length(); i++) {
				JSONObject r = rooms.getJSONObject(i);
				Room room = new Room();
				room.SetPoisonDuration(r.getInt("poisonDuration"));
				room.SetIsCursed(r.getBoolean("isCursed"));
				room.SetIsSticky(r.getBoolean("isSticky"));
				room.SetNumberOfPeopleBeenToRoom(r.getInt("numberOfPeopleToRoom"));
				room.SetMaxCapacity(r.getInt("maxCap"));
				room.SetCurrentCapacity(r.getInt("currentCap"));

				//Students
				JSONArray students = r.getJSONArray("Students");
				for (int j = 0; j < students.length(); j++) {
					Student st = new Student(this);
					st.SetRoom(room);
					room.AddStudent(st);
				}

				//Instructors
				JSONArray instructors = r.getJSONArray("Instructors");
				for (int j = 0; j < instructors.length(); j++) {
					Instructor in = new Instructor();
					in.SetRoom(room);
					room.AddInstructor(in);
				}

				//Janitors
				JSONArray janitors = r.getJSONArray("Janitors");
				for(int j = 0; j < janitors.length(); j++) {
					Janitor jan = new Janitor();
					jan.SetRoom(room);
					room.AddJanitor(jan);
				}

				//Items

				//SlideRule
				JSONArray slideRules = r.getJSONArray("SlideRules");
				for (int j = 0; j < slideRules.length(); j++) {
					SlideRule sl = new SlideRule();
					sl.SetRoom(room);
					sl.SetIsFake(slideRules.getJSONObject(j).getBoolean("fake"));
					room.AddItem(sl);
				}

				//TVSZ
				JSONArray tvszs = r.getJSONArray("TVSZs");
				for (int j = 0; j < tvszs.length(); j++) {
					TVSZ t = new TVSZ();
					t.SetRoom(room);
					t.SetUsesLeft(tvszs.getJSONObject(j).getInt("durability"));
					t.SetIsFake(tvszs.getJSONObject(j).getBoolean("fake"));
					room.AddItem(t);
				}

				//FFP2Mask
				JSONArray ffp2masks = r.getJSONArray("FFP2Masks");
				for (int j = 0; j < ffp2masks.length(); j++) {
					FFP2Mask fp = new FFP2Mask();
					fp.SetRoom(room);
					fp.SetDurability(ffp2masks.getJSONObject(j).getInt("durability"));
					fp.SetIsFake(ffp2masks.getJSONObject(j).getBoolean("fake"));
					room.AddItem(fp);
				}

				//WetTableClothes
				JSONArray wetTableClothes = r.getJSONArray("wetTableClothes");
				for (int j = 0; j < wetTableClothes.length(); j++) {
					WetTableCloth wt = new WetTableCloth();
					wt.SetRoom(room);
					wt.SetDurability(wetTableClothes.getJSONObject(j).getInt("durability"));
					room.AddItem(wt);
				}

				//HolyBeerCup
				JSONArray holyBeerCups = r.getJSONArray("holyBeerCups");
				for (int j = 0; j < holyBeerCups.length(); j++) {
					HolyBeerCup hb = new HolyBeerCup();
					hb.SetRoom(room);
					hb.SetDurability(holyBeerCups.getJSONObject(j).getInt("durability"));
					room.AddItem(hb);
				}

				//AirFresheners
				JSONArray airFresheners = r.getJSONArray("airFresheners");
				for(int j = 0; j < airFresheners.length(); j++) {
					AirFreshener af = new AirFreshener();
					af.SetRoom(room);
					if (airFresheners.getJSONObject(j).getBoolean("isActivated")) af.Activate();
					room.AddItem(af);
				}

				//Camemberts
				JSONArray camemberts = r.getJSONArray("camemberts");
				for (int j = 0; j < camemberts.length(); j++) {
					Camembert cb = new Camembert();
					cb.SetRoom(room);
					if (camemberts.getJSONObject(j).getBoolean("isActivated")) cb.Activate();
					room.AddItem(cb);
				}

				//Transistors
				JSONArray transistors = r.getJSONArray("Transistors");
				for (int j = 0; j < transistors.length(); j++) {
					Transistor tr = new Transistor();
					tr.SetRoom(room);
					room.AddItem(tr);
				}

				//Set Doors to Rooms and Rooms to Doors
				JSONArray roomDoors = r.getJSONArray("Doors");
				for (int j = 0; j < roomDoors.length(); j++) {
					DoorSide dPair = doorsList.get(doorSideIDs.indexOf(roomDoors.getJSONObject(j).getString("id")));
					room.AddDoor(dPair);
					dPair.SetRoom(room);
				}
				this.rooms.add(room);
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lezárja a játékot, a paraméterben megkapott érték alapján pedig a nyertes oldalt.
	 *
	 * @param winSide 0 érték ha Oktató, 1 ha Hallgató
	 * */
	public void EndGame(boolean winSide) {
		Logger.started(this, "EndGame", winSide);
		isEndGame = true;
		this.winSide = winSide;
		Logger.finished(this, "EndGame", winSide);
	}
	
	/**
	 * Átlépteti a játékot a következő körre.
	 * */
	public void NextTurn() {
		Logger.started(this, "NextTurn");

		boolean anyStudentsAlive = AnyStudentsAlive();
		if(!anyStudentsAlive){
			EndGame(false);
		}
		int currentIndex = turnOrder.indexOf(currentTurn);
		currentIndex++;
		if(currentIndex >= turnOrder.size()){
			currentIndex = 0;
			gameTimer--;
			if(gameTimer == 0)
				EndGame(false);
		}

		boolean anyStudentsAlive = AnyStudentsAlive();
		if(!anyStudentsAlive || gameTimer == 0){
			EndGame(false);
		}

		currentTurn = turnOrder.get(currentIndex);
		currentTurn.StartTurn();

		/*boolean toUse = Reader.GetBooleanInput("Legyen e elátkozott minden szoba? ");
		if (toUse && !rooms.isEmpty())
			for (IRoom r : this.rooms){
				r.SetIsCursed(true);
			}*/
		Logger.finished(this, "NextTurn");
	}
	
	/** 
	 * Megnézi, hogy van-e még élő Hallgató a játékban.
	 *
	 * @return true abban az esetben ha van élő játékos, false ha nincs további játékos.
	 * */
	public boolean AnyStudentsAlive() {
		Logger.started(this, "AnyStudentsAlive");
		/*boolean toReturn = Reader.GetBooleanInput("Van még élő Student? ");
		if (!toReturn)
			this.EndGame(false);*/
		boolean anyStudentAlive = false;
		for(IRoom room : rooms) {
			for(Student student : room.GetStudents()){
				if(student.GetIsAlive()) {
					anyStudentAlive = true;
					break;
				}
			}
		}
		Logger.finished(this, "AnyStudentsAlive");
		return anyStudentAlive;
	}

	/**
	 * Hozzáadja azt a Személyt a turnOrder attribútumhoz, akit paraméterként kap.
	 *
	 * @param p Az hozzáadandó Személy.
	 * */
	public void AddToGame(Person p) {
		Logger.started(this, "AddToGame", p);
		turnOrder.add(p);
		Logger.finished(this, "AddToGame", p);
	}

	/**
	 * Hozzáadja a paraméterben kapott szobát a játékhoz.
	 *
	 * @param r Hozzáadandó szoba.
	 * */
	public void AddRoom(Room r) {
		Logger.started(this, "AddRoom", r);
		if (r != null)
			r.SetIsDeterministic(isGameDeterministic);
			this.rooms.add(r);
		Logger.finished(this, "AddRoom", r);
	}
	
	/**
	 * Eltávolítja a paraméterben átadott szobát a játékból.
	 *
	 * @param r  Eltávolítandó szoba.
	 * */
	public void RemoveRoom(Room r) {
		Logger.started(this, "RemoveRoom", r);
		Logger.finished(this, "RemoveRoom", r);
	}

	public boolean GetIsDeterministic(){
		return isGameDeterministic;
	}

	//TODO: csinálni egy UpdateNeighbors függvényt ami végigmegy az összes szobán és beállítja mindegyiknek a szomszédait
	// ezt lehet hivni játék létrehozása után, meg mergeRooms vagy seperateRoomsnál

	/**
	 * Id alapján megkeresi az adott DoorSidet, ammenyiben benne van vissza adja, egyébként null
	 * @param doorId A doorIdja amit keres
	 * @return Maga a DoorSide objekt vagy null
	 */
	public DoorSide findDoorById(String doorId){
		ArrayList<IRoom> rooms = GetRooms();
		for (IRoom room : rooms){
			for (DoorSide door : room.GetDoors()){
				if (door.GetId().equals(doorId)) {
					return door;
				}
			}
		}
		return null;
	}

	/**
	 * Id alapján megkeresi az adott Roomot, ammenyiben benne van vissza adja, egyébként null
	 * @param roomId A Room idja amit keres
	 * @return Maga a Room objekt vagy null
	 */
	public IRoom findRoomById(String roomId){
		for (IRoom room : GetRooms()){
			if (room.GetId().equals(roomId)){
				return room;
			}
		}
		return null;
	}

	/**
	 * Id alapján megkeresi az adott Persont, ammenyiben benne van vissza adja, egyébként null
	 * @param personId A personId amit keres
	 * @return Maga a Person objekt vagy null
	 */
	public IPerson findPersonById(String personId){
		ArrayList<IPerson> persons = GetTurnOrder();
		for (IPerson person : persons){
			if (person.GetId().equals(personId)){
				return person;
			}
		}
		return null;
	}

	/**
	 * Id alapján megkeresi az adott Itemet, ammenyiben benne van vissza adja, egyébként null
	 * @param itemId A doorIdja amit keres
	 * @return Maga az Item objekt vagy null
	 */
	public Item findItemById(String itemId){
		// items (ennek egyenlonek kene lennie (itemsFromPersons + itemsFromRooms)-val es 2 diszjunkt halmaz)
		//     items from persons
		for (IPerson person : GetTurnOrder()){
			for (Item item : person.GetInventory()){
				if (item.GetId().equals(itemId)) return item;
			}
		}
		//     items from rooms
		for (IRoom room : GetRooms()){
			for (Item item : room.GetItems()){
				if (item.GetId().equals(itemId)) return item;
			}
		}
		return null;
	}
}
