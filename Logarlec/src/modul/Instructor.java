package modul;

import util.Logger;
import util.Reader;

/** */
public class Instructor extends Person {
	/** */
	private int stunDuration;

	/** */
	public void AppearInRoom(Room r) {
		Logger.started(this, "AppearInRoom", r);
		r.AddInstructor(this);
		Logger.finished(this, "AppearInRoom", r);
	}

	/** */
	public void StealSoul(Student st) {
		Logger.started(this, "StealSoul", st);
		st.Die();
		Logger.finished(this, "StealSoul", st);
	}

	/** */
	public void Stun(int duration) {
		Logger.started(this, "Stun", duration);
		// existing code
		Logger.finished(this, "Stun", duration);
	}

	/** */
	public void DecrementStun() {
		Logger.started(this, "DecrementStun");
		// existing code
		Logger.finished(this, "DecrementStun");
	}

	/** */
	public void StartTurn() {
		Logger.started(this, "StartTurn");
		// existing code
		Logger.finished(this, "StartTurn");
	}

	/** */
	public void EndTurn() {

		Logger.started(this, "EndTurn");
		// existing code
		Logger.finished(this, "EndTurn");
	}

	/** */
	public void UseItem(Usable u) {
		Logger.started(this, "UseItem", u);
		// Any Usable must be an Item as well
		Item item = (Item)u;
		if(inventory.contains(item)) u.UsedByInstructor(this);
		Logger.finished(this, "UseItem", u);
	}

	/** */
	@Override
	public boolean Pickup(Item i) {
		Logger.started(this, "Pickup", i);
		boolean isPickedUp = i.PickedUpInstructor(this);
		Logger.finished(this, "Pickup", i);
		return isPickedUp;
	}

	/** */
	@Override
	public void Move(DoorSide d) {
		Logger.started(this, "Move", d);
		boolean canBeOpened = Reader.GetBooleanInput("Az ajtot ki lehet nyitni?");
		boolean isVisible = Reader.GetBooleanInput("Az ajto lathato?");
		if(!canBeOpened || !isVisible){
			Logger.finished(this, "Move", d);
			return;
		}
		DoorSide d2 = d.GetPair();
		Room r2 = d2.GetRoom();
		int maxCapacity = r2.GetMaxCapacity();
		int currCapacity = r2.GetCurrentCapacity();
		if(!(currCapacity<maxCapacity)){
			Logger.finished(this, "Move", d);
			return;
		}
		room.RemoveInstructor(this);
		AppearInRoom(r2);
		Logger.finished(this, "Move", d);
	}

}