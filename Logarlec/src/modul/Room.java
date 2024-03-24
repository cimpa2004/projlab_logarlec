package modul;


import util.Logger;
import util.Reader;

import java.util.ArrayList;
import java.util.List;

/** */
public class Room {
	/**
	 * Azt mutatja, hogy a szobának hány körig tart még a mérgesgáz-effektje. Ha eléri a 0-t a szobában minden
	 * hallgató és oktató felébred.
	 * */
	private int poisonDuration;
	
	/**
	 * A szoba maximum kapacitása, azaz egyszerre hány személy tartózkodhat a szobában.
	 * */
	private int maxCapacity;
	
	/**
	 * A szoba jelenlegi kapacitása, azaz éppen hány személy tartózkodik a szobában.
	 * */
	private int currentCapacity;
	
	/**
	 * Azt jelöli, hogy a szoba átkozott-e. Ha átkozott, akkor minden körben egy ajtaja véletlenszerűen
	 * megjelenik vegy eltűnik.
	 * */
	private boolean isCursed;
	
	/**
	 * A szobával szomszédos szobákat tartalmazza (azaz minden olyan szobát, amelybe innen ajtó vezet)
	 * */
	private ArrayList<Room> neighbors;
	
	/**
	 * EgyArrayLista, ami a szobában található tárgyakat tartalmazza.
	 * */
	private ArrayList<Item> items;
	
	/**
	 * A szobában lévő ajtók a szoba felé néző oldalait tárolja (így oldható meg, hogy az egyik irányból látható
	 * vagy bezárt legyen, de a másik irányból nem)
	 * */
	private ArrayList<DoorSide> doors;
	
	/**
	 * Minden olyan személy, aki jelenleg a szobában tartózkodik.
	 * */
	private ArrayList<Person> people;
	
	/**
	 * A személyek közül azok a hellgatók, akik a szobában vannak.
	 * */
	private ArrayList<Student> students;
	
	/**
	 * A személyek közül azok az oktatók, akik a szobában vannak.
	 * */
	private ArrayList<Instructor> instructors;

	public Room(){
		neighbors = new ArrayList<>();
		items = new ArrayList<>();
		doors = new ArrayList<>();
		people = new ArrayList<>();
		students = new ArrayList<>();
		instructors = new ArrayList<>();
	}


	/**
	 * A mérgesgáz idejének csökkentése. Ha minden hallgatónak és oktatónak volt köre, akkor lefut egyszer.
	 * */
	public void DecrementPoison() {
		Logger.started(this, "DecrementPoison");
		if(poisonDuration > 0) {
			poisonDuration--;
			if (poisonDuration == 0){
				for(Student s : students){
					s.SetIsFainted(false);
				}
				for(Instructor i: instructors){
					i.SetIsFainted(false);
				}
			}
		}
		Logger.finished(this, "DecrementPoison");
	}

	/**
	 * Hozzáad egy tárgyat a szobához (amikor például egy tárgy eldobásra kerül).
	 * */
	public void AddItem(Item i) {
		Logger.started(this, "AddItem", i);
		items.add(i);
		Logger.finished(this, "AddItem", i);
	}

	/**
	 * Elvesz egy tárgyat a szobából (amikor például egy tárgyat felvesznek).
	 * */
	public void RemoveItem(Item i) {
		Logger.started(this, "RemoveItem", i);
		// existing code
		Logger.finished(this, "RemoveItem", i);
	}

	/**
	 * A szoba attribútumait (max kapacitás, mérgesgáz ideje, átkozottság, szomszédos szobák)
	 * másolja át a paraméterben megadott szobára. A szoba osztódásánál hívódik meg.
	 * */
	public void CloneAttributes(Room r) {
		Logger.started(this, "CloneAttributes", r);
		// existing code
		Logger.finished(this, "CloneAttributes", r);
	}

	/**
	 * Visszaadja a szobában található tárgyakat.
	 * */
	public ArrayList<Item> GetItems() {
		Logger.started(this, "GetItems");
		// existing code
		Logger.finished(this, "GetItems");
		return null;
	}

	/**
	 * Visszaadja a szobában tartózkodó hallgatókat.
	 * */
	public ArrayList<Student> GetStudents() {
		Logger.started(this, "GetStudents");
		// existing code
		Logger.finished(this, "GetStudents");
		return null;
	}

	public ArrayList<Instructor> GetInstructors() {
		Logger.started(this, "GetInstructors");
		// existing code
		Logger.finished(this, "GetInstructors");
		return this.instructors;
	}

	/**
	 * Visszaadja a szoba jelenlegi kapacitását, azaz hogy jelenleg hány személy tartózkodik a szobában.
	 * */
	public int GetCurrentCapacity() {
		Logger.started(this, "GetCurrentCapacity");
		int _currentCapacity = Reader.GetIntInput("Mekkora a szoba jelenlegi kapacitasa?");
		Logger.finished(this, "GetCurrentCapacity");
		return _currentCapacity;
	}

	/**
	 * Visszaadja a szoba maximum kapacitását, azaz hogy egyszerre legfeljebb hány személy lehet a szobában.
	 * */
	public int GetMaxCapacity() {
		Logger.started(this, "GetMaxCapacity");
		int _maxCapacity = Reader.GetIntInput("Mekkora a szoba maximum kapacitasa?");
		Logger.finished(this, "GetMaxCapacity");
		return _maxCapacity;
	}

	/**
	 * Beállítja a szoba jelenlegi kapacitását, azaz hogy egyszerre legfeljebb hány személy lehet a szobában.
	 * */
	public void SetCurrentCapacity(int cc) {
		Logger.started(this, "GetMaxCapacity");
		currentCapacity = cc;
		Logger.finished(this, "GetMaxCapacity");
	}

	/**
	 * Visszaadja a szoba szomszédait, azaz minden olyan szobát, amelybe innen ajtó vezet.
	 * */
	public ArrayList<Room> GetNeighbors() {
		Logger.started(this, "GetNeighbors");
		// existing code
		Logger.finished(this, "GetNeighbors");
		return null;
	}

	/**
	 * Beállítja a szoba maximum kapacitását, azaz hogy egyszerre legfeljebb hány személy lehet a szobában.
	 * */
	public void SetMaxCapacity(int mc) {
		Logger.started(this, "SetMaxCapacity", mc);
		// existing code
		Logger.finished(this, "SetMaxCapacity", mc);
	}

	/**
	 * Visszaadja azt, hogy a szobában hány körön keresztül tart még a mérgesgáz hatása.
	 * */
	public int GetPoisonDuration() {
		Logger.started(this, "GetPoisonDuration");
		// existing code
		Logger.finished(this, "GetPoisonDuration");
		return 0;
	}

	/** Beállítja azt, hogy a szobában hány körön keresztül tart még a mérgesgáz hatása.*/
	public void SetPoisonDuration(int pd) {
		Logger.started(this, "SetPoisonDuration", pd);
		poisonDuration = pd;
		for (Person p : people) {
			boolean isPersonDefended = p.DefendFromGas();
			if (!isPersonDefended) p.SetIsFainted(true);
		}
		Logger.finished(this, "SetPoisonDuration", pd);
	}

	/**
	 * Hozzáad egy ajtót a szobához. A szobák osztódásánál hívódik meg.
	 * */
	public void AddDoor(DoorSide d) {
		Logger.started(this, "AddDoor", d);
		doors.add(d);
		Logger.finished(this, "AddDoor", d);
	}

	/**
	 * Visszaadja a szobából kivezető ajtók szoba felé néző oldalait.
	 * */
	public ArrayList<DoorSide> GetDoors() {
		Logger.started(this, "GetDoors");
		// existing code
		Logger.finished(this, "GetDoors");
		return null;
	}

	/**
	 * Hozzáad egy szomszédos szobát a szobához, azaz egy olyan szobát, amelyikbe innen vezet ajtó.
	 * A szobák osztódásánál hívódik meg.
	 * */
	public void AddNeighbor(Room r) {
		Logger.started(this, "AddNeighbor", r);
		// existing code
		Logger.finished(this, "AddNeighbor", r);
	}

	/**
	 * Beállítja a szoba szomszédait.
	 * */
	public void SetNeighbors(ArrayList<Room> n) {
		Logger.started(this, "SetNeighbors", n);
		// existing code
		Logger.finished(this, "SetNeighbors", n);
	}

	/**
	 * Összeolvasztja a szobát a paraméterben kapott másik szobával. A maximum kapacitása a nagyobb szoba befogadó-
	 * képességével lesz egyenlő, a mérgesgáz ideje a hosszabb ideig tartó idő lesz, valamint elátkozott lesz, ha
	 * legalább az egyik szoba elátkozott. Az új szoba szomszédjai a régi két szoba szomszédjainak uniója.
	 * Csak akkor hívódik meg, ha mindkét szoba aktuális kapacitása jelenleg 0.
	 * A szobákban lévő tárgyak az új szobába kerülnek.
	 * */
	public boolean MergeRooms(Room r2) {
		Logger.started(this, "MergeRooms", r2);
		// existing code
		Logger.finished(this, "MergeRooms", r2);
		return false;
	}

	/**
	 * A szoba két szobára válik. Mindkét szoba maximum kapacitása egyenlő lesz az eredeti befogadóképességével, valamint
	 * a mérgesgáz- és elátkozottság attribútumok és a szobák szomszédjai is lemásolódnak. Az két új szoba közés is
	 * kerül egy ajtó. Csak akkor hívódik meg, ha a szoba aktuális kapacitása jelenleg 0.
	 * A szobában lévő tárgyak a két szoba között véletlenszerűen lesznek elszórva.
	 * */
	public void SeparateRoom() {
		Logger.started(this, "SeparateRoom");
		// existing code
		Logger.finished(this, "SeparateRoom");
	}

	/**
	 * Hozzáad egy oktatót a szobához (amikor belép), és elájul, hogyha a szoba gázos, és nincsen aktív FFP2-es maszk nála.
	 * */
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

	/**
	 * Elvesz egy oktatót a szobából (átment egy másik szobába)
	 * */
	public void RemoveInstructor(Instructor i) {
		Logger.started(this, "RemoveInstructor", i);
		// existing code
		Logger.finished(this, "RemoveInstructor", i);
	}

	/**
	 * Hozzáad egy hallgatót a szobához (amikor belép), és elájul, hogyha a szoba gázos, és nincsen aktív FFP2-es maszk nála.
	 * */
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

	/**
	 * Elvesz egy hallgatót a szobából (átment egy másik szobába)
	 * */
	public void RemoveStudent(Student s) {
		Logger.started(this, "RemoveStudent", s);
		// existing code
		Logger.finished(this, "RemoveStudent", s);
	}

	/**
	 * Generál véletlenszerűen egy 'true' vagy 'false' bool értéket. A szobák osztódásánál kell ahhoz, hogy
	 * a tárgyak véletlenszerűen meg tudjanak feleződni a szibák között.
	 * */
	public boolean RandomBool() {
		Logger.started(this, "RandomBool");
		// existing code
		Logger.finished(this, "RandomBool");
		return false;
	}

	/**
	 * Véletlenszerűen kiválaszt egy szobát a megadottArrayListából, majd visszaadja.
	 * */
	public Room SelectRoom(ArrayList<Room> r) {
		Logger.started(this, "SelectRoom", r);
		// existing code
		Logger.finished(this, "SelectRoom", r);
		return null;
	}

	/**
	 * Véletlenszerűen kiválaszt egy szobához tartozó ajtót, majd ha láthatatlan volt, akkor láthatóvá teszi,
	 * és fordítva (csak elátkozott szobákban fut le, minden kör végén egyszer)
	 * */
	public void ToggleDoorsVisible() {
		Logger.started(this, "ToggleDoorsVisible");
		// existing code
		Logger.finished(this, "ToggleDoorsVisible");
	}

	/**
	 * Beállítja, hogy a szoba elátkozott legyen, azaz az ajtajai véletlenszerűen megjelenjenek/eltűnjenek.
	 * */
	public void SetIsCursed(boolean isc) {
		Logger.started(this, "SetIsCursed", isc);
		// existing code
		Logger.finished(this, "SetIsCursed", isc);
	}

	/**
	 * Visszaadja, hogy a szoba elátkozott-e.
	 * */
	public boolean GetIsCursed() {
		Logger.started(this, "GetIsCursed");
		// existing code
		Logger.finished(this, "GetIsCursed");
		return false;
	}
}
