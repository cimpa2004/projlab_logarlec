package modul;
import controller.Game;
import util.Logger;
import util.Reader;

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

	/**
	 * Tárolja a játékot, a teszteléshez van rá szükség
	 */
	private Game game;

	public Instructor(String id) {
		super(id);
	}

	public Instructor() {
		super(UUID.randomUUID().toString());
	}

	/**
	 * A függvény által megjelenik az Oktató a paraméterként megadott szobában.
	 *
	 *  @param  r  Az a szoba ahol megjelenik az Oktató
	 */
	public void AppearInRoom(Room r) {
		Logger.started(this, "AppearInRoom", r);
		int currentC = r.GetCurrentCapacity();
		int maxC = r.GetMaxCapacity();
		if(currentC < maxC) {
			room.SetCurrentCapacity(room.GetCurrentCapacity()-1); // kilepes a jelenlegi szobabol
			room = r; // atlepes a masik szobaba
			room.SetCurrentCapacity(room.GetCurrentCapacity()+1); // belepes a masik szobaba
			room.AddInstructor(this);
			
			ArrayList<Student> students = room.GetStudents();
			for(Student student : students) {
				StealSoul(student);
			}
			//Megnezi hogy a szoba gázos e, ez alapján felébred vagy elájul
			if (room.GetPoisonDuration() > 0){
				this.SetIsFainted(true);
			}else if (room.GetPoisonDuration()<=0 && !GetIsFainted()){
				this.SetIsFainted(false);
			}
			//tárgyfelvétel
			if (this.inventory.size() <5){
				if(!this.GetRoom().GetIsSticky())
					Pickup(this.GetRoom().GetItems().get(GetRoom().GetItems().size()));
			}
			//mask aktiválása, minimális
			for (Defendable m: this.GetFFP2Masks())
				((FFP2Mask) m).Activate();
		}
		Logger.finished(this, "AppearInRoom", r);
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
		if (isFainted || stunDuration > 0)
			this.EndTurn();

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
		if(stunDuration > 0 || isFainted) EndTurn();

		// oktato minden kore elejen megprobalja elvenni minden hallgato lelket a jelenlegi szobajaban
		for(Student student : room.GetStudents()){
			StealSoul(student);
		}

		//Mozgató logika
		Random random = new Random();
		if (game.GetIsDeterministic()){
			List<DoorSide> doorsCopy = new ArrayList<>(this.GetRoom().GetDoors());
			Collections.shuffle(doorsCopy);

			for (DoorSide dr : doorsCopy){
				if (random.nextBoolean())
					if (dr.GetCanBeOpened() &&
						dr.GetIsVisible()&&
						dr.GetPair().GetRoom().GetMaxCapacity() > dr.GetPair().GetRoom().GetCurrentCapacity()){
							this.Move(dr);//a keresett ajtón átmegy
							break;
						}
			}
		}else{ //determinisztikus mozgás az első lehetséges szomszéd
			for (DoorSide dr : this.GetRoom().GetDoors()){
				if (dr.GetCanBeOpened() &&
						dr.GetIsVisible()&&
						dr.GetPair().GetRoom().GetMaxCapacity() > dr.GetPair().GetRoom().GetCurrentCapacity()){
					this.Move(dr);//a keresett ajtón átmegy
					break;
					}
			}
		}

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

	@Override
	public boolean GetIsFainted() {
		return isFainted;
	}

	@Override
	public boolean GetIsStunned() {
		return stunDuration > 0;
	}

	@Override
	public boolean GetIsActiveTurn() {
		return activeTurn;
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
		//már tudjuk hogy be lehet lépni
		Logger.started(this, "Move", d);
		DoorSide d2 = d.GetPair();
		Room r2 = d2.GetRoom();
		room.RemoveInstructor(this);
		AppearInRoom(r2);
		Logger.finished(this, "Move", d);
	}

}