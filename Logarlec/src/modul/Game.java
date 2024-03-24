package modul;


import util.Logger;
import util.Reader;

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
	private Room rooms;
	
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
	}
	
	/** */
	public void RemoveRoom(Room r) {
	}
}
