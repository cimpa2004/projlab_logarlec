package controller;

import model.*;
import util.Logger;
import view.*;
import viewmodel.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;

/** */
public class Game implements IVInit {

	/**
	 * Tarol egy ICInitet amivel letretud hozni V objektumokat a Viewban.
	 * */
	ICInit icInit;
	/**
	 * Tárolj IControlt amivel jelezhet a jateknak ha allapotaban valtozas tortent
	 * */
	IControl iControl;
	/**
	 * Tárol egy ICRoomot amivel jelezhet a Viewban levo V szobanak ha egy szoban tortent allapot valtozas
	 * */
	ICRoom icRoom;
	/**
	 * Tárolja a hátralévő körök számát.
	 * */
	private int gameTimer;

	/**
	 * Tarolja, hogy milyen szintu legyen a logolas. Jelenleg ket szint van: 0 ha nincs, 1 ha van logolas
	 */
	private Logger.LogLevel logLevel;

	/**
	 * InputHandler amivel lehet kezelni
	 */
	private InputHandler inputHandler;

	/**
	 * Tarolja, hogy mennyi elo Student van a jatekban. Ez az ertek StartGamenel inicializalodik es amikor egy Student meghal akkor a Die
	 * metodusa csokkenti ezt az erteket
	 */
	private int numberOfAliveStudents;

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
		isEndGame = false;
		gameTimer = 10;

		this.rooms = new ArrayList<>();
		this.turnOrder = new ArrayList<>();
		inputHandler = new InputHandler(this);
	}

	public Game(boolean isGameDeterministic){
		this.isGameDeterministic = isGameDeterministic;
		isEndGame = false;
		gameTimer = 10;

		this.rooms = new ArrayList<>();
		this.turnOrder = new ArrayList<>();
		inputHandler = new InputHandler(this);
	}
	public Game(boolean isGameDeterministic, Logger.LogLevel logLevel){
		this.isGameDeterministic = isGameDeterministic;
		this.logLevel = logLevel;
		Logger.setLogLevel(this.logLevel);
		isEndGame = false;
		gameTimer = 10;

		this.rooms = new ArrayList<>();
		this.turnOrder = new ArrayList<>();
		inputHandler = new InputHandler(this);
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
	 * Vissza adja a jatekhoz tartozo IControlt
	 * @return a jatekhoz tartozo IControl
	 */
	public IControl GetIControl(){
		return this.iControl;
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
	 * Vissza adja a jatek InputHandleret, ezen lehet parancsokat hivni a jatekon.
	 */
	public InputHandler GetInputHandler(){
		return inputHandler;
	}

	/**
	 * Vissz adja a jatekokat tartalmazo szobakat
	 * @return Egy List<IRoom> ami tartalmazza a jatekban levo szobakat
	 */
	public ArrayList<IRoom> GetRooms(){
		return rooms;
	}


	/**
	 * Beallit a jatekhoz egy ICInitet, e fuggveny hivas nekul a grafikus megjelenites nem mukodik
	 * @param icInit megadott ICInit
	 */
	public void SetICInit(ICInit icInit){
		this.icInit = icInit;
	}
	/**
	 * Beallit a jatekhoz egy IControlt, e fuggveny hivas nekul a grafikus megjelenites nem mukodik
	 * @param icControl megadott IControl
	 */
	public void SetIControl(IControl icControl){
		this.iControl = icControl;
	}
	/**
	 * Beallit a jatekhoz egy ICRoomt, e fuggveny hivas nekul a grafikus megjelenites nem mukodik
	 * @param icRoom megadott ICRoom
	 */
	public void SetICRoom(ICRoom icRoom){
		this.icRoom = icRoom;
	}

	public void NotifyStudentDied(){
		Logger.startedModel(this, "NotifyStudentDied");
		numberOfAliveStudents--;
		Logger.finishedModel(this, "NotifyStudentDied");
	}


	@Override
	public void AddStudent(String personID) {
		Logger.startedModel(this, "AddStudent", personID);
		Student newStudent = new Student(personID,this);
		rooms.get(0).AddStudent(newStudent);
		if (icInit != null) icInit.CreateVStudent(newStudent, inputHandler);

		//TODO TEST
		FillUpInventory(newStudent);
		//TODO TEST

		turnOrder.add(0, newStudent);
		currentTurn = newStudent;
		Logger.commandLog("message: Hallgato hozza lett adva a jatekhoz a kovetkezo ID-vel " + personID + "\n");
		Logger.finishedModel(this, "AddStudent", personID);

	}

	@Override
	public void RemoveStudent(String personID) {
		Logger.startedModel(this, "RemoveStudent");
		IPerson student = findPersonById(personID);
		RemoveFromGame(student);
		student.GetRoom().RemoveStudent((Student) student);
		Logger.finishedModel(this, "RemoveStudent");
	}

	/**
	 * Elindítja a játékot, incializálja a játékmenetet.
	 * */
	public void StartGame() {
		Logger.startedModel(this, "StartGame");
		isEndGame = false;
		gameTimer = 10;
		numberOfAliveStudents = 0;
		for(IRoom room : rooms) {
			for(Student student : room.GetStudents()){
				if(student.GetIsAlive()) {
					numberOfAliveStudents++;
				}
			}
		}
		if (currentTurn != null) currentTurn.StartTurn();
		else throw new RuntimeException("No person was added to game when game was started.");

		Logger.finishedModel(this, "StartGame");
	}

	@Override
	public void CreateGame(String mapPathJSON, ICInit icInit, IControl iControl, ICRoom icRoom) {
		Logger.startedModel(this, "CreateGame", mapPathJSON, icInit, iControl, icRoom);
		this.iControl = iControl;
		this.icInit = icInit;
		this.icRoom = icRoom;
		String command = "CreateGame " + "false " + mapPathJSON + " " + logLevel.toString();
		inputHandler.handleCommand(command, icInit);
		Logger.finishedModel(this, "CreateGame", mapPathJSON, icInit, iControl, icRoom);
	}

	/**
	 * Letrehoz egy jatekot, szobakkal, ajtokat, itemekkel, personekkel. Utana lehet hozza adni a jatekhoz
	 * tovabbi personeket. A logLevel DISABLED (ha szukseg van ra at lehet adni parameterkent a CreateGamenek).
	 */
	public void CreateGame(String mapPathJSON) {
		Logger.startedModel(this, "CreateGame", mapPathJSON);
		CreateGame(mapPathJSON, null, null, null);
		Logger.finishedModel(this, "CreateGame", mapPathJSON);
	}

	/**
	 * Lezárja a játékot, a paraméterben megkapott érték alapján pedig a nyertes oldalt.
	 *
	 * @param winSide 0 érték ha Oktató, 1 ha Hallgató
	 * */
	public void EndGame(boolean winSide) {
		Logger.startedModel(this, "EndGame", winSide);
		isEndGame = true;
		this.winSide = winSide;
		if (iControl != null){
			if (winSide) iControl.StudentWin();
			else iControl.InstructorWin();
		}
		Logger.finishedModel(this, "EndGame", winSide);
	}
	
	/**
	 * Átlépteti a játékot a következő körre.
	 * */
	public void NextTurn() {
		Logger.startedModel(this, "NextTurn");
		if (isEndGame){
			Logger.finishedModel(this, "NextTurn");
			return;
		}
		if(turnOrder.isEmpty()) System.err.println("Error: nincs hozza adva szemely a jatekhoz.");
		else if (currentTurn == null) currentTurn = turnOrder.get(0);

		boolean anyStudentsAlive = AnyStudentsAlive();
		if(!anyStudentsAlive){
			EndGame(false);
			Logger.finishedModel(this, "NextTurn");
			return;
		}

		int currentIndex = turnOrder.indexOf(currentTurn);
		currentIndex++;
		if(currentIndex >= turnOrder.size()){
			currentIndex = 0;
			gameTimer--;
		}
		if(gameTimer == 0){
			EndGame(false);
			Logger.finishedModel(this, "NextTurn");
			return;
		}

		currentTurn = turnOrder.get(currentIndex);
		if (!currentTurn.GetIsAlive()){
			Logger.finishedModel(this, "NextTurn");
			NextTurn();
			return;
		}

		if (!isGameDeterministic){
			RandomSeperateRooms();
			RandomMergeRooms();
		}
		ToggleDoorForCursedRooms();

		currentTurn.StartTurn();

		Logger.finishedModel(this, "NextTurn");
	}

	public void ToggleDoorForCursedRooms(){
		for(IRoom room : rooms){
			if(room.GetIsCursed()) room.ToggleDoorsVisible();
		}
	}

	/**
	 * 25% esellyel megprobal szetvalasztani egy szobat
	 */
	public void RandomSeperateRooms(){
		Random rnd = new Random();
		int rndNum = rnd.nextInt(4);
		if (rndNum == 1){
			for(IRoom room : this.rooms){
				boolean isSeparated = SeparateRoom((Room) room);
				if (isSeparated) return;
			}
		}
	}

	/**
	 * 25% esellyel megprobal osszeolvasztani egy szobat
	 */
	public void RandomMergeRooms(){
		Random rnd = new Random();
		int rndNum = rnd.nextInt(4);
		if (rndNum == 1){
			for(IRoom room : this.rooms){
				for(DoorSide doorSide : room.GetDoors()){
					if(doorSide.GetPair() != null && doorSide.GetPair().GetRoom() != null){
						boolean isMerged = MergeRooms((Room) room, doorSide.GetPair().GetRoom());
						if (isMerged) return;
					}
				}
			}
		}
	}
	
	/** 
	 * Megnézi, hogy van-e még élő Hallgató a játékban.
	 *
	 * @return true abban az esetben ha van élő játékos, false ha nincs további játékos.
	 * */
	public boolean AnyStudentsAlive() {
		Logger.startedModel(this, "AnyStudentsAlive");
		Logger.finishedModel(this, "AnyStudentsAlive");
		return numberOfAliveStudents > 0;
	}

	/**
	 * A szoba két szobára válik. Mindkét szoba maximum kapacitása egyenlő lesz az eredeti befogadóképességével,
	 * valamint a mérgesgáz- és elátkozottság attribútumok és a szobák szomszédjai is lemásolódnak.
	 * Az két új szoba közés is kerül egy ajtó.
	 * Csak akkor hívódik meg, ha a szoba aktuális kapacitása jelenleg 0.
	 * A szobában lévő tárgyak a két szoba között véletlenszerűen lesznek elszórva.
	 *
	 * @return Igaz ha sikerült osztódnia a Szobának és hamis ha nem.
	 * */
	public boolean SeparateRoom(Room r1) {
		Logger.startedModel(this, "SeparateRoom");
		// A Room csak akkor tud osztódni ha nincs egy személy se benne.
		if(r1.GetCurrentCapacity() == 0)
		{
			Room r2 = new Room();
			r2.CloneAttributes(r1);
			for (DoorSide d : r1.GetDoors())
			{
				DoorSide dCopy = new DoorSide();
				dCopy.CloneAttributes(d);
				DoorSide d2 = d.GetPair();
				DoorSide d2Copy = new DoorSide();
				d2Copy.CloneAttributes(d2);
				dCopy.ConnectDoors(d2Copy);
			}

			DoorSide dConnect1 = new DoorSide();
			DoorSide dConnect2 = new DoorSide();
			dConnect2.SetPair(dConnect1);
			dConnect1.SetPair(dConnect2);
			r1.AddDoor(dConnect1);
			r2.AddDoor(dConnect2);

			ArrayList<Item> itemsCopy = new ArrayList<Item>(r1.GetItems());
			if(!isGameDeterministic)
				for (Item i: itemsCopy){
					if(r1.RandomBool()){
						r2.AddItem(i);
						r1.RemoveItem(i);
					}
				}
			r1.AddNeighbor(r2);
			r2.AddNeighbor(r1);
			rooms.add(r2);
			if (icRoom != null) icRoom.Split(r1,r2);
			return true;
		}

		// A Room nem tud osztódni, ha tartózkodik valaki a Room -ban.
		Logger.finishedModel(this, "SeparateRoom");
		return false;
	}

	/**
	 * Ezen metódus összeolvasztja a szobát a paraméterben kapott másik szobával.
	 * A maximum kapacitása a nagyobb szoba befogadóképességével lesz egyenlő,
	 * a mérgesgáz ideje a hosszabb ideig tartó idő lesz, valamint elátkozott lesz, ha
	 * legalább az egyik szoba elátkozott. Az új szoba szomszédjai a régi két szoba szomszédjainak uniója.
	 * Csak akkor hívódik meg, ha mindkét szoba aktuális kapacitása jelenleg 0.
	 * A szobákban lévő tárgyak az új szobába kerülnek.
	 *
	 * @param r2 A Room, amivel a jelenlegi Room -ot összeolvasztja a metódus.
	 * */
	public boolean MergeRooms(Room r1, Room r2) {
		Logger.startedModel(this, "MergeRooms", r2);

		// Csak akkor egyesíthetünk két szobát, ha egyikben sem tartózkodik egy Person sem.
		if(r1.GetCurrentCapacity() == 0 && r2.GetCurrentCapacity() == 0){

			// Az egyesült szoba maximális kapacitása a két szoba
			// maximális kapacitása közül a nagyobbik lesz.
			if(r2.GetMaxCapacity() > r1.GetMaxCapacity()){
				r1.SetMaxCapacity(r2.GetMaxCapacity());
			}

			// Ha az egyik szoba is elátkozott, akkor
			// az egyesült szoba is az lesz.
			if(r2.GetIsCursed()){
				r1.SetIsCursed(r2.GetIsCursed());
			}

			// Az egyesült szoba poisonDuration változója a két szoba
			// poisonDuration -je közül a nagyobbik lesz.
			if(r2.GetPoisonDuration() > r1.GetPoisonDuration()){
				r1.SetPoisonDuration(r2.GetPoisonDuration());
			}

			// Minden az r2 -ben lévő tárgyat áthelyezünk az r1 -be.
			ArrayList<Item> r2ItemsCopy = new ArrayList<>(r2.GetItems());
			for(Item item : r2ItemsCopy){
				r2.RemoveItem(item);
				r1.AddItem(item);
			}

			// Az ajtók közti összeköttetéseket frissíteni kell
			// Végigmegyünk a beolvasztandó (r2) szoba összes DoorSide -ján.
			// Iterátort érdemes használnunk, mivel a ciklus futása közben szeretnénk
			// változtatni a listán.

			ArrayList<DoorSide> r2DoorsCopy = new ArrayList<>(r2.GetDoors());
			Iterator<DoorSide> r2DoorsCopyIter = r2DoorsCopy.iterator();

			while(r2DoorsCopyIter.hasNext()){
				DoorSide doorSide = r2DoorsCopyIter.next();

				// Ha egy DoorSide az r1 -el köti össze az r2 -t, akkor
				// már nem lesz szükség ezekre a félajtókra, hiszen az r2 -t
				// az r1 -be olvasztjuk.
				if(doorSide.GetPair().GetRoom() == r1){
					r1.RemoveDoor(doorSide.GetPair());
					doorSide.SetRoom(null);
					doorSide.SetRoom(null);
					r2DoorsCopyIter.remove();
				}else{
					// Ha egy DoorSide egy másik szobával köti össze az r2 -t,
					// akkor azt át kell alakítanunk, hogy a másik szoba az r1 -el
					// legyen összeköttetésben
					doorSide.SetRoom(r1);
					r2DoorsCopyIter.remove();
				}
				r1.RemoveNeighbor(r2);
			}
			RemoveRoom(r2);

			if (icRoom != null) icRoom.Merge(r1, r2);
			Logger.finishedModel(this, "MergeRooms", r2);
			return true;
		}

		// Ha van bárki is az egyik szobában, akkor nem hajtódik
		// végre az egyesítés.
		Logger.finishedModel(this, "MergeRooms", r2);
		return false;
	}

	/**
	 * Hozzáadja azt a Személyt a turnOrder attribútumhoz, akit paraméterként kap.
	 *
	 * @param p Az hozzáadandó Személy.
	 * */
	public void AddToGame(IPerson p) {
		Logger.startedModel(this, "AddToGame", p);
		turnOrder.add(p);
		if(currentTurn == null) currentTurn = p;
		Logger.finishedModel(this, "AddToGame", p);
	}

	/**
	 * Kiveszi azt a Személyt a, akit paraméterként kap.
	 *
	 * @param p A torlendo Személy.
	 * */
	public void RemoveFromGame(IPerson p) {
		Logger.startedModel(this, "RemoveFromGame", p);
		turnOrder.remove(p);
		Logger.finishedModel(this, "RemoveFromGame", p);
	}

	/**
	 * Hozzáadja a paraméterben kapott szobát a játékhoz.
	 *
	 * @param r Hozzáadandó szoba.
	 * */
	public void AddRoom(Room r) {
		Logger.startedModel(this, "AddRoom", r);
		if (r != null){
			r.SetIsDeterministic(isGameDeterministic);
			this.rooms.add(r);
		}
		Logger.finishedModel(this, "AddRoom", r);
	}
	
	/**
	 * Eltávolítja a paraméterben átadott szobát a játékból.
	 *
	 * @param r  Eltávolítandó szoba.
	 * */
	public void RemoveRoom(Room r) {
		Logger.startedModel(this, "RemoveRoom", r);
		rooms.remove(r);
		Logger.finishedModel(this, "RemoveRoom", r);
	}

	public boolean GetIsDeterministic(){
		return isGameDeterministic;
	}


	/**
	 * Id alapján megkeresi az adott DoorSidet, ammenyiben benne van vissza adja, egyébként null
	 * @param doorId A doorIdja amit keres
	 * @return Maga a DoorSide objekt vagy null
	 */
	public DoorSide findDoorById(String doorId){
		ArrayList<IRoom> rooms = GetRooms();
		for (IRoom room : rooms){
			for (DoorSide door : room.GetDoors()){
				if (door.GetID().equals(doorId)) {
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
			if (room.GetID().equals(roomId)){
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
			if (person.GetID().equals(personId)){
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
				if (item.GetID().equals(itemId)) return item;
			}
		}
		//     items from rooms
		for (IRoom room : GetRooms()){
			for (Item item : room.GetItems()){
				if (item.GetID().equals(itemId)) return item;
			}
		}
		return null;
	}

	//TODO JUST A TEST METHOD
	public void FillUpInventory(Student student){
		Room defaultRoom = new Room();

		AirFreshener af = new AirFreshener("TestAirFreshener");
		af.SetRoom(defaultRoom);
		VAirFreshener vaf = new VAirFreshener(af);
		vaf.SetOwner(student.GetIVStudentUpdate());
		af.SetIVItemUpdate(vaf);
		student.AddToInventory(af);

		Transistor t = new Transistor("TestTransistor");
		t.SetRoom(defaultRoom);
		VTransistor vt = new VTransistor(t);
		vt.SetOwner(student.GetIVStudentUpdate());
		t.SetIVItemUpdate(vt);
		student.AddToInventory(t);


		Transistor t2 = new Transistor("TestTransistor2");
		t2.SetRoom(defaultRoom);
		VTransistor vt2 = new VTransistor(t2);
		vt2.SetOwner(student.GetIVStudentUpdate());
		t2.SetIVItemUpdate(vt2);
		student.AddToInventory(t2);

		FFP2Mask mask = new FFP2Mask("TestMask");
		mask.SetRoom(defaultRoom);
		VFFP2Mask vmask = new VFFP2Mask(mask);
		vmask.SetOwner(student.GetIVStudentUpdate());
		mask.SetIVItemUpdate(vmask);
		student.AddToInventory(mask);

		/*HolyBeerCup hbc = new HolyBeerCup("TestHBC");
		hbc.SetRoom(defaultRoom);
		VHolyBeerCup vhbc = new VHolyBeerCup(hbc);
		vhbc.SetOwner(student.GetIVStudentUpdate());
		hbc.SetIVItemUpdate(vhbc);
		student.AddToInventory(hbc);*/

		/*Camembert cam = new Camembert(("TestCamembert"));
		cam.SetRoom(defaultRoom);
		VCamembert vcam = new VCamembert(cam);
		vcam.SetOwner(student.GetIVStudentUpdate());
		cam.SetIVItemUpdate(vcam);
		student.AddToInventory(cam);*/

		/*TVSZ tvsz = new TVSZ("TestTVSZ");
		tvsz.SetRoom(defaultRoom);
		VTVSZ vtvsz = new VTVSZ(tvsz);
		vtvsz.SetOwner(student.GetIVStudentUpdate());
		tvsz.SetIVItemUpdate(vtvsz);
		student.AddToInventory(tvsz);*/

		WetTableCloth wtc = new WetTableCloth("TestWTC");
		wtc.SetRoom(defaultRoom);
		VWetTableCloth vwtc = new VWetTableCloth(wtc);
		vwtc.SetOwner(student.GetIVStudentUpdate());
		wtc.SetIVItemUpdate(vwtc);
		student.AddToInventory(wtc);

		/*SlideRule sr = new SlideRule("TestFakeSlideRule", this);
		sr.SetRoom(defaultRoom);
		sr.SetIsFake(true);
		VSlideRule vsr = new VSlideRule(sr);
		vsr.SetOwner(student.GetIVStudentUpdate());
		sr.SetIVItemUpdate(vsr);
		student.AddToInventory(sr);*/
	}
}
