package modul;


import util.Logger;
import util.Reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

// TODO: kiszedni az összes AddNeighbor, RemoveNeighbor stb logikákat mert már a Game végzi ezek beállítását
//  és a determinisztikusság attól függjön, hogy mi az isDeterministic értéke

/** */
public class Room implements IRoom {
	/**
	 * Az adott Roomot egyertelmuen azonositja
	 */
	protected String id;

	/**
	 * Azt mutatja, hogy a szobának hány körig tart még a mérgesgáz-effektje. Ha eléri a 0-t a szobában minden
	 * hallgató és oktató felébred.
	 * */
	private int poisonDuration;

	/**
	 * Jelzi, hogy a jatek determinisztikus-e
	 */
	private boolean isDeterministic;
	
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
	 * A személyek közül azok a hellgatók, akik a szobában vannak.
	 * */
	private ArrayList<Student> students;
	
	/**
	 * A személyek közül azok az oktatók, akik a szobában vannak.
	 * */
	private ArrayList<Instructor> instructors;

	/**
	 * A személyek közül azok az takarítók, akik a szobában vannak.
	 * */
	private ArrayList<Janitor> janitors;

	/**
	 * Jelzi, hogy a szoba ragacsos-e.
	 */
	private boolean isSticky;

	/**
	 * Jelzi, hogy hány ember volt már a szobában. Ha a Janitor kitakaritja a szobát, akkor ez nullázodik, mert olyan
	 * tiszta lesz a szoba, hogy nem lehet megállapítani, hogy voltak-e benne.
	 */
	private int numberOfPeopleBeenToRoom;

	public Room(String id){
		this.id = id;
		neighbors = new ArrayList<>();
		items = new ArrayList<>();
		doors = new ArrayList<>();
		janitors = new ArrayList<>();
		students = new ArrayList<>();
		instructors = new ArrayList<>();
	}

	public Room(){
		this.id = UUID.randomUUID().toString();
		neighbors = new ArrayList<>();
		items = new ArrayList<>();
		doors = new ArrayList<>();
		janitors = new ArrayList<>();
		students = new ArrayList<>();
		instructors = new ArrayList<>();
	}


	/**
	 * Vissza adja, hogy az adott szoba ragacsos-e.
	 * @return Az érték, ami jelzi, hogy ragacsos-e a szoba
	 */
	public boolean GetIsSticky(){
		Logger.started(this, "DecrementPoison");
		Logger.finished(this, "DecrementPoison");
		return isSticky;
	}

	public void SetIsSticky(boolean value){
		this.isSticky = value;
		if (!value)
			this.numberOfPeopleBeenToRoom = 0;
	}

	public void SetIsDeterministic(boolean isDeterministic){
		Logger.started(this, "SetIsDeterministic");
		Logger.finished(this, "SetIsDeterministic");
		this.isDeterministic = isDeterministic;
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
		i.SetRoom(this);
		Logger.finished(this, "AddItem", i);
	}

	/**
	 * Elvesz egy tárgyat a szobából (amikor például egy tárgyat felvesznek).
	 * */
	public void RemoveItem(Item i) {
		Logger.started(this, "RemoveItem", i);
		items.remove(i);
		i.SetRoom(null);
		Logger.finished(this, "RemoveItem", i);
	}

	/**
	 * A szoba attribútumait (max kapacitás, mérgesgáz ideje, átkozottság, szomszédos szobák)
	 * másolja át a paraméterben megadott szobára. A szoba osztódásánál hívódik meg.
	 * */
	public void CloneAttributes(Room r) {
		Logger.started(this, "CloneAttributes", r);
		this.poisonDuration = r.poisonDuration;
		this.isCursed = r.isCursed;
		this.maxCapacity = r.maxCapacity;
		this.neighbors = r.neighbors;
		r.AddNeighbor(this);
		this.AddNeighbor(r);
		Logger.finished(this, "CloneAttributes", r);
	}

	/**
	 * Visszaadja a szobában található tárgyakat.
	 * */
	public ArrayList<Item> GetItems() {
		Logger.started(this, "GetItems");
		Logger.finished(this, "GetItems");
		return items;
	}

	/**
	 * Visszaadja a szobában tartózkodó hallgatókat.
	 * */
	public ArrayList<Student> GetStudents() {
		Logger.started(this, "GetStudents");
		// existing code
		Logger.finished(this, "GetStudents");
		return students;
	}

	/**
	 * Visszaadja a szobában tartózkodó oktatokat.
	 * */
	public ArrayList<Instructor> GetInstructors() {
		Logger.started(this, "GetInstructors");
		// existing code
		Logger.finished(this, "GetInstructors");
		return this.instructors;
	}

	/**
	 * Visszaadja a szobában tartózkodó takaritokat.
	 * */
	public ArrayList<Janitor> GetJanitors() {
		Logger.started(this, "GetJanitors");
		// existing code
		Logger.finished(this, "GetJanitors");
		return this.janitors;
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
	 * Visszaadja, hogy hány ember volt már a szobában az utolso takaritas ota
	 * */
	public int GetNumberOfPeopleBeenToRoom() {
		Logger.started(this, "NumberOfPeopleBeenToRoom");
		Logger.finished(this, "NumberOfPeopleBeenToRoom");
		return numberOfPeopleBeenToRoom;
	}

	/**
	 * Beallitva, hogy hány ember volt már a szobában az utolso takaritas ota
	 * */
	public void SetNumberOfPeopleBeenToRoom(int number) {
		Logger.started(this, "NumberOfPeopleBeenToRoom");
		numberOfPeopleBeenToRoom = number;
		Logger.finished(this, "NumberOfPeopleBeenToRoom");
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
		Logger.finished(this, "GetNeighbors");
		return neighbors;
	}

	/**
	 * Beállítja a szoba maximum kapacitását, azaz hogy egyszerre legfeljebb hány személy lehet a szobában.
	 * */
	public void SetMaxCapacity(int mc) {
		Logger.started(this, "SetMaxCapacity", mc);
		maxCapacity = mc;
		Logger.finished(this, "SetMaxCapacity", mc);
	}

	/**
	 * Visszaadja azt, hogy a szobában hány körön keresztül tart még a mérgesgáz hatása.
	 * */
	public int GetPoisonDuration() {
		Logger.started(this, "GetPoisonDuration");
		Logger.finished(this, "GetPoisonDuration");
		return poisonDuration;
	}

	/** Beállítja azt, hogy a szobában hány körön keresztül tart még a mérgesgáz hatása.*/
	public void SetPoisonDuration(int pd) {
		Logger.started(this, "SetPoisonDuration", pd);
		poisonDuration = pd;
		for (Student s : students) {
			boolean isPersonDefended = s.DefendFromGas();
			if (!isPersonDefended) s.SetIsFainted(true);
		}
		for (Instructor i : instructors) {
			boolean isPersonDefended = i.DefendFromGas();
			if (!isPersonDefended) i.SetIsFainted(true);
		}
		for (Janitor j : janitors) {
			boolean isPersonDefended = j.DefendFromGas();
			if (!isPersonDefended) j.SetIsFainted(true);
		}
		Logger.finished(this, "SetPoisonDuration", pd);
	}

	/**
	 * Hozzáad egy ajtót a szobához. A szobák osztódásánál hívódik meg.
	 * */
	public void AddDoor(DoorSide d) {
		Logger.started(this, "AddDoor", d);
		doors.add(d);
		// Set neighboors
		if(d.GetPair() != null && d.GetPair().GetRoom() != null){
			Room connectedRoom = d.GetPair().GetRoom();
			this.AddNeighbor(connectedRoom);
			if(!connectedRoom.GetNeighbors().contains(this)) connectedRoom.AddNeighbor(this);
		}
		Logger.finished(this, "AddDoor", d);
	}

	public void RemoveDoor(DoorSide d){
		Logger.started(this, "RemoveDoor", d);
		doors.remove(d);
		Logger.finished(this, "RemoveDoor", d);
	}

	/**
	 * Visszaadja a szobából kivezető ajtók szoba felé néző oldalait.
	 * */
	public ArrayList<DoorSide> GetDoors() {
		Logger.started(this, "GetDoors");
		Logger.finished(this, "GetDoors");
		return doors;
	}

	/**
	 * Hozzáad egy szomszédos szobát a szobához, azaz egy olyan szobát, amelyikbe innen vezet ajtó.
	 * A szobák osztódásánál hívódik meg.
	 * */
	public void AddNeighbor(Room r) {
		Logger.started(this, "AddNeighbor", r);
		neighbors.add(r);
		Logger.finished(this, "AddNeighbor", r);
	}

	/**
	 * Beállítja a szoba szomszédait.
	 * */
	public void SetNeighbors(ArrayList<Room> n) {
		Logger.started(this, "SetNeighbors", n);
		// TODO: függvény implementálása
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

	/**
	 * A szoba két szobára válik. Mindkét szoba maximum kapacitása egyenlő lesz az eredeti befogadóképességével, valamint
	 * a mérgesgáz- és elátkozottság attribútumok és a szobák szomszédjai is lemásolódnak. Az két új szoba közés is
	 * kerül egy ajtó. Csak akkor hívódik meg, ha a szoba aktuális kapacitása jelenleg 0.
	 * A szobában lévő tárgyak a két szoba között véletlenszerűen lesznek elszórva.
	 * */
	public boolean SeparateRoom() {
		Logger.started(this, "SeparateRoom");
		if(currentCapacity == 0)
		{
			Room r2 = new Room(UUID.randomUUID().toString());
			r2.CloneAttributes(this);
			for (DoorSide d : doors)
			{
				DoorSide dCopy = new DoorSide(UUID.randomUUID().toString());
				dCopy.CloneAttributes(d);
				DoorSide d2 = d.GetPair();
				DoorSide d2Copy = new DoorSide(UUID.randomUUID().toString());
				d2Copy.CloneAttributes(d2);
				dCopy.ConnectDoors(d2Copy);
			}
			ArrayList<Item> itemsCopy = new ArrayList<Item>(items);
			for (Item i: itemsCopy){
				boolean trueorfalse = Reader.GetBooleanInput("Melyik szobába kerüljön a tárgy (false: az eredeti szobába, true: az új szobába)");
				if(trueorfalse){
					r2.AddItem(i);
					RemoveItem(i);
				}
			}
			return true;
		}
		Logger.finished(this, "SeparateRoom");
		return false;
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
		Logger.finished(this, "AddInstructor", i);
	}

	/**
	 * Elvesz egy oktatót a szobából (átment egy másik szobába)
	 * */
	public void RemoveInstructor(Instructor i) {
		Logger.started(this, "RemoveInstructor", i);
		instructors.remove(i);
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
		Logger.finished(this, "AddStudent", s);
	}

	/**
	 * Elvesz egy hallgatót a szobából (átment egy másik szobába)
	 * */
	public void RemoveStudent(Student s) {
		Logger.started(this, "RemoveStudent", s);
		students.remove(s);
		Logger.finished(this, "RemoveStudent", s);
	}

	/**
	 * Hozzáad egy takarítót a szobához (amikor belép), és elájul, hogyha a szoba gázos, és nincsen aktív FFP2-es maszk nála.
	 * */
	public void AddJanitor(Janitor j) {
		Logger.started(this, "AddJanitor", j);
		j.SetRoom(this);
		// TODO szoba tisztitása itt
		janitors.add(j);
		Logger.finished(this, "AddJanitor", j);
	}

	/**
	 * Elvesz egy takarítót a szobából (átment egy másik szobába)
	 * */
	public void RemoveJanitor(Janitor j) {
		Logger.started(this, "RemoveJanitor", j);
		janitors.remove(j);
		Logger.finished(this, "RemoveJanitor", j);
	}

	/**
	 * Generál véletlenszerűen egy 'true' vagy 'false' bool értéket. A szobák osztódásánál kell ahhoz, hogy
	 * a tárgyak véletlenszerűen meg tudjanak feleződni a szibák között.
	 * */
	public boolean RandomBool() {
		Logger.started(this, "RandomBool");
		//TODO: implement: ha determinisztikus a játék akkor mindig csak egy értéket adjon vissza
		Logger.finished(this, "RandomBool");
		return false;
	}

	/**
	 * Véletlenszerűen kiválaszt egy szobát a megadottArrayListából, majd visszaadja.
	 * */
	public Room SelectRoom(ArrayList<Room> r) {
		Logger.started(this, "SelectRoom", r);
		//TODO: implement
		Logger.finished(this, "SelectRoom", r);
		return null;
	}

	/**
	 * Véletlenszerűen kiválaszt egy szobához tartozó ajtót, majd ha láthatatlan volt, akkor láthatóvá teszi,
	 * és fordítva (csak elátkozott szobákban fut le, minden kör végén egyszer)
	 * */
	public void ToggleDoorsVisible() {
		Logger.started(this, "ToggleDoorsVisible");
		//TODO: implement
		Logger.finished(this, "ToggleDoorsVisible");
	}

	/**
	 * Beállítja, hogy a szoba elátkozott legyen, azaz az ajtajai véletlenszerűen megjelenjenek/eltűnjenek.
	 * */
	public void SetIsCursed(boolean isc) {
		Logger.started(this, "SetIsCursed", isc);
		//TODO: implement
		Logger.finished(this, "SetIsCursed", isc);
	}

	@Override
	public String GetId() {
		return id;
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
