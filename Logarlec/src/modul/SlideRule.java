package modul;
import controller.Game;
import util.*;

import java.util.UUID;

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

	public SlideRule(String id) {
		super(id);
	}

	public SlideRule() {
		super(UUID.randomUUID().toString());
	}

	@Override
	public boolean GetIsFake() {
		return isFake;
	}

	public void SetIsFake(boolean b){
		isFake = b;
	}

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

		boolean isAdded = st.AddToInventory(this);

		if(isAdded && !isFake) game.EndGame(true);
		
		Logger.finished(this, "PickedUpStudent", st);
		return isAdded;
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
		p.RemoveFromInventory(this);
		Logger.finished(this, "Thrown", p);
	}
}
