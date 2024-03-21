package modul;


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
		System.out.println("STARTED: " + this + ".Decrement()");
		if(usesLeft>0) usesLeft = usesLeft - 1;
		System.out.println("FINISHED: " + this + ".Decrement()");
	}

	/** */
	public boolean PickedUpStudent(Student st) {
		System.out.println("STARTED: " + this + ".PickedUpStudent(" + st + ")");
		if(usesLeft>0) st.AddTVSZ(this);
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

	@Override
	public boolean CanDefend() {
		System.out.println("STARTED: " + this + ".CanDefend()");
		System.out.println("FINISHED: " + this + ".CanDefend()");
		return usesLeft > 0;
	}
}
