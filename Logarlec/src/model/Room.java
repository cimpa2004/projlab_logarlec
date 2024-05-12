package model;


import util.Logger;
import viewmodel.IRoom;
import viewmodel.IVRoom;

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

	/**
	 * A Room osztály konstruktora.
	 * A Room id változóját egy random értékre állítja.
	 * Továbbá létrehozza a neighbors, items, doors, janitors, students és az instructors listákat.
	 */
	public Room(){
		this.id = UUID.randomUUID().toString();
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
		Logger.started(this, "GetIsSticky");
		Logger.finished(this, "GetIsSticky");
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
		this.isSticky = value;
		if (!value)
			this.numberOfPeopleBeenToRoom = 0;
	}

	/**
	 * A Room isDeterministic változóját beállítja a paraméterként kapott értékre.
	 *
	 * @param isDeterministic az isDeterministic változó új értéke.
	 */
	public void SetIsDeterministic(boolean isDeterministic){
		Logger.started(this, "SetIsDeterministic");
		Logger.finished(this, "SetIsDeterministic");
		this.isDeterministic = isDeterministic;
	}


	/**
	 * Ezen metódus a Room poisonDuration változóját csökkenti eggyel, ha az nagyobb mint 0.
	 * Ha a poisonDuration elérte a nullát, akkor felébreszti az elájult oktatókat és hallgatókat.
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
	 * A Room items listájához hozzáadja a paraméterként kapott tárgyat és a
	 * tárgy Room változóját beállítja az adott Room -ra.
	 *
	 * @param i A hozzáadandó tárgy.
	 * */
	public void AddItem(Item i) {
		Logger.started(this, "AddItem", i);
		items.add(i);
		i.SetRoom(this);
		Logger.finished(this, "AddItem", i);
	}

	/**
	 * Elvesz egy tárgyat a szobából (amikor például egy tárgyat felvesznek).
	 * A Room items listájából eltávolítja a tárgyta és a tárgy room változóját nullára állítja.
	 *
	 * @param i Az eltávolítandó tárgy
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
	 *
	 * @param r A Room, amely a jelenlegi Room klónja lesz.
	 * */
	public void CloneAttributes(Room r) {
		Logger.started(this, "CloneAttributes", r);
		this.poisonDuration = r.GetPoisonDuration();
		this.isCursed = r.GetIsCursed();
		this.isSticky = r.GetIsSticky();
		this.maxCapacity = r.GetMaxCapacity();
		Logger.finished(this, "CloneAttributes", r);
	}

	/**
	 * Visszaadja a szobában található tárgyakat.
	 *
	 * @return A Room items listája.
	 * */
	public ArrayList<Item> GetItems() {
		Logger.started(this, "GetItems");
		Logger.finished(this, "GetItems");
		return items;
	}

	/**
	 * Hozzáad egy szomszédot a szobához.
	 *
	 * @param r: A hozzáadandó szoba
	 * */
	public void AddNeighbor(Room r) {
		neighbors.add(r);
        neighbors.remove(this);
	}

	/**
	 * Elvesz egy szomszédot a szobától.
	 *
	 * @param r: Az elvevendő szoba (szerintem ez létező szó)
	 * */
	public void RemoveNeighbor(Room r) {
		neighbors.remove(r);
	}

	/**
	 * Visszaadja a szobában tartózkodó hallgatókat.
	 *
	 * @return A Room students listája.
	 * */
	public ArrayList<Student> GetStudents() {
		Logger.started(this, "GetStudents");
		Logger.finished(this, "GetStudents");
		return students;
	}

	/**
	 * Visszaadja a szobában tartózkodó oktatokat.
	 *
	 * @return A Room instructors listája.
	 * */
	public ArrayList<Instructor> GetInstructors() {
		Logger.started(this, "GetInstructors");
		Logger.finished(this, "GetInstructors");
		return instructors;
	}

	/**
	 * Visszaadja a szobában tartózkodó takaritokat.
	 *
	 * @return A Room janitors listája.
	 * */
	public ArrayList<Janitor> GetJanitors() {
		Logger.started(this, "GetJanitors");
		Logger.finished(this, "GetJanitors");
		return janitors;
	}

	/**
	 * Visszaadja a szoba jelenlegi kapacitását, azaz hogy jelenleg hány személy tartózkodik a szobában.
	 *
	 * @return A Room currentCapacity változója.
	 * */
	public int GetCurrentCapacity() {
		Logger.started(this, "GetCurrentCapacity");
		Logger.finished(this, "GetCurrentCapacity");
		return currentCapacity;
	}

	/**
	 * Visszaadja a szoba maximum kapacitását, azaz hogy egyszerre legfeljebb hány személy lehet a szobában.
	 *
	 * @return A Room maxCapacity változója.
	 * */
	public int GetMaxCapacity() {
		Logger.started(this, "GetMaxCapacity");
		Logger.finished(this, "GetMaxCapacity");
		return maxCapacity;
	}


	/**
	 * Visszaadja, hogy hány ember volt már a szobában az utolsó takarítás óta.
	 *
	 * @return A Room numberOfPeopleBeenToRoom változója.
	 * */
	public int GetNumberOfPeopleBeenToRoom() {
		Logger.started(this, "NumberOfPeopleBeenToRoom");
		Logger.finished(this, "NumberOfPeopleBeenToRoom");
		return numberOfPeopleBeenToRoom;
	}

	/**
	 * Beallitva, hogy hány ember volt már a szobában az utolsó takarítás óta a paraméterként kapott értékre.
	 *
	 * @param number A numberOfPeopleBeenToRoom változó új értéke.
	 * */
	public void SetNumberOfPeopleBeenToRoom(int number) {
		Logger.started(this, "NumberOfPeopleBeenToRoom");
		numberOfPeopleBeenToRoom = number;
		Logger.finished(this, "NumberOfPeopleBeenToRoom");
	}

	/**
	 * Beállítja a szoba jelenlegi kapacitását, azaz hogy egyszerre legfeljebb hány személy lehet a szobában.
	 *
	 * @param cc A currentCapacity változó új értéke.
	 * */
	public void SetCurrentCapacity(int cc) {
		Logger.started(this, "GetMaxCapacity");
		currentCapacity = cc;
		Logger.finished(this, "GetMaxCapacity");
	}

	/**
	 * Visszaadja a szoba szomszédait, azaz minden olyan szobát, amelybe innen ajtó vezet.
	 *
	 * @return A Room neighbors listája.
	 * */
	public ArrayList<Room> GetNeighbors() {
		Logger.started(this, "GetNeighbors");
		Logger.finished(this, "GetNeighbors");
		return neighbors;
	}

	/**
	 * Beállítja a szoba maximum kapacitását, azaz hogy egyszerre legfeljebb hány személy lehet a szobában.
	 *
	 * @param mc A maxCapacity változó új értéke.
	 * */
	public void SetMaxCapacity(int mc) {
		Logger.started(this, "SetMaxCapacity", mc);
		maxCapacity = mc;
		Logger.finished(this, "SetMaxCapacity", mc);
	}

	/**
	 * Visszaadja azt, hogy a szobában hány körön keresztül tart még a mérgesgáz hatása.
	 *
	 * @return A poisonDuration változó értéke.
	 * */
	public int GetPoisonDuration() {
		Logger.started(this, "GetPoisonDuration");
		Logger.finished(this, "GetPoisonDuration");
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
		Logger.started(this, "SetPoisonDuration", pd);
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
		}
		Logger.finished(this, "SetPoisonDuration", pd);
	}

	/**
	 * Hozzáad egy ajtót a szobához. A szobák osztódásánál hívódik meg.
	 *
	 * @param d A Room -hoz hozzáadandó ajtó.
	 * */
	public void AddDoor(DoorSide d) {
		Logger.started(this, "AddDoor", d);
		doors.add(d);
		Logger.finished(this, "AddDoor", d);
	}

	/**
	 * Ezen metódus a paraméterként kapott ajtót eltávolítja a szoba ajtói közül,
	 * azaz a doors listából.
	 *
	 * @param d Az eltávolítandó ajtó.
	 */
	public void RemoveDoor(DoorSide d){
		Logger.started(this, "RemoveDoor", d);
		doors.remove(d);
		Logger.finished(this, "RemoveDoor", d);
	}

	/**
	 * Visszaadja a szobából kivezető ajtók szoba felé néző oldalait.
	 *
	 * @return A Room doors listája.
	 * */
	public ArrayList<DoorSide> GetDoors() {
		Logger.started(this, "GetDoors");
		Logger.finished(this, "GetDoors");
		return doors;
	}

	/**
	 * Hozzáadja a paraméterként kapott oktatót a szobához (amikor belép)
	 * és elájul, hogyha a szoba gázos, és nincsen aktív FFP2-es maszk nála.
	 *
	 * @param i A hozzáadandó Instructor.
	 * */
	public void AddInstructor(Instructor i) {
		Logger.started(this, "AddInstructor", i);
		i.SetRoom(this);
		currentCapacity++;
		numberOfPeopleBeenToRoom++;
		instructors.add(i);
		Logger.finished(this, "AddInstructor", i);
	}

	/**
	 * Eltávolítja a paraméterként kapott Instructort a szobából (átment egy másik szobába)
	 *
	 * @param i Az eltávolítandó Instructor.
	 * */
	public void RemoveInstructor(Instructor i) {
		Logger.started(this, "RemoveInstructor", i);
		instructors.remove(i);
		currentCapacity--;
		Logger.finished(this, "RemoveInstructor", i);
	}

	/**
	 * Hozzáadja a paraméterként kapott Studentet a szobához (amikor belép),
	 * és elájul, hogyha a szoba gázos, és nincsen aktív FFP2-es maszk nála.
	 *
	 * @param s A hozzáadandó Student.
	 * */
	public void AddStudent(Student s) {
		Logger.started(this, "AddStudent", s);
		s.SetRoom(this);
		currentCapacity++;
		numberOfPeopleBeenToRoom++;
		if(numberOfPeopleBeenToRoom >= 5)
			SetIsSticky(true);
		students.add(s);
		Logger.finished(this, "AddStudent", s);
	}

	/**
	 * Eltávolítja a paraméterként kapott Studentet a szobából (átment egy másik szobába)
	 *
	 * @param s Az eltávolítandó Student.
	 * */
	public void RemoveStudent(Student s) {
		Logger.started(this, "RemoveStudent", s);
		students.remove(s);
		currentCapacity--;
		Logger.finished(this, "RemoveStudent", s);
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
		Logger.started(this, "AddJanitor", j);
		currentCapacity++;
		janitors.add(j);
		Logger.finished(this, "AddJanitor", j);
	}

	/**
	 * Ezen metódus a paraméterként kapott Janitort eltávolítja a szobából, azaz
	 * kiveszi a Room janitors listájából.
	 *
	 * @param j Az eltávolítandó Janitor.
	 * */
	public void RemoveJanitor(Janitor j) {
		Logger.started(this, "RemoveJanitor", j);
		janitors.remove(j);
		currentCapacity--;
		Logger.finished(this, "RemoveJanitor", j);
	}

	/**
	 * Generál véletlenszerűen egy 'true' vagy 'false' bool értéket. A szobák osztódásánál kell ahhoz, hogy
	 * a tárgyak véletlenszerűen meg tudjanak feleződni a szobák között.
	 * Ha a játék determinisztikus akkor mindig igazat ad vissza.
	 *
	 * @return Egy random boolean érték.
	 * */
	public boolean RandomBool() {
		Logger.started(this, "RandomBool");
		Logger.finished(this, "RandomBool");

		if(isDeterministic){
			return true;
		}

		Random rand = new Random();
		return rand.nextBoolean();
	}

	/**
	 * Véletlenszerűen kiválaszt egy szobát a paraméterként megadott ArrayListából, majd visszaadja.
	 *
	 * @param r A lista, amelyből egy Room -ot kell kiválasztani véletlenszerűen
	 * @return A véletlenszerűen kiválasztott Room
	 * */
	public Room SelectRoom(ArrayList<Room> r) {
		Logger.started(this, "SelectRoom", r);
		Logger.finished(this, "SelectRoom", r);
		if(r.isEmpty()){
			throw  new IllegalArgumentException("Error: Üres lista lett megadva!");
		}
		// Ha a játék determinisztikus akkor mindig az első szobát adja vissza.
		if(isDeterministic){
			return r.get(0);
		}

		// Ha a játék nem determinisztikus akkor véletlenszerűen kiválaszt egy DoorSide -ot
		Random randInt = new Random();
		return r.get(randInt.nextInt(r.size()));
	}

	/**
	 * Véletlenszerűen kiválaszt egy szobához tartozó ajtót, majd ha láthatatlan volt, akkor láthatóvá teszi,
	 * és fordítva (csak elátkozott szobákban fut le, minden kör végén egyszer)
	 * */
	public void ToggleDoorsVisible() {
		Logger.started(this, "ToggleDoorsVisible");
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
		Logger.finished(this, "ToggleDoorsVisible");
	}

	/**
	 * Beállítja, hogy a szoba elátkozott legyen, azaz az ajtajai véletlenszerűen megjelenjenek/eltűnjenek.
	 *
	 * @param isc Az isCursed változó leendő értéke.
	 * */
	public void SetIsCursed(boolean isc) {
		Logger.started(this, "SetIsCursed", isc);
		isCursed = isc;
		Logger.finished(this, "SetIsCursed", isc);
	}

	/**
	 * Ezen metódus visszaadja a Room id változójának értékét.
	 *
	 * @return A Room id változója.
	 */
	@Override
	public String GetId() {
		return id;
	}

	/**
	 * Ezen metódus visszaadja, hogy a szoba elátkozott-e.
	 *
	 * @return Az isCursed változó értéke.
	 * */
	public boolean GetIsCursed() {
		Logger.started(this, "GetIsCursed");
		Logger.finished(this, "GetIsCursed");
		return isCursed;
	}
}
