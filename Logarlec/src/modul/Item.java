package modul;


import util.Logger;

import java.util.UUID;

/**
 * Az Item absztrakt osztály a játékban előforduló tárgyakat reprezentálja általánosan.
 * Az Item osztály összefoglalja azokat az attribútumokta és metódusokat,
 * amelyekkel az összes tárgy rendelkezik.
 * */
public abstract class Item {
	/**
	 * Az adott Itemet egyertelmuen azonositja
	 */
	protected String id;

	/**
	 * Ez a változó tárolja el, hogy az adott Item melyik szobában van.
	 * */
	private Room room;

	public Item(String id){
		this.id = id;
	}

	public Item(){
		this.id = UUID.randomUUID().toString();
	}
	
	/**
	 * Ez a változó tartalmazza, hogy az adott Item, melyik Person -höz tartozik.
	 * Amennyiben nem tartozik senkihez, akkor üresen marad a változó.
	 * */
	private Person owner;

	public abstract boolean GetIsFake();

	/**
	 *Vissza adja, hogy aktivalva volt-e mar
	 * @return igaz/hamis ertek ami jelzi hogy aktivalva van e, a nem aktivalhato Itemek mindig false-al ternek vissza
	 */
	public abstract boolean GetIsActive();

	/**
	 * Megadja a párját
	 * @return Pair értéke, null ha nincs párja
	 */
	public abstract Transistor GetPair();

	/**
	 * Vissza adja a tárgyhoz tartozó egyedi id-t.
	 * @return A tárgyhoz tartozó egyedi id.
	 */
	public String GetId(){
		return id;
	}

	public void SetOwner(Person p){
		Logger.started(this, "SetOwner");

		owner = p;

		Logger.finished(this, "SetOwner");
	}

	public Person GetOwner(){
		Logger.started(this, "GetOwner");

		Logger.finished(this, "GetOwner");
		return owner;
	}

	/**
	 * Ez a metódus az Item room változójának megadja azt a szobát, amelyeket az r paraméterében kap.
	 * Ez a metódus tipikusan, akkor hívódik meg, ha egy Person eldob egy Item -et.
	 * Vagy akkor, amikor a játék kezdetekor elhelyezzük a tárgyakat a szobákban.
	 *
	 * @param	r	Azon szoba, amelyre át szeretnénk állítani az adott Item room változóját.
	 * */
	public void SetRoom(Room r) {
		Logger.started(this, "SetRoom");

		room = r;

		Logger.finished(this, "SetRoom");
	}
	
	/**
	 * Ez a metódus visszatérési értékként megadja azt a szobát, amelyben az Item található,
	 * vagy null értéket, amennyiben egy Person már felvette.
	 * */
	public Room GetRoom() {
		Logger.started(this, "GetRoom");

		Logger.finished(this, "GetRoom");
		return room;
	}
	
	/**
	 * Ez egy absztrakt függvény, amelyet a leszármazott osztályok majd külön-külön implementálnak.
	 * Meghívásakor a paraméterként megkapott Student inventárjában elhelyezi magát a tárgy
	 * és átállítja az owner változóját a paraméterként kapott Student -re.
	 * Igaz értékkel fog visszatérni, ha sikeresen fel tudta venni az adott tárgyat a Student és
	 * hamissal, ha nem.
	 * Abben az esetben nem sikerülhet felvenni a tárgyat, ha a Student inventárja már tele van.
	 * Specális esete amikor az Item, aminek ezt a függvényét meghívták egy SlideRule.
	 * Ez esetben véget ér a játék, a hallgatók nyertek.
	 *
	 * @param	st	Azon Student, aki megpróbálja felvenni az adott Item -et.
	 * */
	public abstract boolean PickedUpStudent(Student st);
	
	/**
	 * Ez egy absztrakt függvény, amelyet a leszármazott osztályok majd külön-külön implementálnak.
	 * Meghívásakor a paraméterként megkapott Instructor inventárjában elhelyezi magát a tárgy
	 * és átállítja az owner változóját a paraméterként kapott Instructor -ra.
	 * Igaz értékkel fog visszatérni, ha sikeresen fel tudta venni az adott tárgyat az Instructor és
	 * hamissal, ha nem.
	 * Abben az esetben nem sikerülhet felvenni a tárgyat, ha a Instructor inventárja már tele van,
	 * vagy az Item amit megpróbál felvenni egy SlideRule.
	 *
	 * @param	i	Azon Instructor, aki megpróbálja felvenni az adott Item -et.
	 * */
	public abstract boolean PickedUpInstructor(Instructor i);
	
	/**
	 * Ez egy absztrakt függvény, amelyet a leszármazott osztályok majd külön-külön implementálnak.
	 * Ezen metódus a Person Throw függvényéből hívódik meg.
	 * Kezeli az eldobást a tárgy szemszögéből, az Itemtől függően törli a Person
	 * védelmi Itemjei közül is a megadott Itemet.
	 *
	 * @param p	Azon Person, aki eldobta az adott tárgyat.
	 * */
	public abstract void Thrown(Person p);


	/**
	 *Egy hallgató használta a tárgyat
	 * @param s implementáció a megvalósításban
	 */
	public abstract void UsedByStudent(Student s);

	/**
	 * Egy oktató használta a tárgyat
	 * @param i implementáció a megvalósításban
	 */
	public abstract void UsedByInstructor(Instructor i);
}
