package modul;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : modul.Item.java
//  @ Date : 2024. 03. 20.
//  @ Author : 
//
//




/** */
public abstract class Item {
	/** */
	private Room room;
	
	/** */
	private Person owner;
	
	/** */
	public void SetRoom(Room r) {
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