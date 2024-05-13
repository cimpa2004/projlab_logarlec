package model;


import util.Logger;
import view.VItem;
import viewmodel.IVItem;
import viewmodel.IVItemUpdate;

import java.util.UUID;

/**
 * Az Item absztrakt osztály a játékban előforduló tárgyakat reprezentálja általánosan.
 * Az Item osztály összefoglalja azokat az attribútumokta és metódusokat,
 * amelyekkel az összes tárgy rendelkezik.
 * */
public abstract class Item implements IVItem {
	/**
	 * Ez egy string típusú változó, amely egyértelműen azonosít egy Item -et.
	 */
	protected String id;


	/**
	 * Ez a Room típusú változó eltárolja azt a Room -ot, amelyben az adott tárgy van.
	 * Amennyiben már egy Person felvette a tárgyat, null értéket vesz fel a változó.
	 * */
	private Room room;

	/**
	 * Ez a Person típusú változó eltárolja azt a Person -t, akihez az adott Item tartozik.
	 * Amennyiben senkihez sem tartozik a tárgy, null értéket vesz fel a változó.
	 * */
	private Person owner;

	/**
	 * Az Item osztály konstruktora.
	 * Beállítja a paraméterként megadott id-t.
	 *
	 * @param id Az id értéke erre az értékre lesz beállítva.
	 * */
	public Item(String id){
		this.id = id;
	}

	/**
	 * Az Item osztály konstruktora.
	 * Az id-t beállítja egy random értékre.
	 * */
	public Item(){
		this.id = UUID.randomUUID().toString();
	}

	/**
	 * Visszaadja, hogy az adott Item hamis-e.
	 * Van olyan item amire ez mindig hamis,
	 * van olyan amire igaz is lehet.
	 *
	 * @return Igaz ha hamis az Item és hamis ha igazi az Item.
	 * */
	public abstract boolean GetIsFake();

	/**
	 * Ezen metódussal le lehet kérdezni, hogy egy Item aktív-e.
	 *
	 * @return igaz/hamis ertek ami jelzi hogy aktivalva van e, a nem aktivalhato Itemek mindig false-al ternek vissza
	 */
	public abstract boolean GetIsActive();

	/**
	 * Ezen metódussal le lehet kérdezni, hogy az
	 * adott Transistor  -nak melyik Transistor  a párja.
	 *
	 * @return Pair értéke, null ha nincs párja
	 */
	public abstract Transistor GetPair();

	/**
	 * Vissza adja a tárgyhoz tartozó egyedi id-t.
	 *
	 * @return A tárgyhoz tartozó egyedi id.
	 */
	public String GetId(){
		return id;
	}

	/**
	 * Az Item owner változójának értékét a
	 * paraméterként kapott Person -re állítja.
	 *
	 * @param p Azon Person, amelyre az adott
	 * Item owner változóját beállítja a metódus.
	 */
	public void SetOwner(Person p){
		Logger.started(this, "SetOwner");

		owner = p;

		Logger.finished(this, "SetOwner");
	}

	/**
	 * Visszaadja, hogy az Item melyik Person -hoz tartozik
	 *
	 * @return Az owner változó, amely felvehet
	 * null értéket is ha senkihez sem tartozik a tárgy.
	 */
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
	 *
	 * @return A szoba, ahol a tárgy éppen van.
	 * Ha egy személynél van akkor null értéket ad vissza.
	 * */
	public Room GetRoom() {
		Logger.started(this, "GetRoom");

		Logger.finished(this, "GetRoom");
		return room;
	}
	
	/**
	 * Ez egy absztrakt függvény, amelyet a leszármazott osztályok majd külön-külön implementálnak.
	 * Ezen metódus referenciaként átveszi hogy melyik Student vette fel és
	 * megpróbálja elhelyezi magát a Student inventoryában.
	 * Meghívja a Student AddToInventory metódusát, aminek átadja az Item -et.
	 * Specális esete amikor az Item, aminek ezt a függvényét meghívták egy SlideRule.
	 * Ez esetben véget ér a játék, a hallgatók nyertek.
	 *
	 * @param	st	Azon Student, aki megpróbálja felvenni az adott Item -et.
	 * @return Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie az Item -et a Student -nek.
	 * A metódus visszatérési igaz, ha sikerült felvenni és hamis ha nem.
	 * */
	public abstract boolean PickedUpStudent(Student st);

	/**
	 * Ez egy absztrakt függvény, amelyet a leszármazott osztályok majd külön-külön implementálnak.
	 * Ezen metódus referenciaként átveszi hogy melyik Instructor vette fel és
	 * megpróbálja elhelyezi magát az Instructor inventoryában.
	 * Meghívja az Instructor AddToInventory metódusát, aminek átadja az Item -et.
	 *
	 * @param	i	Azon Instructor, aki megpróbálja felvenni az adott Item -et.
	 * @return Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie az Item -et az Instructor -nak.
	 * A metódus visszatérési igaz, ha sikerült felvenni és hamis ha nem.
	 * */
	public abstract boolean PickedUpInstructor(Instructor i);
	
	/**
	 * Ezen metódus akkor hívódik meg, ha a Person el szeretné távolítani
	 * az inventoryából az adott Item -et.
	 * A metódus eltávolítja az Item -et a Person inventoryából
	 * a Person RemoveFromInventory metódus meghívásával.
	 *
	 * @param p	Azon Person, aki eldobta az adott tárgyat.
	 * */
	public abstract void Thrown(Person p);


	/**
	 * Ezen metódus akkor hívódik meg, ha egy Student szeretne
	 * használni egy az inventoryában lévő Item -et.
	 *
	 * @param s Azon Student, aki használná az adott tárgyat.
	 */
	public abstract void UsedByStudent(Student s);

	/**
	 * Ezen metódus akkor hívódik meg, ha egy Instructor szeretne
	 * használni egy az inventoryában lévő Item -et.
	 * Ez egy absztrakt metódus, amit az egyes tárgyak külön-külön valósítanak meg.
	 *
	 * @param i Azon Instructor, aki használná az adott tárgyat.
	 */
	public abstract void UsedByInstructor(Instructor i);
}
