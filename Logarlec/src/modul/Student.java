package modul;

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
	}
	
	/** 
	 * Ezt a függvény hívja meg a Game a Studenten amikor jelzi neki, hogy a köre megkeződik. A játékos
	 * ezen belül adhatja meg a lépéseit. Ekkor az activeTurn true értékre változik.
	*/
	public void StartTurn() {
	}
	
	/** 
	 * A Student ezzel a függvénnyel jelzi, hogy a köre véget ért. Ekkor a activeTurn értékre false-ra vált.
	*/
	public void EndTurn() {
	}
	
	/** 
	 * Ennek a függvény hatására a hallgató meghalhat. Innentől kezdve az isAlive változója false lesz amennyiben meghal. 
	 * Ekkor, a Game már többet nem fogja meghivni rajta a StartTurn függvényt. Amennyiben a Student rendelkezik olyan 
	 * Defendable-el ami meg tudja védeni akkor a nem hal meg, és false értékkel tér vissza.
	 * 
	 *  @return    Egy boolean érték, amely azt jelzi, hogy a hallgató meghalt, vagy sikerült valahogy túlélni
	*/
	public boolean Die() {
		return false;
	}
	
	/** 
	 * A Student a paraméterként megadott Usable-t használja. A Student mindenképp meghívja ekkor ezen
	 * a Usable-n a UsedByStudent(this) függvényét, magát átadva paraméterként. Ennek hatására az adott 
	 * Usable-t aktiválja amennyiben az még nem volt aktiválva, és képes lesz használni az adott Usable
	 * képességeit.
	*/
	public void UseItem(Usable u) {
	}
	
	/** 
	 * A Student a két paraméterként megadott Transistor-t összekapcsolja. Ez után már képes lesz használni a
	 * két Transistor-t arra, hogy szobák között teleportáljon.
	*/
	public void ConnectTransistors(Transistor t1, Transistor t2) {
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
		return false;
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
	}


}
