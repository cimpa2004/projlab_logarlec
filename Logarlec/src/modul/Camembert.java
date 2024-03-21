package modul;



/** */
public class Camembert extends Item implements Usable {
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
	public void UsedByStudent(Student s) {
	}
	
	/** */
	public void UsedByInstructor(Instructor i) {
	}
	


}
