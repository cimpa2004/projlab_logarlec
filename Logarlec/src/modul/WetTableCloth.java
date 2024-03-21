package modul;




/** */
public class WetTableCloth extends Item implements Usable, Defendable {
	/** */
	private int effectDuration;
	
	/** */
	private boolean isActivated;
	
	/** */
	public boolean Activate() {
		return false;
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
	@Override
	public void Decrement() {
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
