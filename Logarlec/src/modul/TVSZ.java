package modul;


import util.Logger;
import util.Reader;

/** */
public class TVSZ extends Item implements Defendable{
	/** */
	private int usesLeft;

	public TVSZ(){
		usesLeft = Reader.GetIntInput("A létrejövő TVSZ hányszor tudjon még védeni gyilkosságtól?");
	}

	/** */
	@Override
	public void Decrement() {
		Logger.started(this, "Decrement");
		if (usesLeft > 0) usesLeft = usesLeft - 1;
		Logger.finished(this, "Decrement");
	}

	/** */
	public boolean PickedUpStudent(Student st) {
		Logger.started(this, "PickedUpStudent", st);
		if (usesLeft > 0) st.AddTVSZ(this);
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
	@Override
	public boolean CanDefend() {
		Logger.started(this, "CanDefend");
		Logger.finished(this, "CanDefend");
		return usesLeft > 0;
	}
}
