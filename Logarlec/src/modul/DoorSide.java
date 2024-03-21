package modul;


import java.util.ArrayList;
import java.util.List;

/** */
public class DoorSide {
	/** */
	private boolean canBeOpened;
	
	/** */
	private boolean isVisible;
	
	/** */
	private Room room;
	
	/** */
	private DoorSide pair;

	public DoorSide(){
	}

	/** */
	public void CloneAttributes(DoorSide d) {
		System.out.println("STARTED: " + this + ".CloneAttributes(" + d + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".CloneAttributes(" + d + ")");
	}

	/** */
	public void ConnectDoors(DoorSide d) {
		System.out.println("STARTED: " + this + ".ConnectDoors(" + d + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".ConnectDoors(" + d + ")");
	}

	/** */
	public DoorSide GetPair() {
		System.out.println("STARTED: " + this + ".GetPair()");
		System.out.println("FINISHED: " + this + ".GetPair()");
		return pair;
	}

	/** */
	public void SetPair(DoorSide d) {
		System.out.println("STARTED: " + this + ".SetPair()");
		pair = d;
		System.out.println("FINISHED: " + this + ".SetPair()");
	}

	/** */
	public Room GetRoom() {
		System.out.println("STARTED: " + this + ".GetRoom()");
		System.out.println("FINISHED: " + this + ".GetRoom()");
		return room;
	}

	/** */
	public void SetRoom(Room r) {
		System.out.println("STARTED: " + this + ".SetRoom(" + r + ")");
		room = r;
		r.AddDoor(this);
		System.out.println("FINISHED: " + this + ".SetRoom(" + r + ")");
	}

	/** */
	public void SetCanBeOpened(boolean b) {
		System.out.println("STARTED: " + this + ".SetCanBeOpened(" + b + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".SetCanBeOpened(" + b + ")");
	}

	/** */
	public void SetIsVisible(boolean b) {
		System.out.println("STARTED: " + this + ".SetIsVisible(" + b + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".SetIsVisible(" + b + ")");
	}

	/** */
	public boolean GetCanBeOpened() {
		System.out.println("STARTED: " + this + ".GetCanBeOpened()");
		// existing code
		System.out.println("FINISHED: " + this + ".GetCanBeOpened()");
		return false;
	}

	/** */
	public boolean GetIsVisible() {
		System.out.println("STARTED: " + this + ".GetIsVisible()");
		// existing code
		System.out.println("FINISHED: " + this + ".GetIsVisible()");
		return false;
	}
}
