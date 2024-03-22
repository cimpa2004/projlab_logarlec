package modul;

import util.Reader;

/**
 * Student class reprezentálja a játékban a hallgatókat. A játékot játszó felhasználók ezeket az entitásokat
 * irányítják a játék során.
*/
public class Student extends Person {
	/** 
	 * Ez a változó jelzi, hogy az adott Student az még él e.
	*/
	private boolean isAlive;
	
	/** 
	 * A játékra való referencia. Amikor a Student meghal akkor ennek segítségével tudja értesíteni
	 * a játékot, hogy meghalt és többé nem következhet.
	*/
	private Game game;
	
	/** 
	 * Ez a függvény által megjelenik a Student a paraméterként megadott szobában.
	 * 
	 *  @param  r  Az a szoba ahol megjelenik a Student
	*/
	public void AppearInRoom(Room r) {
		System.out.println("STARTED: " + this + ".AppearInRoom(" + r + ")");
		room = r;
		System.out.println("FINISHED: " + this + ".AppearInRoom(" + r + ")");
	}
	
	/** 
	 * Ezt a függvény hívja meg a Game a Studenten amikor jelzi neki, hogy a köre megkeződik. A játékos
	 * ezen belül adhatja meg a lépéseit. Ekkor az activeTurn true értékre változik.
	*/
	public void StartTurn() {
		System.out.println("STARTED: " + this + ".StartTurn()");

		System.out.println("FINISHED: " + this + ".StartTurn()");
	}
	
	/** 
	 * A Student ezzel a függvénnyel jelzi, hogy a köre véget ért. Ekkor a activeTurn értékre false-ra vált.
	*/
	public void EndTurn() {
		System.out.println("STARTED: " + this + ".EndTurn()");

		System.out.println("FINISHED: " + this + ".EndTurn()");
	}
	
	/** 
	 * Ennek a függvény hatására a hallgató meghalhat. Innentől kezdve az isAlive változója false lesz amennyiben meghal. 
	 * Ekkor, a Game már többet nem fogja meghivni rajta a StartTurn függvényt. Amennyiben a Student rendelkezik olyan 
	 * Defendable-el ami meg tudja védeni akkor a nem hal meg, és false értékkel tér vissza.
	 * 
	 *  @return    Egy boolean érték, amely azt jelzi, hogy a hallgató meghalt, vagy sikerült valahogy túlélni
	*/
	public boolean Die() {
		System.out.println("STARTED: " + this + ".Die()");

		System.out.println("FINISHED: " + this + ".Die()");
		return false;
	}
	
	/** 
	 * A Student a paraméterként megadott Usable-t használja. A Student mindenképp meghívja ekkor ezen
	 * a Usable-n a UsedByStudent(this) függvényét, magát átadva paraméterként. Ennek hatására az adott 
	 * Usable-t aktiválja amennyiben az még nem volt aktiválva, és képes lesz használni az adott Usable
	 * képességeit.
	 * 
	 *  @param  u  A Usable amit a Student használni szeretne
	*/
	public void UseItem(Usable u) {
		System.out.println("STARTED: " + this + ".UseItem(" + u + ")");
		// Any Usable must be an Item as well
		Item item = (Item)u;
		if(inventory.contains(item)) u.UsedByStudent(this);
		System.out.println("FINISHED: " + this + ".UseItem(" + u + ")");
	}
	
	/** 
	 * A Student a két paraméterként megadott Transistor-t összekapcsolja. Ez után már képes lesz használni a
	 * két Transistor-t arra, hogy szobák között teleportáljon.
	 * 
	 *  @param t1  Az egyik transzisztor amit csatlakoztatni akar a másikhoz
	 *  @param t2  A másik tranzisztor amit a t1-hez szeretne csatlakoztatni
	*/
	public void ConnectTransistors(Transistor t1, Transistor t2) {
		System.out.println("STARTED: " + this + ".ConnectTransistors(" + t1 + ", " + t2 + ")");

		System.out.println("FINISHED: " + this + ".ConnectTransistors(" + t1 + ", " + t2 + ")");
	}
	
	/** 
	 * A Student ennek a függvény hatására felveszi az adott Itemet. Ennek hatására a Student mindenképp meghívja
	 * az adott i Itemen a PickedUpStudent(this) függvényét, magát átadva paraméterként. Előfordulhat, hogy nem képes
	 * felvenni a tárgyat a Student, ha nincs hely az intentoryjában, ekkor false-al tér vissza, egyébként true-val.
	 *
	 * @param  i  Az az Item, amelyet a Student fel szeretne venni.
	 * @return    Visszatérési érték egy boolean, ami jelzi, hogy sikerült-e felvenni az i Itemet vagy sem
	*/
	public boolean Pickup(Item i) {
		System.out.println("STARTED: " + this + ".Pickup(" + i + ")");
		boolean isPickedUp = i.PickedUpStudent(this);
		System.out.println("FINISHED: " + this + ".Pickup(" + i + ")");
		return isPickedUp;
	}


	/** 
	 * Ennek segítségével tud a Student a szobák között mozogni. A kiválaszott ajtóba megpróbál belépni. Lehetséges, hogy 
	 * az adott ajtón nem lehet belépni valamiért, de amennyiben tud, akkor a Student megjelenhet az ajtó által definiált 
	 * másik szobában.
	 * 
	 *  @param  d  Egy ajtó, amelyen a Person megpróbál átlépni egy másik szobába
	*/
	@Override
	public void Move(DoorSide d) {
		System.out.println("STARTED: " + this + ".Move(" + d + ")");
		boolean canBeOpened = Reader.GetBooleanInput("Az ajtot ki lehet nyitni?");
		boolean isVisible = Reader.GetBooleanInput("Az ajto lathato?");
		if(!canBeOpened || !isVisible){
			System.out.println("FINISHED: " + this + ".Move(" + d + ")");
			return;
		}
		DoorSide d2 = d.GetPair();
		Room r2 = d2.GetRoom();
		int maxCapacity = r2.GetMaxCapacity();
		int currCapacity = r2.GetCurrentCapacity();
		if(!(currCapacity<maxCapacity)){
			System.out.println("FINISHED: " + this + ".Move(" + d + ")");
			return;
		}
		room.RemoveStudent(this);
		AppearInRoom(r2);
		System.out.println("FINISHED: " + this + ".Move(" + d + ")");
	}


}
