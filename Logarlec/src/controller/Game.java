package controller;

import modul.IPerson;
import modul.IRoom;
import modul.Person;
import modul.Room;
import util.Logger;
import util.Reader;

import java.util.ArrayList;
import java.util.List;

/** */
public class Game {
	/**
	 * Tárolja a hátralévő körök számát.
	 * */
	private int gameTimer;

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
	private List<IRoom> rooms = new ArrayList<>();

	/**
	 * Vissza adja az IPerson listat ami tartalmazza a jatekban levo szemelyeket
	 * @return A jatekot jatszo szemelyek
	 */
	public ArrayList<IPerson> GetTurnOrder() {
		return turnOrder;
	}

	public Game(){
		isGameDeterministic = false;
	}
	public Game(boolean isGameDeterministic){
		this.isGameDeterministic = isGameDeterministic;
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
		boolean anyStudentsAlive = AnyStudentsAlive();
		if(!anyStudentsAlive){
			EndGame(false);
		}
		int currentIndex = turnOrder.indexOf(currentTurn);
		currentIndex++;
		if(currentIndex >= turnOrder.size()){
			currentIndex = 0;
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
}
