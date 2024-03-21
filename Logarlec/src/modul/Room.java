package modul;


import util.Logger;
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
		Logger.started(this, "DecrementPoison");
		// existing code
		Logger.finished(this, "DecrementPoison");
	}

	/** */
	public void AddItem(Item i) {
		Logger.started(this, "AddItem", i);
		items.add(i);
		Logger.finished(this, "AddItem", i);
	}

	/** */
	public void RemoveItem(Item i) {
		Logger.started(this, "RemoveItem", i);
		// existing code
		Logger.finished(this, "RemoveItem", i);
	}

	/** */
	public void CloneAttributes(Room r) {
		Logger.started(this, "CloneAttributes", r);
		// existing code
		Logger.finished(this, "CloneAttributes", r);
	}

	/** */
	public List<Item> GetItems() {
		Logger.started(this, "GetItems");
		// existing code
		Logger.finished(this, "GetItems");
		return null;
	}

	/** */
	public List<Student> GetStudents() {
		Logger.started(this, "GetStudents");
		// existing code
		Logger.finished(this, "GetStudents");
		return null;
	}

	/** */
	public int GetCurrentCapacity() {
		Logger.started(this, "GetCurrentCapacity");
		int _currentCapacity = Reader.GetIntInput("Mekkora a szoba jelenlegi kapacitasa?");
		Logger.finished(this, "GetCurrentCapacity");
		return _currentCapacity;
	}

	/** */
	public int GetMaxCapacity() {
		Logger.started(this, "GetMaxCapacity");
		int _maxCapacity = Reader.GetIntInput("Mekkora a szoba maximum kapacitasa?");
		Logger.finished(this, "GetMaxCapacity");
		return _maxCapacity;
	}

	/** */
	public List<Room> GetNeighbors() {
		Logger.started(this, "GetNeighbors");
		// existing code
		Logger.finished(this, "GetNeighbors");
		return null;
	}

	/** */
	public void SetMaxCapacity(int mc) {
		Logger.started(this, "SetMaxCapacity", mc);
		// existing code
		Logger.finished(this, "SetMaxCapacity", mc);
	}

	/** */
	public int GetPoisonDuration() {
		Logger.started(this, "GetPoisonDuration");
		// existing code
		Logger.finished(this, "GetPoisonDuration");
		return 0;
	}

	/** */
	public void SetPoisonDuration(int pd) {
		Logger.started(this, "SetPoisonDuration", pd);
		poisonDuration = pd;
		for (Person p : people) {
			boolean isPersonDefended = p.DefendFromGas();
			if (!isPersonDefended) p.SetIsFainted(true);
		}
		Logger.finished(this, "SetPoisonDuration", pd);
	}

	/** */
	public void AddDoor(DoorSide d) {
		Logger.started(this, "AddDoor", d);
		doors.add(d);
		Logger.finished(this, "AddDoor", d);
	}

	/** */
	public List<DoorSide> GetDoors() {
		Logger.started(this, "GetDoors");
		// existing code
		Logger.finished(this, "GetDoors");
		return null;
	}

	/** */
	public void AddNeighbor(Room r) {
		Logger.started(this, "AddNeighbor", r);
		// existing code
		Logger.finished(this, "AddNeighbor", r);
	}

	/** */
	public void SetNeighbors(Room n) {
		Logger.started(this, "SetNeighbors", n);
		// existing code
		Logger.finished(this, "SetNeighbors", n);
	}

	/** */
	public boolean MergeRooms(Room r2) {
		Logger.started(this, "MergeRooms", r2);
		// existing code
		Logger.finished(this, "MergeRooms", r2);
		return false;
	}

	/** */
	public void SeparateRoom() {
		Logger.started(this, "SeparateRoom");
		// existing code
		Logger.finished(this, "SeparateRoom");
	}

	/** */
	public void AddInstructor(Instructor i) {
		Logger.started(this, "AddInstructor", i);
		i.SetRoom(this);
		if (poisonDuration > 0) {
			boolean defended = i.DefendFromGas();
			if (!defended) {
				i.SetIsFainted(true);
			}
		}
		instructors.add(i);
		people.add(i);
		Logger.finished(this, "AddInstructor", i);
	}

	/** */
	public void RemoveInstructor(Instructor i) {
		Logger.started(this, "RemoveInstructor", i);
		// existing code
		Logger.finished(this, "RemoveInstructor", i);
	}

	/** */
	public void AddStudent(Student s) {
		Logger.started(this, "AddStudent", s);
		s.SetRoom(this);
		if (poisonDuration > 0) {
			boolean defended = s.DefendFromGas();
			if (!defended) {
				s.SetIsFainted(true);
			}
		}
		students.add(s);
		people.add(s);
		Logger.finished(this, "AddStudent", s);
	}

	/** */
	public void RemoveStudent(Student s) {
		Logger.started(this, "RemoveStudent", s);
		// existing code
		Logger.finished(this, "RemoveStudent", s);
	}

	/** */
	public boolean RandomBool() {
		Logger.started(this, "RandomBool");
		// existing code
		Logger.finished(this, "RandomBool");
		return false;
	}

	/** */
	public Room SelectRoom(Room r) {
		Logger.started(this, "SelectRoom", r);
		// existing code
		Logger.finished(this, "SelectRoom", r);
		return null;
	}

	/** */
	public void ToggleDoorsVisible() {
		Logger.started(this, "ToggleDoorsVisible");
		// existing code
		Logger.finished(this, "ToggleDoorsVisible");
	}

	/** */
	public void SetIsCursed(boolean isc) {
		Logger.started(this, "SetIsCursed", isc);
		// existing code
		Logger.finished(this, "SetIsCursed", isc);
	}

	/** */
	public boolean GetIsCursed() {
		Logger.started(this, "GetIsCursed");
		// existing code
		Logger.finished(this, "GetIsCursed");
		return false;
	}
}
