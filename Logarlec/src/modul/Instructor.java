package modul;

import util.Logger;
import util.Reader;

/**
 * Az Instructor class valósítja meg a játékban az oktatókat.
 * Ezek az entitások a program által vannak vezérelve.
 */
public class Instructor extends Person {
	/**
	 * Ez a változó tárolja az oktató bénulásának idejét.
	 * */
	private int stunDuration;

	/**
	 * A függvény által megjelenik az Oktató a paraméterként megadott szobában.
	 *
	 *  @param  r  Az a szoba ahol megjelenik az Oktató
	 */
	public void AppearInRoom(Room r) {
		Logger.started(this, "AppearInRoom", r);
		r.AddInstructor(this);
		Logger.finished(this, "AppearInRoom", r);
	}

	/**
	 * Az Oktató megöli a Hallgatót, akit paraméterben kap.
	 *
	 *  @param  st  Az a Hallgató akit az Oktató megöl
	 */
	public void StealSoul(Student st) {
		Logger.started(this, "StealSoul", st);
		// existing code
		boolean protected_ = Reader.GetBooleanInput("Van a hallgatót megvédő tárgy? ");
		if (protected_)
			st.DefendFromKill();
		st.Die();
		Logger.finished(this, "StealSoul", st);
	}

	/**
	 * Az Oktató ezen a függvényen keresztül szenvedi el a bénulást.
	 *
	 * @param  duration  A bénulás időtartama.
	 * */
	public void Stun(int duration) {
		Logger.started(this, "Stun", duration);
		// existing code
		Logger.finished(this, "Stun", duration);
	}

	/**
	 * Csökkenti az Oktatón található bénulás időtartamát.
	 * */
	public void DecrementStun() {
		Logger.started(this, "DecrementStun");
		// existing code
		Logger.finished(this, "DecrementStun");
	}

	/**
	 * Ezt a függvényt hívja meg a Game az Oktatón, amikor jelzi neki, hogy a köre megkeződik. Az activeTurn true értékre változik.
	 */
	public void StartTurn() {
		Logger.started(this, "StartTurn");
		// existing code
		Logger.finished(this, "StartTurn");
	}

	/**
	 * Az Oktató ezzel a függvénnyel jelzi, hogy a köre véget ért. Ekkor az activeTurn false értéket vesz fel.
	 */
	public void EndTurn() {

		Logger.started(this, "EndTurn");
		// existing code
		Logger.finished(this, "EndTurn");
	}

	/**
	 * Az Oktató a paraméterben kapott Usable tárgyat használja. Ekkor meghívja a UsedByInstructor függvényt
	 * az adott tárgyon. Ennek hatására az adott Usable aktiválódik (feltéve, hogy az FFP2 maszk vagy Camembert), ha még nem volt aktiválva és
	 * képes lesz használni az adott Usable képességeit.
	 *
	 *  @param  u  Usable amit az Oktató használni fog
	 */
	public void UseItem(Usable u) {
		Logger.started(this, "UseItem", u);
		// Any Usable must be an Item as well
		Item item = (Item)u;
		if(inventory.contains(item)) u.UsedByInstructor(this);
		Logger.finished(this, "UseItem", u);
	}

	/**
	 * Az Oktató ezzel a függvénnyel veszi fel a tárgyakat. Az Oktató a felvett tárgyon ekkor
	 * meghívja a PickedUpInsturctor függvényt, paraméterben átadva saját példányát.
	 *
	 * @param i Az a tárgy amit felvesz az Oktató
	 * @return Visszatérési érték, ami a sikeres felvételt / sikertelent jelzi vissza.
	 * */
	@Override
	public boolean Pickup(Item i) {
		Logger.started(this, "Pickup", i);
		boolean isPickedUp = i.PickedUpInstructor(this);
		Logger.finished(this, "Pickup", i);
		return isPickedUp;
	}

	/**
	 * Ezzel a függvénnyel tud az Oktató a szobák között mozogni. Ha az ajtó kinyitható
	 * és látható is, akkor az oktató átlép a másik szobába, amit az ajtó rejt.
	 *
	 *  @param  d  Az az ajtó, amin az Oktató átlép a másik szobába
	 */
	@Override
	public void Move(DoorSide d) {
		Logger.started(this, "Move", d);
		boolean canBeOpened = Reader.GetBooleanInput("Az ajtot ki lehet nyitni?");
		boolean isVisible = Reader.GetBooleanInput("Az ajto lathato?");
		if(!canBeOpened || !isVisible){
			Logger.finished(this, "Move", d);
			return;
		}
		DoorSide d2 = d.GetPair();
		Room r2 = d2.GetRoom();
		int maxCapacity = r2.GetMaxCapacity();
		int currCapacity = r2.GetCurrentCapacity();
		if(!(currCapacity<maxCapacity)){
			Logger.finished(this, "Move", d);
			return;
		}
		room.RemoveInstructor(this);
		AppearInRoom(r2);
		Logger.finished(this, "Move", d);
	}

}