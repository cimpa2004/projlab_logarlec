package modul;


import util.Logger;

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
		Logger.started(this, "CloneAttributes", d);
		// existing code
		Logger.finished(this, "CloneAttributes", d);
	}

	/** */
	public void ConnectDoors(DoorSide d) {
		Logger.started(this, "ConnectDoors", d);
		// existing code
		Logger.finished(this, "ConnectDoors", d);
	}

	/** */
	public DoorSide GetPair() {
		Logger.started(this, "GetPair");
		Logger.finished(this, "GetPair");
		return pair;
	}

	/** */
	public void SetPair(DoorSide d) {
		Logger.started(this, "SetPair", d);
		pair = d;
		Logger.finished(this, "SetPair", d);
	}

	/** */
	public Room GetRoom() {
		Logger.started(this, "GetRoom");
		Logger.finished(this, "GetRoom");
		return room;
	}

	/** */
	public void SetRoom(Room r) {
		Logger.started(this, "SetRoom", r);
		room = r;
		r.AddDoor(this);
		Logger.finished(this, "SetRoom", r);
	}

	/** */
	public void SetCanBeOpened(boolean b) {
		Logger.started(this, "SetCanBeOpened", b);
		// existing code
		Logger.finished(this, "SetCanBeOpened", b);
	}

	/** */
	public void SetIsVisible(boolean b) {
		Logger.started(this, "SetIsVisible", b);
		// existing code
		Logger.finished(this, "SetIsVisible", b);
	}

	/** */
	public boolean GetCanBeOpened() {
		Logger.started(this, "GetCanBeOpened");
		// existing code
		Logger.finished(this, "GetCanBeOpened");
		return false;
	}

	/** */
	public boolean GetIsVisible() {
		Logger.started(this, "GetIsVisible");
		// existing code
		Logger.finished(this, "GetIsVisible");
		return false;
	}
}
