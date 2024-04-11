package modul;

import util.Logger;
import util.Reader;

import java.util.ArrayList;
import java.util.List;

/**
 * Student class reprezentálja a játékban a hallgatókat. A játékot játszó felhasználók ezeket az entitásokat
 * irányítják a játék során.
*/
public class Student extends Person {

	public Student(Game g){
		this.game = g;
	}
	public Student(){}

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
		Logger.started(this, "AppearInRoom", r);
		int currentC = r.GetCurrentCapacity();
		int maxC = r.GetMaxCapacity();
		if(currentC < maxC) {
			room.SetCurrentCapacity(room.GetCurrentCapacity()-1); // kilepes a jelenlegi szobabol
			room = r; // atlepes a masik szobaba
			room.SetCurrentCapacity(room.GetCurrentCapacity()+1); // belepes a masik szobaba
			room.AddStudent(this);

			ArrayList<Instructor> instructors = room.GetInstructors();
			// belep a szobaba es minden oktato megprobalja elvenni a lelket, de vedekezhet
			for(Instructor instructor : instructors){
				instructor.StealSoul(this);
				if(!isAlive) break;
			}
		}
		Logger.finished(this, "AppearInRoom", r);
	}
	
	/** 
	 * Ezt a függvény hívja meg a Game a Studenten amikor jelzi neki, hogy a köre megkeződik. A játékos
	 * ezen belül adhatja meg a lépéseit. Ekkor az activeTurn true értékre változik.
	*/
	public void StartTurn() {
		Logger.started(this, "StartTurn");
		activeTurn = true;
		// ha kor kezdetekor gazos szobaban van akkor elkabul
		if(room.GetPoisonDuration() > 0){
			if(!ffp2Masks.isEmpty()){
				Defendable ffp2Mask = GetRandomActive(ffp2Masks);
				if(ffp2Mask != null){
					ffp2Mask.Decrement();
					// ha mar a vedes utan tobbet nem tud vedeni, akkor kiszedjuk a listabol
					if(!ffp2Mask.CanDefend()) RemoveFFP2Mask(ffp2Mask);
				}else{
					SetIsFainted(true);
				}
			}// ha nincs gazos szobaban kor elejen akkor vissza nyeri eszmeletet
		}else{
			SetIsFainted(false);
		}
		// ha el van kabulva akkor egybol veget er a kore, semmit nem tud csinalni
		if(isFainted) EndTurn();

		// itt kezdheti meg a hallgato a lepeseit


		Logger.finished(this, "StartTurn");
	}


	/** 
	 * A Student ezzel a függvénnyel jelzi, hogy a köre véget ért. Ekkor a activeTurn értékre false-ra vált.
	*/
	public void EndTurn() {
		Logger.started(this, "EndTurn");
		ArrayList<Defendable> holyBeerCupsCopy = new ArrayList<>(this.holyBeerCups);
		for (Defendable h : holyBeerCupsCopy) {
			h.Decrement();
			// ha Decrement utan mar nem tudna vedeni akkor lejart a holyBeerCup, kivesszuk a listabol
			if(!h.CanDefend()) this.holyBeerCups.remove(h);
		}
		activeTurn = false;
		Logger.finished(this, "EndTurn");
	}
	
	/** 
	 * Ennek a függvény hatására a hallgató meghalhat. Innentől kezdve az isAlive változója false lesz amennyiben meghal. 
	 * Ekkor, a Game már többet nem fogja meghivni rajta a StartTurn függvényt. Amennyiben a Student rendelkezik olyan 
	 * Defendable-el ami meg tudja védeni akkor a nem hal meg, és false értékkel tér vissza.
	 * 
	 *  @return    Egy boolean érték, amely azt jelzi, hogy a hallgató meghalt, vagy sikerült valahogy túlélni
	*/
	public boolean Die() {
		Logger.started(this, "Die");
		isAlive = false;
		game.RemoveFromGame(this);
		EndTurn();
		Logger.finished(this, "Die");
		return isAlive;
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
		Logger.started(this, "UseItem", u);
		// Any Usable must be an Item as well
		Item item = (Item)u;
		if(inventory.contains(item)) u.UsedByStudent(this);
		Logger.finished(this, "UseItem", u);
	}
	
	/** 
	 * A Student a két paraméterként megadott Transistor-t összekapcsolja. Ez után már képes lesz használni a
	 * két Transistor-t arra, hogy szobák között teleportáljon.
	 * 
	 *  @param t1  Az egyik transzisztor amit csatlakoztatni akar a másikhoz
	 *  @param t2  A másik tranzisztor amit a t1-hez szeretne csatlakoztatni
	*/
	public void ConnectTransistors(Transistor t1, Transistor t2) {
		Logger.started(this, "ConnectTransistors", t1, t2);
		Logger.finished(this, "ConnectTransistors", t1, t2);
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
		Logger.started(this, "Pickup", i);
		boolean isPickedUp = i.PickedUpStudent(this);
		Logger.finished(this, "Pickup", i);
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
		Logger.started(this, "Move", d);
		boolean canBeOpened = Reader.GetBooleanInput("Az ajtot ki lehet nyitni?");
		boolean isVisible = Reader.GetBooleanInput("Az ajto lathato?");
		if (!canBeOpened || !isVisible) {
			Logger.finished(this, "Move", d);
			return;
		}
		DoorSide d2 = d.GetPair();
		Room r2 = d2.GetRoom();
		room.RemoveStudent(this);
		AppearInRoom(r2);
		Logger.finished(this, "Move", d);
	}


}
