package model;
import controller.Game;
import util.Logger;

import java.util.*;

/**
 * Az Instructor class valósítja meg a játékban az oktatókat.
 * Ezek az entitások a program által vannak vezérelve.
 */
public class Instructor extends Person {
	/**
	 * Ez a változó tárolja az oktató bénulásának idejét.
	 * */
	private int stunDuration;

	public Instructor(String id, Game g) {
		super(id);
		this.game = g;
	}

	public Instructor(Game g) {
		super(UUID.randomUUID().toString());
		this.game = g;
	}


	/**
	 * A függvény által megjelenik az Oktató a paraméterként megadott szobában.
	 *
	 *  @param  r  Az a szoba ahol megjelenik az Oktató
	 */
	public boolean AppearInRoom(Room r) {
		Logger.started(this, "AppearInRoom", r);
		int currentC = r.GetCurrentCapacity();
		int maxC = r.GetMaxCapacity();
		if(currentC < maxC) {
			Room oldRoom = room;
			room = r; // atlepes a masik szobaba
			oldRoom.RemoveInstructor(this);
			room.AddInstructor(this);

			//megvizsgalja, hogy gazos-e a szoba
			if (room.GetPoisonDuration() > 0){
				//ha van nala ffp2 maszk, akkor megprobal aktivalni egyet
				if(!ffp2Masks.isEmpty()) {
					if(this.DefendFromGas()) this.SetIsFainted(false);
					else this.SetIsFainted(true);
				} else this.SetIsFainted(true);
			}
			
			// Megprobalja megolni a hallgatokat
			ArrayList<Student> studentCopy = new ArrayList<>(room.GetStudents());
			for(Student student : studentCopy) {
				StealSoul(student);
			}
			//tárgyfelvétel
			/*if (this.inventory.size() <5){
				if(!this.GetRoom().GetIsSticky())
					Pickup(this.GetRoom().GetItems().get(GetRoom().GetItems().size() - 1));
			}*///Nem szükséges a teszteléshez
			//mask aktiválása, minimális
			for (Defendable m: this.GetFFP2Masks())
				((FFP2Mask) m).Activate();
		} else {
			return false;
		}
		Logger.finished(this, "AppearInRoom", r);
		return true;
	}

	/**
	 * Az Oktató megöli a Hallgatót, akit paraméterben kap.
	 *
	 *  @param  st  Az a Hallgató akit az Oktató megöl
	 */
	public void StealSoul(Student st) {
		Logger.started(this, "StealSoul", st);
		if(!isFainted && !(stunDuration > 0)){
			if (!st.DefendFromKill(this)){
				st.Die();
			}
		}
		Logger.finished(this, "StealSoul", st);
	}

	/**
	 * Az Oktató ezen a függvényen keresztül szenvedi el a bénulást.
	 *
	 * @param  duration  A bénulás időtartama.
	 * */
	public void Stun(int duration) {
		Logger.started(this, "Stun", duration);
		stunDuration = duration;
		Logger.finished(this, "Stun", duration);
	}

	/**
	 * Csökkenti az Oktatón található bénulás időtartamát.
	 * */
	public void DecrementStun() {
		Logger.started(this, "DecrementStun");
		if(stunDuration > 0){
			stunDuration = stunDuration - 1;
		}
		Logger.finished(this, "DecrementStun");
	}

	/**
	 * Ezt a függvényt hívja meg a Game az Oktatón, amikor jelzi neki, hogy a köre megkeződik. Az activeTurn true értékre változik.
	 */
	@Override
	public void StartTurn() {
		if (isFainted || stunDuration > 0){
			this.EndTurn();
			return;
		}

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
			}
		}
		// ha nincs gazos szobaban kor elejen akkor vissza nyeri eszmeletet
		else{
			SetIsFainted(false);
		}
		// ha az oktato meg van benulva vagy el van kabulva akkor egybol veget er a kore, semmit nem tud csinalni
		if(stunDuration > 0 || isFainted){
			EndTurn();
			return;
		}

		// oktato minden kore elejen megprobalja elvenni minden hallgato lelket a jelenlegi szobajaban
		ArrayList<Student> studentCopy = new ArrayList<>(room.GetStudents());
		for(Student student : studentCopy){
			StealSoul(student);
		}

		//Mozgató logika
		Random random = new Random();
		if (!game.GetIsDeterministic()){
			List<DoorSide> doorsCopy = new ArrayList<>(this.GetRoom().GetDoors());
			Collections.shuffle(doorsCopy);

			for (DoorSide dr : doorsCopy){
				if (random.nextBoolean())
					if (dr.IsDoorUseable()){
							this.Move(dr);//a keresett ajtón átmegy
							break;
						}
			}
		}
//		else{ //determinisztikus mozgás az első lehetséges szomszéd
//			for (DoorSide dr : this.GetRoom().GetDoors()){
//				if (dr.IsDoorUseable()){
//					this.Move(dr);//a keresett ajtón átmegy
//					break;
//					}
//			}
//		}

		EndTurn();
		Logger.finished(this, "StartTurn");
	}

	/**
	 * Az Oktató ezzel a függvénnyel jelzi, hogy a köre véget ért. Ekkor az activeTurn false értéket vesz fel.
	 */
	public void EndTurn() {
		Logger.started(this, "EndTurn");
		if (this.stunDuration > 0)
			stunDuration--;
		this.activeTurn = false;

		game.NextTurn();
		Logger.finished(this, "EndTurn");
	}

	/**
	 * Elájult e?
	 * @return isFainted értéke
	 */
	@Override
	public boolean GetIsFainted() {
		return isFainted;
	}

	/**
	 * Bénítva van e?
	 * @return stunDuracion >? 0
	 */
	@Override
	public boolean GetIsStunned() {
		return stunDuration > 0;
	}

	/**
	 * Aktiv e a köre?
	 * @return activeTurn értéke
	 */
	@Override
	public boolean GetIsActiveTurn() {
		return activeTurn;
	}

	@Override
	public boolean GetIsAlive() {
		return true;
	}

	@Override
	public void ConnectTransistors(Transistor t1, Transistor t2) {
		throw new RuntimeException("Az instruktor nem csatlakoztathat ossze tranzisztorokat.");
	}

	/**
	 * Az Oktató a paraméterben kapott Usable tárgyat használja. Ekkor meghívja a UsedByInstructor függvényt
	 * az adott tárgyon. Ennek hatására az adott Item aktiválódik (amennyiben kepes erre), ha még nem volt aktiválva és
	 * képes lesz használni az adott Item képességeit.
	 *
	 *  @param  i  Item amit az Oktató használni fog
	 */
	public void UseItem(Item i) {
		Logger.started(this, "UseItem", i);
		if(inventory.contains(i)) i.UsedByInstructor(this);
		Logger.finished(this, "UseItem", i);
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
	public boolean Move(DoorSide d) {
		//már tudjuk hogy be lehet lépni
		if (!room.GetDoors().contains(d)) return false;
		Logger.started(this, "Move", d);
		DoorSide d2 = d.GetPair();
		Room r2 = d2.GetRoom();
		boolean isAppeared = AppearInRoom(r2);
		Logger.finished(this, "Move", d);
		return isAppeared;
	}

}