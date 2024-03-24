package modul;


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
	 * Tárolja, hogy a játék végetért-e.
	 * */
	private boolean isEndGame;
	
	/**
	 * A játékban szereplő Hallgatók és Oktatók sorrendjét tároló attribútum.
	 * */
	private Person turnOrder;
	
	/**
	 * A soronkövetkező Személy található meg benne.
	 * */
	private Person currentTurn;

	

	/**
	 * Itt vannak eltárolva a játékban található szobák.
	 * */
	private List<Room> rooms = new ArrayList<>();
	
	/**
	 * Elindítja a játékot, incializálja a játékmenetet.
	 * */
	public void StartGame() {
		Logger.started(this, "StartGame");
		Logger.finished(this, "StartGame");
	}
	
	/**
	 * Lezárja a játékot, a paraméterben megkapott érték alapján pedig a nyertes oldalt.
	 *
	 * @param winSide 0 érték ha Oktató, 1 ha Hallgató
	 * */
	public void EndGame(boolean winSide) {
		Logger.started(this, "EndGame", winSide);
		Logger.finished(this, "EndGame", winSide);
	}
	
	/**
	 * Átlépteti a játékot a következő körre.
	 * */
	public void NextTurn() {

		Logger.started(this, "NextTurn");
		

		boolean toUse = Reader.GetBooleanInput("Legyen e elátkozott minden szoba? ");
		if (toUse && !rooms.isEmpty())
			for (Room r : this.rooms){
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
	 * Eltávolítja azt a Személyt a turnOrder attribútumból, akit paraméterként kap.
	 *
	 * @param p Az eltávolítandó Személy.
	 * */
	public void RemoveFromGame(Person p) {
		Logger.started(this, "RemoveFromGame", p);
		Logger.finished(this, "RemoveFromGame", p);
	}
	
	/**
	 * Hozzáadja a paraméterben kapott szobát a játékhoz.
	 *
	 * @param r Hozzáadandó szoba.
	 * */
	public void AddRoom(Room r) {
		Logger.started(this, "AddRoom", r);
		if (r != null)
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
}
