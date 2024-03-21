package modul;


import util.Reader;

/** */
public class Instructor extends Person {
	/** */
	private int stunDuration;

	/** */
	public void AppearInRoom(Room r) {
		System.out.println("STARTED: " + this + ".AppearInRoom(" + r + ")");
		r.AddInstructor(this);
		System.out.println("FINISHED: " + this + ".AppearInRoom(" + r + ")");
	}

	/** */
	public void StealSoul(Student st) {
		System.out.println("STARTED: " + this + ".StealSoul(" + st + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".StealSoul(" + st + ")");
	}

	/** */
	public void Stun(int duration) {
		System.out.println("STARTED: " + this + ".Stun(" + duration + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".Stun(" + duration + ")");
	}

	/** */
	public void DecrementStun() {
		System.out.println("STARTED: " + this + ".DecrementStun()");
		// existing code
		System.out.println("FINISHED: " + this + ".DecrementStun()");
	}

	/** */
	public void StartTurn() {
		System.out.println("STARTED: " + this + ".StartTurn()");
		// existing code
		System.out.println("FINISHED: " + this + ".StartTurn()");
	}

	/** */
	public void EndTurn() {
		System.out.println("STARTED: " + this + ".EndTurn()");
		// existing code
		System.out.println("FINISHED: " + this + ".EndTurn()");
	}

	/** */
	public void UseItem(Usable u) {
		System.out.println("STARTED: " + this + ".UseItem(" + u + ")");
		u.UsedByInstructor(this);
		System.out.println("FINISHED: " + this + ".UseItem(" + u + ")");
	}

	/** */
	@Override
	public boolean Pickup(Item i) {
		System.out.println("STARTED: " + this + ".Pickup(" + i + ")");
		boolean isPickedUp = i.PickedUpInstructor(this);
		System.out.println("FINISHED: " + this + ".Pickup(" + i + ")");
		return isPickedUp;
	}

	/** */
	@Override
	public void Move(DoorSide d) {
		System.out.println("STARTED: " + this + ".Move(" + d + ")");
		boolean canBeOpened = Reader.GetBooleanInput("Az ajtot ki lehet nyitni?");
		boolean isVisible = Reader.GetBooleanInput("Az ajto lathato?");
		if(!canBeOpened || !isVisible){
			System.out.println("FINISHED: " + this + ".Move(" + d + ")");
			return;
		}
		DoorSide d2 = d.GetPair();
		Room r2 = d2.GetRoom();
		int maxCapacity = r2.GetMaxCapacity();
		int currCapacity = r2.GetCurrentCapacity();
		if(!(currCapacity<maxCapacity)){
			System.out.println("FINISHED: " + this + ".Move(" + d + ")");
			return;
		}
		room.RemoveInstructor(this);
		AppearInRoom(r2);
		System.out.println("FINISHED: " + this + ".Move(" + d + ")");
	}

}
