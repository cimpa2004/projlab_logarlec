package modul;


import util.Logger;
import util.Reader;

import java.util.ArrayList;
import java.util.List;

/** */
public class Game {
	/** */
	private int gameTimer;
	
	/** */
	private boolean isEndGame;
	
	/** */
	private Person turnOrder;
	
	/** */
	private Person currentTurn;

	
	/** */
	private List<Room> rooms = new ArrayList<>();
	
	/** */
	public void StartGame() {
	}
	
	/** */
	public void EndGame(boolean winSide) {
		Logger.started(this, "EndGame", winSide);

		Logger.finished(this, "EndGame", winSide);
	}
	
	/** */
	public void NextTurn() {
		boolean toUse = Reader.GetBooleanInput("Legyen e elátkozott minden szoba? ");
		if (toUse && !rooms.isEmpty())
			for (Room r : this.rooms){
				r.SetIsCursed(true);
			}
	}
	
	/** */
	public boolean AnyStudentsAlive() {
		Logger.started(this, "AnyStudentsAlive");
		boolean toReturn = Reader.GetBooleanInput("Van még élő Student? ");
		if (!toReturn)
			this.EndGame(false);
		Logger.finished(this, "AnyStudentsAlive");
		return toReturn;
	}
	
	/** */
	public void RemoveFromGame(Person p) {
	}
	
	/** */
	public void AddRoom(Room r) {
		if (r != null)
			this.rooms.add(r);
	}
	
	/** */
	public void RemoveRoom(Room r) {
	}
}
