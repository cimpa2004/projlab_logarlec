package model;


import util.Logger;
import view.VRoom;
import viewmodel.*;

import java.util.*;

/**
 * A Room osztály felelős egy szoba reprezentálásáért és annak működéséért a játékban.
 * Kezeli a szoba kapacitását, tartalmát, állapotát és a szobához kapcsolódó tevékenységeket.
 * */
public class Room implements IRoom, IVRoom {
	/**
	 *  Ez egy string típusú változó, amely egyértelműen azonosít egy Room -ot.
	 */
	protected String id;
	
	private IVRoomUpdate ivRoomUpdate;

	/**
	 * Ez az integer typusú változó tárolja, hogy a szobának hány körig tart még a mérgesgáz-effektje.
	 * */
	private int poisonDuration;

	/**
	 * Ez a boolean típusú változó jelzi, hogy a játék determinisztikus-e.
	 */
	private boolean isDeterministic;
	
	/**
	 * A szoba maximális kapacitása, azaz egyszerre legfeljebb hány személy tartózkodhat a szobában.
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
	 * Egy ArrayLista, ami a szobában található tárgyakat tartalmazza.
	 * */
	private ArrayList<Item> items;
	
	/**
	 * A szobában lévő ajtók a szoba felé néző oldalait tárolja (így oldható meg, hogy az egyik irányból látható
	 * vagy bezárt legyen, de a másik irányból nem)
	 * */
	private ArrayList<DoorSide> doors;

	/**
	 * A személyek közül azok a hallgatók, akik a szobában vannak.
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
	 * Ez a boolean típusú változó jelzi, hogy a szoba ragacsos-e.
	 */
	private boolean isSticky;

	/**
	 * Jelzi, hogy hány ember volt már a szobában. Ha a Janitor kitakaritja a szobát, akkor ez nullázodik, mert olyan
	 * tiszta lesz a szoba, hogy nem lehet megállapítani, hogy voltak-e benne.
	 */
	private int numberOfPeopleBeenToRoom;

	/**
	 * A Room osztály konstruktora.
	 * A Room id változóját a paraméterben kapott értékre állítja.
	 * Továbbá létrehozza a neighbors, items, doors, janitors, students és az instructors listákat.
	 *
	 * @param id A szoba leendő id -ja
	 */
	public Room(String id){
		this.id = id;
		neighbors = new ArrayList<>();
		items = new ArrayList<>();
		doors = new ArrayList<>();
		janitors = new ArrayList<>();
		students = new ArrayList<>();
		instructors = new ArrayList<>();

		currentCapacity = 0;
		maxCapacity = 10;
	}

	@Override
	public void SetIVRoomUpdate(IVRoomUpdate ivRoomUpdate){
		Logger.startedModel(this, "SetIVRoomUpdate", ivRoomUpdate);
		this.ivRoomUpdate = ivRoomUpdate;
		Logger.finishedModel(this, "SetIVRoomUpdate", ivRoomUpdate);
	}


	/**
	 * A Room osztály konstruktora.
	 * A Room id változóját egy random értékre állítja.
	 * Továbbá létrehozza a neighbors, items, doors, janitors, students és az instructors listákat.
	 */
	public Room(){
		this.id = "Room-"+UUID.randomUUID();
		neighbors = new ArrayList<>();
		items = new ArrayList<>();
		doors = new ArrayList<>();
		janitors = new ArrayList<>();
		students = new ArrayList<>();
		instructors = new ArrayList<>();

		currentCapacity = 0;
		maxCapacity = 10;
	}


	/**
	 * Vissza adja, hogy az adott szoba ragacsos-e.
	 *
	 * @return Az isSticky változó értéke.
	 */
	public boolean GetIsSticky(){
		Logger.startedModel(this, "GetIsSticky");
		Logger.finishedModel(this, "GetIsSticky");
		return isSticky;
	}

	/**
	 * Ezen metódus a Room isSticky változóját a paraméterben kapott értékre állítja.
	 * Ha hamisra állítja a változót, akkor reset -eli a numberOfPeopleBeenToRoom változó értékér
	 * azaz nullára állítja.
	 *
	 * @param value az isSticky változó új értéke.
	 */
	public void SetIsSticky(boolean value){
		Logger.startedModel(this, "SetIsSticky", value);
		this.isSticky = value;
		if (!value)  this.numberOfPeopleBeenToRoom = 0;
		Logger.finishedModel(this, "SetIsSticky", value);
	}

	/**
	 * A Room isDeterministic változóját beállítja a paraméterként kapott értékre.
	 *
	 * @param isDeterministic az isDeterministic változó új értéke.
	 */
	public void SetIsDeterministic(boolean isDeterministic){
		Logger.startedModel(this, "SetIsDeterministic");
		Logger.finishedModel(this, "SetIsDeterministic");
		this.isDeterministic = isDeterministic;
	}


	/**
	 * Ezen metódus a Room poisonDuration változóját csökkenti eggyel, ha az nagyobb mint 0.
	 * Ha a poisonDuration elérte a nullát, akkor felébreszti az elájult oktatókat és hallgatókat.
	 * */
	public void DecrementPoison() {
		Logger.startedModel(this, "DecrementPoison");
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
		Logger.finishedModel(this, "DecrementPoison");
	}

	/**
	 * Hozzáad egy tárgyat a szobához (amikor például egy tárgy eldobásra kerül).
	 * A Room items listájához hozzáadja a paraméterként kapott tárgyat és a
	 * tárgy Room változóját beállítja az adott Room -ra.
	 *
	 * @param i A hozzáadandó tárgy.
	 * */
	public void AddItem(Item i) {
		Logger.startedModel(this, "AddItem", i);
		items.add(i);
		i.SetRoom(this);
		Logger.finishedModel(this, "AddItem", i);
	}

	/**
	 * Elvesz egy tárgyat a szobából (amikor például egy tárgyat felvesznek).
	 * A Room items listájából eltávolítja a tárgyta és a tárgy room változóját nullára állítja.
	 *
	 * @param i Az eltávolítandó tárgy
	 * */
	public void RemoveItem(Item i) {
		Logger.startedModel(this, "RemoveItem", i);
		items.remove(i);
		i.SetRoom(null);
		Logger.finishedModel(this, "RemoveItem", i);
	}

	/**
	 * A szoba attribútumait (max kapacitás, mérgesgáz ideje, átkozottság, szomszédos szobák)
	 * másolja át a paraméterben megadott szobára. A szoba osztódásánál hívódik meg.
	 *
	 * @param r A Room, amely a jelenlegi Room klónja lesz.
	 * */
	public void CloneAttributes(Room r) {
		Logger.startedModel(this, "CloneAttributes", r);
		this.poisonDuration = r.GetPoisonDuration();
		this.isCursed = r.GetIsCursed();
		this.isSticky = r.GetIsSticky();
		this.maxCapacity = r.GetMaxCapacity();
		Logger.finishedModel(this, "CloneAttributes", r);
	}

	/**
	 * Visszaadja a szobában található tárgyakat.
	 *
	 * @return A Room items listája.
	 * */
	public ArrayList<Item> GetItems() {
		Logger.startedModel(this, "GetItems");
		Logger.finishedModel(this, "GetItems");
		return items;
	}

	public ArrayList<IVItem> GetIVItems() {
		Logger.startedModel(this, "GetIVItems");
		Logger.finishedModel(this, "GetIVItems");
		ArrayList<IVItem> ivItems = new ArrayList<IVItem>();
		for(Item i : items)
			ivItems.add((IVItem) i);
		return ivItems;
	}

	/**
	 * Hozzáad egy szomszédot a szobához.
	 *
	 * @param r: A hozzáadandó szoba
	 * */
	public void AddNeighbor(Room r) {
		Logger.startedModel(this, "AddNeighbor", r);
		neighbors.add(r);
        neighbors.remove(this);
		Logger.finishedModel(this, "AddNeighbor", r);
	}

	/**
	 * Elvesz egy szomszédot a szobától.
	 *
	 * @param r: Az elvevendő szoba (szerintem ez létező szó)
	 * */
	public void RemoveNeighbor(Room r) {
		Logger.startedModel(this, "RemoveNeighbor", r);
		neighbors.remove(r);
		Logger.finishedModel(this, "RemoveNeighbor", r);
	}

	/**
	 * Visszaadja a szobában tartózkodó hallgatókat.
	 *
	 * @return A Room students listája.
	 * */
	public ArrayList<Student> GetStudents() {
		Logger.startedModel(this, "GetStudents");
		Logger.finishedModel(this, "GetStudents");
		return students;
	}

	/**
	 * Visszaadja a szobában tartózkodó hallgatókat interface típusban.
	 *
	 * @return A Room students listája.
	 * */
	public ArrayList<IVStudent> GetIVStudents() {
		Logger.startedModel(this, "GetIVStudents");
		Logger.finishedModel(this, "GetIVStudents");
		ArrayList<IVStudent> ivStudents = new ArrayList<IVStudent>();
		for(Student s : students)
			ivStudents.add((IVStudent) s);
		return ivStudents;
	}

	/**
	 * Visszaadja a szobában tartózkodó oktatokat.
	 *
	 * @return A Room instructors listája.
	 * */
	public ArrayList<Instructor> GetInstructors() {
		Logger.startedModel(this, "GetInstructors");
		Logger.finishedModel(this, "GetInstructors");
		return instructors;
	}

	/**
	 * Visszaadja a szobában tartózkodó hallgatókat interface típusban.
	 *
	 * @return A Room students listája.
	 * */
	public ArrayList<IVInstructor> GetIVInstructors() {
		Logger.startedModel(this, "GetIVInstructors");
		Logger.finishedModel(this, "GetIVInstructors");
		ArrayList<IVInstructor> ivInstructors = new ArrayList<IVInstructor>();
		for(Instructor i : instructors)
			ivInstructors.add((IVInstructor) i);
		return ivInstructors;
	}

	/**
	 * Visszaadja a szobában tartózkodó takaritokat.
	 *
	 * @return A Room janitors listája.
	 * */
	public ArrayList<Janitor> GetJanitors() {
		Logger.startedModel(this, "GetJanitors");
		Logger.finishedModel(this, "GetJanitors");
		return janitors;
	}

	/**
	 * Visszaadja a szobában tartózkodó hallgatókat interface típusban.
	 *
	 * @return A Room students listája.
	 * */
	public ArrayList<IVJanitor> GetIVJanitors() {
		Logger.startedModel(this, "GetIVJanitors");
		Logger.finishedModel(this, "GetIVJanitors");
		ArrayList<IVJanitor> ivJanitors = new ArrayList<IVJanitor>();
		for(Janitor j : janitors)
			ivJanitors.add((IVJanitor) j);
		return ivJanitors;
	}

	/**
	 * Visszaadja a szoba jelenlegi kapacitását, azaz hogy jelenleg hány személy tartózkodik a szobában.
	 *
	 * @return A Room currentCapacity változója.
	 * */
	public int GetCurrentCapacity() {
		Logger.startedModel(this, "GetCurrentCapacity");
		Logger.finishedModel(this, "GetCurrentCapacity");
		return currentCapacity;
	}

	/**
	 * Visszaadja a szoba maximum kapacitását, azaz hogy egyszerre legfeljebb hány személy lehet a szobában.
	 *
	 * @return A Room maxCapacity változója.
	 * */
	public int GetMaxCapacity() {
		Logger.startedModel(this, "GetMaxCapacity");
		Logger.finishedModel(this, "GetMaxCapacity");
		return maxCapacity;
	}


	/**
	 * Visszaadja, hogy hány ember volt már a szobában az utolsó takarítás óta.
	 *
	 * @return A Room numberOfPeopleBeenToRoom változója.
	 * */
	public int GetNumberOfPeopleBeenToRoom() {
		Logger.startedModel(this, "NumberOfPeopleBeenToRoom");
		Logger.finishedModel(this, "NumberOfPeopleBeenToRoom");
		return numberOfPeopleBeenToRoom;
	}

	/**
	 * Beallitva, hogy hány ember volt már a szobában az utolsó takarítás óta a paraméterként kapott értékre.
	 *
	 * @param number A numberOfPeopleBeenToRoom változó új értéke.
	 * */
	public void SetNumberOfPeopleBeenToRoom(int number) {
		Logger.startedModel(this, "NumberOfPeopleBeenToRoom");
		numberOfPeopleBeenToRoom = number;
		Logger.finishedModel(this, "NumberOfPeopleBeenToRoom");
	}

	/**
	 * Beállítja a szoba jelenlegi kapacitását, azaz hogy egyszerre legfeljebb hány személy lehet a szobában.
	 *
	 * @param cc A currentCapacity változó új értéke.
	 * */
	public void SetCurrentCapacity(int cc) {
		Logger.startedModel(this, "GetMaxCapacity");
		currentCapacity = cc;
		Logger.finishedModel(this, "GetMaxCapacity");
	}

	/**
	 * Visszaadja a szoba szomszédait, azaz minden olyan szobát, amelybe innen ajtó vezet.
	 *
	 * @return A Room neighbors listája.
	 * */
	public ArrayList<Room> GetNeighbors() {
		Logger.startedModel(this, "GetNeighbors");
		Logger.finishedModel(this, "GetNeighbors");
		return neighbors;
	}

	/**
	 * Beállítja a szoba maximum kapacitását, azaz hogy egyszerre legfeljebb hány személy lehet a szobában.
	 *
	 * @param mc A maxCapacity változó új értéke.
	 * */
	public void SetMaxCapacity(int mc) {
		Logger.startedModel(this, "SetMaxCapacity", mc);
		maxCapacity = mc;
		Logger.finishedModel(this, "SetMaxCapacity", mc);
	}

	/**
	 * Visszaadja azt, hogy a szobában hány körön keresztül tart még a mérgesgáz hatása.
	 *
	 * @return A poisonDuration változó értéke.
	 * */
	public int GetPoisonDuration() {
		Logger.startedModel(this, "GetPoisonDuration");
		Logger.finishedModel(this, "GetPoisonDuration");
		return poisonDuration;
	}

	/**
	 * Beállítja, hogy a szobában hány körön keresztül tart még a mérgesgáz hatása.
	 * Ha ez az érték nagyobb, mint 0, akkor az összes szobában tartózkodó
	 * oktató és hallgató elájul, abban  az esetben ha nem tudják magukat a gáztól megvédeni.
	 *
	 * @param pd A poisonDuration változó új értéke.
	 * */
	public void SetPoisonDuration(int pd) {
		Logger.startedModel(this, "SetPoisonDuration", pd);
		poisonDuration = pd;
		if(poisonDuration > 0){
			for (Student s : students) {
				boolean isPersonDefended = s.DefendFromGas();
				if (!isPersonDefended) s.SetIsFainted(true);
			}
			for (Instructor i : instructors) {
				boolean isPersonDefended = i.DefendFromGas();
				if (!isPersonDefended) i.SetIsFainted(true);
			}
			if (ivRoomUpdate != null) ivRoomUpdate.RoomSetPoisonous(pd);
		}
		Logger.finishedModel(this, "SetPoisonDuration", pd);
	}

	/**
	 * Hozzáad egy ajtót a szobához. A szobák osztódásánál hívódik meg.
	 *
	 * @param d A Room -hoz hozzáadandó ajtó.
	 * */
	public void AddDoor(DoorSide d) {
		Logger.startedModel(this, "AddDoor", d);
		doors.add(d);
		Logger.finishedModel(this, "AddDoor", d);
	}

	/**
	 * Ezen metódus a paraméterként kapott ajtót eltávolítja a szoba ajtói közül,
	 * azaz a doors listából.
	 *
	 * @param d Az eltávolítandó ajtó.
	 */
	public void RemoveDoor(DoorSide d){
		Logger.startedModel(this, "RemoveDoor", d);
		doors.remove(d);
		Logger.finishedModel(this, "RemoveDoor", d);
	}

	/**
	 * Visszaadja a szobából kivezető ajtók szoba felé néző oldalait.
	 *
	 * @return A Room doors listája.
	 * */
	public ArrayList<DoorSide> GetDoors() {
		Logger.startedModel(this, "GetDoors");
		Logger.finishedModel(this, "GetDoors");
		return doors;
	}

	public ArrayList<IVDoorSide> GetIVDoors() {
		Logger.startedModel(this, "GetIVDoors");
		Logger.finishedModel(this, "GetIVDoors");
		ArrayList<IVDoorSide> ivDoors = new ArrayList<IVDoorSide>();
		for(IVDoorSide d : doors)
			ivDoors.add((IVDoorSide) d);
		return ivDoors;
	}

	/**
	 * Hozzáadja a paraméterként kapott oktatót a szobához (amikor belép)
	 * és elájul, hogyha a szoba gázos, és nincsen aktív FFP2-es maszk nála.
	 *
	 * @param i A hozzáadandó Instructor.
	 * */
	public void AddInstructor(Instructor i) {
		Logger.startedModel(this, "AddInstructor", i);
		i.SetRoom(this);
		currentCapacity++;
		numberOfPeopleBeenToRoom++;
		instructors.add(i);
		Logger.finishedModel(this, "AddInstructor", i);
	}

	/**
	 * Eltávolítja a paraméterként kapott Instructort a szobából (átment egy másik szobába)
	 *
	 * @param i Az eltávolítandó Instructor.
	 * */
	public void RemoveInstructor(Instructor i) {
		Logger.startedModel(this, "RemoveInstructor", i);
		i.SetRoom(null);
		instructors.remove(i);
		currentCapacity--;
		Logger.finishedModel(this, "RemoveInstructor", i);
	}

	/**
	 * Hozzáadja a paraméterként kapott Studentet a szobához (amikor belép),
	 * és elájul, hogyha a szoba gázos, és nincsen aktív FFP2-es maszk nála.
	 *
	 * @param s A hozzáadandó Student.
	 * */
	public void AddStudent(Student s) {
		Logger.startedModel(this, "AddStudent", s);
		s.SetRoom(this);
		currentCapacity++;
		numberOfPeopleBeenToRoom++;
		if(numberOfPeopleBeenToRoom >= 5)
			SetIsSticky(true);
		students.add(s);
		Logger.finishedModel(this, "AddStudent", s);
	}

	/**
	 * Eltávolítja a paraméterként kapott Studentet a szobából (átment egy másik szobába)
	 *
	 * @param s Az eltávolítandó Student.
	 * */
	public void RemoveStudent(Student s) {
		Logger.startedModel(this, "RemoveStudent", s);
		s.SetRoom(null);
		students.remove(s);
		currentCapacity--;
		Logger.finishedModel(this, "RemoveStudent", s);
	}

	/**
	 *  Ezen metódus a paraméterként kapott Janitort hozzáadja a szobához, azaz
	 * 	hozzáadja a Room janitors listájához és a Janitor room változóját beállítja a jelenlegi szobára.
	 *	Továbbá egy Janitor új szobába lépve kitakarítja a szobát, azaz az isSticky változót hamisra állítja és a
	 *	poisonDuration változót pedig nullára.
	 *	Ezeken kívül minden mozogni képes (nem bénult / ájult) embert kirak a szobából, még takarítás előtt.
	 *
	 * @param j A hozzáadandó Janitor.
	 * */
	public void AddJanitor(Janitor j) {
		Logger.startedModel(this, "AddJanitor", j);
		j.SetRoom(this);
		currentCapacity++;
		janitors.add(j);
		Logger.finishedModel(this, "AddJanitor", j);
	}

	/**
	 * Ezen metódus a paraméterként kapott Janitort eltávolítja a szobából, azaz
	 * kiveszi a Room janitors listájából.
	 *
	 * @param j Az eltávolítandó Janitor.
	 * */
	public void RemoveJanitor(Janitor j) {
		Logger.startedModel(this, "RemoveJanitor", j);
		j.SetRoom(null);
		janitors.remove(j);
		currentCapacity--;
		Logger.finishedModel(this, "RemoveJanitor", j);
	}

	/**
	 * Generál véletlenszerűen egy 'true' vagy 'false' bool értéket. A szobák osztódásánál kell ahhoz, hogy
	 * a tárgyak véletlenszerűen meg tudjanak feleződni a szobák között.
	 * Ha a játék determinisztikus akkor mindig igazat ad vissza.
	 *
	 * @return Egy random boolean érték.
	 * */
	public boolean RandomBool() {
		Logger.startedModel(this, "RandomBool");

		if(isDeterministic){
			Logger.finishedModel(this, "RandomBool");
			return true;
		}

		Random rand = new Random();
		Logger.finishedModel(this, "RandomBool");
		return rand.nextBoolean();
	}

	/**
	 * Véletlenszerűen kiválaszt egy szobát a paraméterként megadott ArrayListából, majd visszaadja.
	 *
	 * @param r A lista, amelyből egy Room -ot kell kiválasztani véletlenszerűen
	 * @return A véletlenszerűen kiválasztott Room
	 * */
	public Room SelectRoom(ArrayList<Room> r) {
		Logger.startedModel(this, "SelectRoom", r);
		if(r.isEmpty()){
			throw  new IllegalArgumentException("Error: Üres lista lett megadva!");
		}
		// Ha a játék determinisztikus akkor mindig az első szobát adja vissza.
		if(isDeterministic){
			Logger.finishedModel(this, "SelectRoom", r);
			return r.get(0);
		}

		// Ha a játék nem determinisztikus akkor véletlenszerűen kiválaszt egy DoorSide -ot
		Random randInt = new Random();
		Logger.finishedModel(this, "SelectRoom", r);
		return r.get(randInt.nextInt(r.size()));
	}

	/**
	 * Véletlenszerűen kiválaszt egy szobához tartozó ajtót, majd ha láthatatlan volt, akkor láthatóvá teszi,
	 * és fordítva (csak elátkozott szobákban fut le, minden kör végén egyszer)
	 * */
	public void ToggleDoorsVisible() {
		Logger.startedModel(this, "ToggleDoorsVisible");
		if(isCursed){
			// Ha a játék determinisztikus akkor mindig az első ajtót adja vissza.
			if(isDeterministic){
				DoorSide firstDoor = doors.get(0);
                firstDoor.SetIsVisible(!firstDoor.GetIsVisible());
			}
			// Ha a játék nem determinisztikus akkor véletlenszerűen kiválaszt egy DoorSide -ot
			Random randInt = new Random();
			DoorSide randomDoor = doors.get(randInt.nextInt(doors.size()));
			randomDoor.SetIsVisible(!randomDoor.GetIsVisible());

		}
		Logger.finishedModel(this, "ToggleDoorsVisible");
	}

	/**
	 * Beállítja, hogy a szoba elátkozott legyen, azaz az ajtajai véletlenszerűen megjelenjenek/eltűnjenek.
	 *
	 * @param isc Az isCursed változó leendő értéke.
	 * */
	public void SetIsCursed(boolean isc) {
		Logger.startedModel(this, "SetIsCursed", isc);
		isCursed = isc;
		Logger.finishedModel(this, "SetIsCursed", isc);
	}

	/**
	 * Ezen metódus visszaadja a Room id változójának értékét.
	 *
	 * @return A Room id változója.
	 */
	@Override
	public String GetID() {
        Logger.startedModel(this, "GetID");
        Logger.finishedModel(this, "GetID");
        return this.id;
    }

	/**
	 * Ezen metódus visszaadja, hogy a szoba elátkozott-e.
	 *
	 * @return Az isCursed változó értéke.
	 * */
	public boolean GetIsCursed() {
		Logger.startedModel(this, "GetIsCursed");
		Logger.finishedModel(this, "GetIsCursed");
		return isCursed;
	}

	@Override
	public VRoom GetVRoom(){
		Logger.startedModel(this, "GetVRoom");
		Logger.finishedModel(this, "GetVRoom");
		return ivRoomUpdate.GetVRoom();
	}

	
}
