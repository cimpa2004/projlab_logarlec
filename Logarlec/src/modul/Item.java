package modul;




/** */
public abstract class Item {
	/** */
	private Room room;
	
	/** */
	private Person owner;
	
	/** */
	public void SetRoom(Room r) {
		room = r;
	}
	
	/** */
	public Room GetRoom() {
		return null;
	}
	
	/** */
	public abstract boolean PickedUpStudent(Student st);
	
	/** */
	public abstract boolean PickedUpInstructor(Instructor i);
	
	/** */
	public abstract void Thrown(Person p);
}
