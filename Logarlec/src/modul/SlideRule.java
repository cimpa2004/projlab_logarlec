package modul;
import controller.Game;
import util.*;
/**
 * A SlideRule egy olyan osztály ami a Tárgy osztályból van leszármaztatva.
 * Felvételével a játékos megnyeri a játékot.
 */
public class SlideRule extends Item {
	/**
	 * A változón keresztül éri el a logarléc a játékot.
	 * */
	private Game game;

	/**
	 * Ezen változó tárolja, hogy az adott SlideRule igazi-e.
	 * */
	private boolean isFake;
	
	/**
	 * A függvény a paraméterben kapott Hallgató inventoryába rakja a logarlécet,
	 * meghívja az EndGame függvényt. Ha sikerült felvenni igaz értékkel tér vissza,
	 * ha nem akkor hamis értékkel.
	 *
	 * @param st Az a Hallgató, aki felvette a logarlécet
	 * @return olyan boolean érték, ami a felvétel sikerességét tükrözi
	 * */
	public boolean PickedUpStudent(Student st) {
		Logger.started(this, "PickedUpStudent", st);
		// TODO ha isFake akkor nem nyeri meg a játékot
		Logger.finished(this, "PickedUpStudent", st);
		return false;
	}

	/**
	 * A logarlécet a paraméterben kapott Oktató venné fel, de mivel
	 * ez nem megengedett, így mindig hamis értékkel visszatér.
	 *
	 * @param i Az az Oktató, aki felvenné a logarlécet
	 * @return mindig hamis érték
	 * */
	public boolean PickedUpInstructor(Instructor i) {
		Logger.started(this, "PickedUpInstructor", i);
		Logger.finished(this, "PickedUpInstructor", i);
		return false;
	}
	
	/**
	 * A paraméterben kapott Személy eldobja a tárgyat.
	 *
	 * @param p Az a személy aki meghívta a függvényt.
	 * */
	public void Thrown(Person p) {
		Logger.started(this, "Thrown", p);
		Logger.finished(this, "Thrown", p);
	}
	
	/**
	 * A paraméterben kapott Személy használja tárgyat
	 *
	 * @param p Az a személy aki meghívta a függvényt.
	 * */
	public void UsedItem(Person p) {
		Logger.started(this, "Thrown", p);
		Logger.finished(this, "Thrown", p);
	}

}
