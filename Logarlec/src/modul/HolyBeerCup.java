package modul;




/** */
public class HolyBeerCup extends Item implements Usable, Defendable {
	/** */
	private int effectDuration;
	
	/** */
	private boolean isActivated;
	
	/** */
	@Override
	public boolean Activate() {
		return false;
	}
	
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
	
	/** */
	public void UsedByStudent(Student s) {
	}
	
	/** */
	public void UsedByInstructor(Instructor i) {
	}


	@Override
	public boolean CanDefend() {
		return false;
	}
}
