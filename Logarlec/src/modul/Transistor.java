package modul;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : modul.Transistor.java
//  @ Date : 2024. 03. 20.
//  @ Author : 
//
//




/** */
public class Transistor extends Item implements Usable {
	/** */
	private boolean isActive;
	
	/** */
	public boolean Activate() {
		return false;
	}
	
	/** */
	public void SetPairs(Transistor t1, Transistor t2) {
	}
	
	/** */
	public void SetPair(Transistor t) {
	}
	
	/** */
	public Transistor GetPair() {
		return null;
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