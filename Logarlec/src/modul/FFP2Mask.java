package modul;


import util.Reader;

/** */
public class FFP2Mask extends Item implements Usable, Defendable {
	/** */
	private int durability;
	
	/** */
	private boolean isActivated;

	public FFP2Mask(){
		isActivated = false;
	}

	/** */
	public boolean Activate() {
		System.out.println("STARTED: " + this + ".Activate()");
		isActivated = Reader.GetBooleanInput("Sikerült aktiválni az FFP2Mask-ot?");
		System.out.println("FINISHED: " + this + ".Activate()");
		return isActivated;
	}

	@Override
	public void Decrement() {
		System.out.println("STARTED: " + this + ".Decrement()");
		if(durability > 0) durability = durability - 1;
		System.out.println("FINISHED: " + this + ".Decrement()");
	}

	/** */
	public boolean PickedUpStudent(Student st) {
		System.out.println("STARTED: " + this + ".PickedUpStudent(" + st + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".PickedUpStudent(" + st + ")");
		return false;
	}

	/** */
	public boolean PickedUpInstructor(Instructor i) {
		System.out.println("STARTED: " + this + ".PickedUpInstructor(" + i + ")");
		boolean isAdded = i.AddToInventory(this);
		System.out.println("FINISHED: " + this + ".PickedUpInstructor(" + i + ")");
		return isAdded;
	}

	/** */
	public void Thrown(Person p) {
		System.out.println("STARTED: " + this + ".Thrown(" + p + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".Thrown(" + p + ")");
	}

	/** */
	public void UsedByStudent(Student s) {
		System.out.println("STARTED: " + this + ".UsedByStudent(" + s + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".UsedByStudent(" + s + ")");
	}

	/** */
	public void UsedByInstructor(Instructor i) {
		System.out.println("STARTED: " + this + ".UsedByInstructor(" + i + ")");
		boolean _isActivated = Activate();
		if(_isActivated){
			i.AddFFP2Mask(this);
		}
		System.out.println("FINISHED: " + this + ".UsedByInstructor(" + i + ")");
	}

	@Override
	public boolean CanDefend() {
		System.out.println("STARTED: " + this + ".CanDefend()");
		System.out.println("FINISHED: " + this + ".CanDefend()");
		durability = Reader.GetIntInput("Hanyszor hasznalhato meg az FFP2 maszk?");
		return isActivated && durability > 0;
	}

}
