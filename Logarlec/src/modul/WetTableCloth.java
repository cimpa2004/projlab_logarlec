package modul;


import util.Reader;

/** */
public class WetTableCloth extends Item implements Usable, Defendable {
	/** */
	private int effectDuration;
	
	/** */
	private boolean isActivated;

	/** */
	public boolean Activate() {
		System.out.println("STARTED: " + this + ".Activate()");
		isActivated = Reader.GetBooleanInput("Sikerült aktiválni a WetTableClothot?");
		System.out.println("FINISHED: " + this + ".Activate()");
		return isActivated;
	}

	/** */
	public boolean PickedUpStudent(Student st) {
		System.out.println("STARTED: " + this + ".PickedUpStudent(" + st + ")");
		boolean isAdded = st.AddToInventory(this);
		System.out.println("FINISHED: " + this + ".PickedUpStudent(" + st + ")");
		return isAdded;
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
		System.out.println("FINISHED: " + this + ".Thrown(" + p + ")");
	}

	/** */
	@Override
	public void Decrement() {
		System.out.println("STARTED: " + this + ".Decrement()");
		if(effectDuration>0) effectDuration = effectDuration - 1;
		System.out.println("FINISHED: " + this + ".Decrement()");
	}
	
	/** */
	public void UsedByStudent(Student s) {
		System.out.println("STARTED: " + this + ".UsedByStudent(" + s + ")");
		Activate();
		if(isActivated){
			s.AddWetTableCloth(this);
		}
		// TODO stun Instructors in Student's Room
		System.out.println("FINISHED: " + this + ".UsedByStudent(" + s + ")");
	}

	/** */
	public void UsedByInstructor(Instructor i) {
		System.out.println("STARTED: " + this + ".UsedByInstructor(" + i + ")");
		System.out.println("FINISHED: " + this + ".UsedByInstructor(" + i + ")");
	}

	@Override
	public boolean CanDefend() {
		System.out.println("STARTED: " + this + ".CanDefend()");
		System.out.println("FINISHED: " + this + ".CanDefend()");
		effectDuration = Reader.GetIntInput("Mennyi ideig hatásos még a WetTableCloth?");
		return isActivated && effectDuration > 0;
	}
}
