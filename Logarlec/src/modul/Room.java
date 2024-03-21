package modul;


import util.Reader;

import java.util.ArrayList;
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
	private List<DoorSide> rooms;
	
	/** */
	private List<Room> neighbors;
	
	/** */
	private List<Item> items;
	
	/** */
	private List<DoorSide> doors;
	
	/** */
	private List<Person> people;
	
	/** */
	private List<Student> students;
	
	/** */
	private List<Instructor> instructors;

	public Room(){
		rooms = new ArrayList<>();
		neighbors = new ArrayList<>();
		items = new ArrayList<>();
		doors = new ArrayList<>();
		people = new ArrayList<>();
		students = new ArrayList<>();
		instructors = new ArrayList<>();
	}


	/** */
	public void DecrementPoison() {
		System.out.println("STARTED: " + this + ".DecrementPoison()");
		// existing code
		System.out.println("FINISHED: " + this + ".DecrementPoison()");
	}

	/** */
	public void AddItem(Item i) {
		System.out.println("STARTED: " + this + ".AddItem(" + i + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".AddItem(" + i + ")");
	}

	/** */
	public void RemoveItem(Item i) {
		System.out.println("STARTED: " + this + ".RemoveItem(" + i + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".RemoveItem(" + i + ")");
	}

	/** */
	public void CloneAttributes(Room r) {
		System.out.println("STARTED: " + this + ".CloneAttributes(" + r + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".CloneAttributes(" + r + ")");
	}

	/** */
	public List<Item> GetItems() {
		System.out.println("STARTED: " + this + ".GetItems()");
		// existing code
		System.out.println("FINISHED: " + this + ".GetItems()");
		return null;
	}

	/** */
	public List<Student> GetStudents() {
		System.out.println("STARTED: " + this + ".GetStudents()");
		// existing code
		System.out.println("FINISHED: " + this + ".GetStudents()");
		return null;
	}

	/** */
	public int GetCurrentCapacity() {
		System.out.println("STARTED: " + this + ".GetCurrentCapacity()");
		int _currentCapacity = Reader.GetIntInput("Mekkora a szoba jelenlegi kapacitasa?");
		System.out.println("FINISHED: " + this + ".GetCurrentCapacity()");
		return _currentCapacity;
	}

	/** */
	public int GetMaxCapacity() {
		System.out.println("STARTED: " + this + ".GetMaxCapacity()");
		int _maxCapacity = Reader.GetIntInput("Mekkora a szoba maximum kapacitasa?");
		System.out.println("FINISHED: " + this + ".GetMaxCapacity()");
		return _maxCapacity;
	}

	/** */
	public List<Room> GetNeighbors() {
		System.out.println("STARTED: " + this + ".GetNeighbors()");
		// existing code
		System.out.println("FINISHED: " + this + ".GetNeighbors()");
		return null;
	}

	/** */
	public void SetMaxCapacity(int mc) {
		System.out.println("STARTED: " + this + ".SetMaxCapacity(" + mc + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".SetMaxCapacity(" + mc + ")");
	}

	/** */
	public int GetPoisonDuration() {
		System.out.println("STARTED: " + this + ".GetPoisonDuration()");
		// existing code
		System.out.println("FINISHED: " + this + ".GetPoisonDuration()");
		return 0;
	}

	/** */
	public void SetPoisonDuration(int pd) {
		System.out.println("STARTED: " + this + ".SetPoisonDuration(" + pd + ")");
		poisonDuration = pd;
		System.out.println("FINISHED: " + this + ".SetPoisonDuration(" + pd + ")");
	}

	/** */
	public void AddDoor(DoorSide d) {
		System.out.println("STARTED: " + this + ".AddDoor(" + d + ")");
		doors.add(d);
		System.out.println("FINISHED: " + this + ".AddDoor(" + d + ")");
	}

	/** */
	public List<DoorSide> GetDoors() {

		System.out.println("STARTED: " + this + ".GetDoors()");
		// existing code
		System.out.println("FINISHED: " + this + ".GetDoors()");
		return null;
	}

	/** */
	public void AddNeighbor(Room r) {
		System.out.println("STARTED: " + this + ".AddNeighbor(" + r + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".AddNeighbor(" + r + ")");
	}

	/** */
	public void SetNeighbors(Room n) {
		System.out.println("STARTED: " + this + ".SetNeighbors(" + n + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".SetNeighbors(" + n + ")");
	}

	/** */
	public boolean MergeRooms(Room r2) {
		System.out.println("STARTED: " + this + ".MergeRooms(" + r2 + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".MergeRooms(" + r2 + ")");
		return false;
	}

	/** */
	public void SeparateRoom() {
		System.out.println("STARTED: " + this + ".SeparateRoom()");
		// existing code
		System.out.println("FINISHED: " + this + ".SeparateRoom()");
	}

	/** */
	public void AddInstructor(Instructor i) {
		System.out.println("STARTED: " + this + ".AddInstructor(" + i + ")");
		i.SetRoom(this);
		if(poisonDuration>0){
			boolean defended = i.DefendFromGas();
			if(!defended){
				i.SetIsFainted(true);
			}
		}
		System.out.println("FINISHED: " + this + ".AddInstructor(" + i + ")");
	}

	/** */
	public void RemoveInstructor(Instructor i) {
		System.out.println("STARTED: " + this + ".RemoveInstructor(" + i + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".RemoveInstructor(" + i + ")");
	}

	/** */
	public void AddStudent(Student s) {
		System.out.println("STARTED: " + this + ".AddStudent(" + s + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".AddStudent(" + s + ")");
	}

	/** */
	public void RemoveStudent(Student s) {
		System.out.println("STARTED: " + this + ".RemoveStudent(" + s + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".RemoveStudent(" + s + ")");
	}

	/** */
	public boolean RandomBool() {
		System.out.println("STARTED: " + this + ".RandomBool()");
		// existing code
		System.out.println("FINISHED: " + this + ".RandomBool()");
		return false;
	}

	/** */
	public Room SelectRoom(Room r) {
		System.out.println("STARTED: " + this + ".SelectRoom(" + r + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".SelectRoom(" + r + ")");
		return null;
	}

	/** */
	public void ToggleDoorsVisible() {
		System.out.println("STARTED: " + this + ".ToggleDoorsVisible()");

// existing code
		System.out.println("FINISHED: " + this + ".ToggleDoorsVisible()");
	}

	/** */
	public void SetIsCursed(boolean isc) {
		System.out.println("STARTED: " + this + ".SetIsCursed(" + isc + ")");
		// existing code
		System.out.println("FINISHED: " + this + ".SetIsCursed(" + isc + ")");
	}

	/** */
	public boolean GetIsCursed() {
		System.out.println("STARTED: " + this + ".GetIsCursed()");
		// existing code
		System.out.println("FINISHED: " + this + ".GetIsCursed()");
		return false;
	}
}
