package modul;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : modul.Room.java
//  @ Date : 2024. 03. 20.
//  @ Author : 
//
//




/** */
public class Room {
	/** */
	private int poisonDuration;
	
	/** */
	private int maxCapacity;
	
	/** */
	private int currentCapacity;
	
	/** */
	private bool isCursed;
	
	/** */
	private DoorSide rooms;
	
	/** */
	private Room neighbors;
	
	/** */
	private Item items;
	
	/** */
	private DoorSide doors;
	
	/** */
	private Person people;
	
	/** */
	private Student students;
	
	/** */
	private Instructor instructors;
	
	/** */
	private Room neighbors;
	
	/** */
	public void DecrementPoison() {
	}
	
	/** */
	public void AddItem(Item i) {
	}
	
	/** */
	public void RemoveItem(Item i) {
	}
	
	/** */
	public void CloneAttributes(Room r) {
	}
	
	/** */
	public Item* GetItems() {
	}
	
	/** */
	public Student* GetStudents() {
	}
	
	/** */
	public int GetCurrentCapacity() {
	}
	
	/** */
	public int GetMaxCapacity() {
	}
	
	/** */
	public Room* GetNeighbors() {
	}
	
	/** */
	public void SetMaxCapacity(int mc) {
	}
	
	/** */
	public int GetPoisonDuration() {
	}
	
	/** */
	public void SetPoisonDuration(int pd) {
	}
	
	/** */
	public void AddDoor(DoorSide d) {
	}
	
	/** */
	public DoorSide* GetDoors() {
	}
	
	/** */
	public void AddNeighbor(Room r) {
	}
	
	/** */
	public void SetNeighbors(Room* n) {
	}
	
	/** */
	public bool MergeRooms(Room r2) {
	}
	
	/** */
	public void SeparateRoom() {
	}
	
	/** */
	public void AddInstructor(Instructor i) {
	}
	
	/** */
	public void RemoveInstructor(Instructor i) {
	}
	
	/** */
	public void AddStudent(Student s) {
	}
	
	/** */
	public void RemoveStudent(Student s) {
	}
	
	/** */
	public bool RandomBool() {
	}
	
	/** */
	public Room SelectRoom(Room* r) {
	}
	
	/** */
	public void ToggleDoorsVisible() {
	}
	
	/** */
	public void SetIsCursed(bool isc) {
	}
	
	/** */
	public bool GetIsCursed() {
	}
}
