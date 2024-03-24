package modul;

import java.util.ArrayList;
import java.util.List;

/** */
public class Game {
	/** */
	private int gameTimer;
	
	/** */
	private boolean isEndGame;
	
	/** */
	private ArrayList<Person> turnOrder;
	
	/** */
	private Person currentTurn;

	
	/** */
	private ArrayList<Room> rooms;
	
	/** */
	public void StartGame() {
	}
	
	/** */
	public void EndGame(boolean winSide) {
	}
	
	/** */
	public void NextTurn() {
		currentTurn.EndTurn();
		boolean anyStudentsAlive = AnyStudentsAlive();
		if(!anyStudentsAlive){
			EndGame(false);
		}
		int currentIndex = turnOrder.indexOf(currentTurn);
		currentIndex++;
		if(currentIndex > turnOrder.size()){
			currentIndex = 0;
		}
		currentTurn = turnOrder.get(currentIndex);
		currentTurn.StartTurn();
	}
	
	/** */
	public boolean AnyStudentsAlive() {
		return false;
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
