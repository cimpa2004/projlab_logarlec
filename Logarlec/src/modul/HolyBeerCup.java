package modul;

import util.Logger;
import util.Reader;

/** */
public class HolyBeerCup extends Item implements Usable, Defendable {
	/** */
	private int effectDuration;
	/** */
	private boolean isActivated;
	/** */
	@Override
	public boolean Activate() {
		Logger.started(this, "Activate");
		isActivated = Reader.GetBooleanInput("Sikerült aktiválni a HolyBeerCupot?");
		Logger.finished(this, "Activate");
		return isActivated;
	}

	/** */
	@Override
	public void Decrement() {
		Logger.started(this, "Decrement");
		if(effectDuration>0) effectDuration = effectDuration - 1;
		Logger.finished(this, "Decrement");
	}
	/** */
	public boolean PickedUpStudent(Student st) {
		Logger.started(this, "PickedUpStudent", st);
		boolean isAdded = st.AddToInventory(this);
		Logger.finished(this, "PickedUpStudent", st);
		return isAdded;
	}
	/** */
	public boolean PickedUpInstructor(Instructor i) {
		Logger.started(this, "PickedUpInstructor", i);
		boolean isAdded = i.AddToInventory(this);
		Logger.finished(this, "PickedUpInstructor", i);
		return isAdded;
	}
	/** */
	public void Thrown(Person p) {
		Logger.started(this, "Thrown", p);
		Logger.finished(this, "Thrown", p);
	}
	/** */
	public void UsedByStudent(Student s) {
		Logger.started(this, "UsedByStudent", s);
		Activate();
		if(isActivated){
			s.AddHolyBeerCup(this);
		}
		Logger.finished(this, "UsedByStudent", s);
	}
	/** */
	public void UsedByInstructor(Instructor i) {
		Logger.started(this, "UsedByInstructor", i);
		Logger.finished(this, "UsedByInstructor", i);
	}
	/** */
	@Override
	public boolean CanDefend() {
		Logger.started(this, "CanDefend");
		Logger.finished(this, "CanDefend");
		effectDuration = Reader.GetIntInput("Mennyi ideig hatásos még a HolyBeerCup?");
		return isActivated && effectDuration > 0;
	}
}
