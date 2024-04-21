package controller;

import modul.*;
import util.Logger;
import util.Reader;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
	 * Tárolja, hogy a játék determinisztikus legyen-e
	 */
	private boolean isGameDeterministic;
	
	/**
	 * Tárolja, hogy a játék végetért-e.
	 * */
	private boolean isEndGame;

	/**
	 * Miutan vege a jateknak, ez mutatja, hogy melyik oldal nyert. 1 ha a hallgatok, 0 ha az oktatok
	 * */
	private boolean winSide;
	
	/**
	 * A játékban szereplő Hallgatók és Oktatók sorrendjét tároló attribútum.
	 * */
	private ArrayList<IPerson> turnOrder;
	
	/**
	 * A soronkövetkező Személy található meg benne.
	 * */
	private IPerson currentTurn;

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
		// TODO
		Logger.finished(this, "StartGame");
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
		// TODO csak akkor menjen ha még tart a játék (vége)
		int currentIndex = turnOrder.indexOf(currentTurn);
		currentIndex++;
		if(currentIndex >= turnOrder.size()){
			currentIndex = 0;
			gameTimer = gameTimer - 1; // mert ekkor mindenki lepett
		}

		boolean anyStudentsAlive = AnyStudentsAlive();
		if(!anyStudentsAlive || gameTimer == 0){
			EndGame(false);
		}

		currentTurn = turnOrder.get(currentIndex);
		currentTurn.StartTurn();


		boolean toUse = Reader.GetBooleanInput("Legyen e elátkozott minden szoba? ");
		if (toUse && !rooms.isEmpty())
			for (IRoom r : this.rooms){
				r.SetIsCursed(true);
			}
		Logger.finished(this, "NextTurn");
	}
	
	/** 
	 * Megnézi, hogy van-e még élő Hallgató a játékban.
	 *
	 * @return true abban az esetben ha van élő játékos, false ha nincs további játékos.
	 * */
	public boolean AnyStudentsAlive() {
		Logger.started(this, "AnyStudentsAlive");
		boolean toReturn = Reader.GetBooleanInput("Van még élő Student? ");
		if (!toReturn)
			this.EndGame(false);
		Logger.finished(this, "AnyStudentsAlive");
		return toReturn;
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
