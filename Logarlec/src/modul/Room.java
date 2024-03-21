package modul;


import java.util.List;

/** */
public class Room {
	/** */
	private int poisonDuration;
	
	/** */
	private int maxCapacity;
	
	/** */
	private int currentCapacity;
	
	/** */
	private boolean isCursed;
	
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
	public List<Item> GetItems() {
		return null;
	}
	
	/** */
	public List<Student> GetStudents() {
		return null;
	}
	
	/** */
	public int GetCurrentCapacity() {
		return 0;
	}
	
	/** */
	public int GetMaxCapacity() {
		return 0;
	}
	
	/** */
	public List<Room> GetNeighbors() {
		return null;
	}
	
	/** */
	public void SetMaxCapacity(int mc) {
	}
	
	/** */
	public int GetPoisonDuration() {
		return 0;
	}
	
	/** */
	public void SetPoisonDuration(int pd) {
	}
	
	/** */
	public void AddDoor(DoorSide d) {
	}
	
	/** */
	public List<DoorSide> GetDoors() {
		return null;
	}
	
	/** */
	public void AddNeighbor(Room r) {
	}
	
	/** */
	public void SetNeighbors(Room n) {
	}
	
	/** */
	public boolean MergeRooms(Room r2) {
		return false;
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
	public boolean RandomBool() {
		return false;
	}
	
	/** */
	public Room SelectRoom(Room  r) {
		return null;
	}
	
	/** */
	public void ToggleDoorsVisible() {
	}
	
	/** */
	public void SetIsCursed(boolean isc) {
	}
	
	/** */
	public boolean GetIsCursed() {
		return false;
	}
}
