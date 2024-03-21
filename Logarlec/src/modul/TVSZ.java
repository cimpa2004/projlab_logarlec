package modul;




/** */
public class TVSZ extends Item implements Defendable{
	/** */
	private int usesLeft;
	
	/** */
	@Override
	public void Decrement() {
	}
	
	/** */
	public boolean PickedUpStudent(Student st) {
		return false;
	}
	
	/** */
	public boolean PickedUpInstructor(Instructor i) {
		return false;
	}
	
	/** */
	public void Thrown(Person p) {
	}


	@Override
	public boolean CanDefend() {
		return false;
	}
}
