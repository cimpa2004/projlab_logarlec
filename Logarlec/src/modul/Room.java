package modul;


import util.Logger;
import util.Reader;

import java.util.ArrayList;
import java.util.Iterator;
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
		i.SetRoom(this);
		Logger.finished(this, "AddItem", i);
	}

	/** */
	public void RemoveItem(Item i) {
		Logger.started(this, "RemoveItem", i);
		items.remove(i);
		i.SetRoom(null);
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
		Logger.finished(this, "GetItems");
		return items;
	}

	/** */
	public List<Student> GetStudents() {
		Logger.started(this, "GetStudents");
		// existing code
		Logger.finished(this, "GetStudents");
		return null;
	}

	public List<Instructor> GetInstructors() {
		Logger.started(this, "GetInstructors");
		// existing code
		Logger.finished(this, "GetInstructors");
		return this.instructors;
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
		Logger.finished(this, "GetNeighbors");
		return neighbors;
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

	public void RemoveDoor(DoorSide d){
		Logger.started(this, "RemoveDoor", d);
		doors.remove(d);
		Logger.finished(this, "RemoveDoor", d);
	}

	/** */
	public List<DoorSide> GetDoors() {
		Logger.started(this, "GetDoors");
		Logger.finished(this, "GetDoors");
		return doors;
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

		// Csak akkor egyesíthetünk két szobát, ha egyikben sem tartózkodik egy Person sem.
		if(this.GetCurrentCapacity() == 0 && r2.GetCurrentCapacity() == 0){

			// Az egyesült szoba maximális kapacitása a két szoba
			// maximális kapacitása közül a nagyobbik lesz.
			if(r2.GetMaxCapacity() > this.GetMaxCapacity()){
				this.SetMaxCapacity(r2.GetMaxCapacity());
			}

			// Ha az egyik szoba is elátkozott, akkor
			// az egyesült szoba is az lesz.
			if(r2.GetIsCursed()){
				this.SetIsCursed(r2.GetIsCursed());
			}

			// Az egyesült szoba poisonDuration változója a két szoba
			// poisonDuration -je közül a nagyobbik lesz.
			if(r2.GetPoisonDuration() > this.GetPoisonDuration()){
				this.SetPoisonDuration(r2.GetPoisonDuration());
			}

			// Minden az r2 -ben lévő tárgyat áthelyezünk az r1 -be.
			for(Item item : r2.GetItems()){
				r2.RemoveItem(item);
				this.AddItem(item);
			}

			// Az ajtók közti összeköttetéseket frissíteni kell
			// Végigmegyünk a beolvasztandó (r2) szoba összes DoorSide -ján.
			// Iterátort érdemes használnunk, mivel a ciklus futása közben szeretnénk
			// változtatni a listán.

			Iterator<DoorSide> iter = r2.GetDoors().iterator();

			while(iter.hasNext()){
				DoorSide doorSide = iter.next();

				// Ha egy DoorSide az r1 -el köti össze az r2 -t, akkor
				// már nem lesz szükség ezekre a félajtókra, hiszen az r2 -t
				// az r1 -be olvasztjuk.
				if(doorSide.GetPair().GetRoom() == this){
					this.RemoveDoor(doorSide.GetPair());
					doorSide.SetRoom(null);
					doorSide.SetRoom(null);
					iter.remove();
				}else{
					// Ha egy DoorSide egy másik szobával köti össze az r2 -t,
					// akkor azt át kell alakítanunk, hogy a másik szoba az r1 -el
					// legyen összeköttetésben
					doorSide.SetRoom(this);
					iter.remove();
				}
			}

			// Legvégül kivesszük a szobákat egymás szomszédai közül.
			this.GetNeighbors().remove(r2);
			r2.GetNeighbors().remove(this);

			Logger.finished(this, "MergeRooms", r2);
			return true;
		}

		// Ha van bárki is az egyik szobában, akkor nem hajtódik
		// végre az egyesítés.
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
