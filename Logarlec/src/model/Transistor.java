package model;

import util.Logger;
import viewmodel.IVTransistor;
import viewmodel.IVRoom;

import java.awt.*;
import java.util.UUID;

/**
 * A Transistor osztály a Tranzisztor tárgy reprezentációja.
 * Eltárolja a párját, ha van neki és hogy aktív-e.
 * Felelőssége, hogy egy Transistor példány felvételekor, használatakor és eldobásakor
 * frissítse a Transistor változóit és kezelje, hogy ezen cselekvések,
 * hogy hatnak ki a Transistor környezetére (owner és room).
 *  {@inheritDoc}
 */
public class Transistor extends Item implements Usable, IVTransistor {
	/**
	 * Ez a boolean típusú változó tárolja az adott Transistor példányról,
	 * hogy aktiválva van-e vagy nem.
	 */
	private boolean isActive;

	/**
	 * Ez a Transistor típusú változó tárolja az adott Transistor példány párját, ha van neki.
	 */
	private Transistor pair;

	/**
	 * A Transistor osztály konstruktora.
	 * Az isActivated értékét hamisra állítja.
	 * A pair értékét null -ra állítja.
	 * Az id értékét a paraméterként kapott értékre állítja.
	 *
	 * @param id Az id változó értékét erre az értékre állítja.
	 */
	public Transistor(String id){
		super(id);
		isActive = false;
		pair = null;
	}

	/**
	 * Az Transistor osztály konstruktora.
	 * Az isActivated értékét hamisra állítja.
	 * A pair értékét null -ra állítja.
	 * Az id-t beállítja egy random értékre.
	 */
	public Transistor(){
		super("Transistor-"+UUID.randomUUID());
		isActive = false;
		pair = null;
	}

	/**
	 * Visszaadja, hogy az adott Transistor hamis-e.
	 * Egy Transistor nem lehet hamis, tehát
	 * mindig hamisat fog visszaadni.
	 * */
	@Override
	public boolean GetIsFake() {
		Logger.startedModel(this, "GetIsFake");
		Logger.finishedModel(this, "GetIsFake");
		return false;
	}

	/**
	 *  Ez a metódus, abban az esetben lesz meghívva ha egy Transistor tárgyat aktiválni szeretnénk.
	 *  Ha már aktiválva volt, akkor deaktiváljuk, azaz az isActive változót hamisra állítjuk.
	 *  Ha nem aktív akkor aktiváljuk, azaz az isActive változót igazra állítjuk.
	 *
	 * @return A metódus az isActive értékével tér majd vissza.
	 */
	@Override
	public boolean Activate() {
		Logger.startedModel(this, "Activate");
		if(!isActive && this.GetPair() != null)
			isActive = true;
		else
			isActive = false;
		Logger.finishedModel(this, "Activate");
		return isActive;
	}

	/**
	 * Ezen metódussal le lehet kérdezni, hogy egy Transistor példány aktív-e.
	 *
	 * @return az isActivated változó értéke.
	 */
	@Override
	public boolean GetIsActive() {
		Logger.startedModel(this, "GetIsActive");
		Logger.finishedModel(this, "GetIsActive");
		return isActive;
	}


	/**
	 * A Transistort párosítja a paraméterként kapott Transistorral.
	 * A két transistor párosítja, mindekettőn meghívja a SetPair-t
	 *
	 * @param t a másik transistor
	 */
	public void SetPairs(Transistor t) {
		Logger.startedModel(this, "SetPairs", t);
		this.SetPair(t);
		t.SetPair(this);
		Logger.finishedModel(this, "SetPairs", t);
	}

	/**
	 * A Transistor pair válozóját a paraméterként kapott Transistorra állítja.
	 *
	 * @param t a transistor új párja
	 */
	public void SetPair(Transistor t) {
		Logger.startedModel(this, "SetPair", t);
		pair = t;
		Logger.finishedModel(this, "SetPair", t);
	}

	/**
	 * Ezen metódussal le lehet kérdezni, hogy az
	 * adott Transistor  -nak melyik Transistor  a párja.
	 *
	 * @return Pair értéke, null ha nincs párja
	 */
	public Transistor GetPair() {
		Logger.startedModel(this, "GetPair");
		Logger.finishedModel(this, "GetPair");
		return this.pair;
	}

	/**
	 * Ezen metódus referenciaként átveszi hogy melyik Student vette fel
	 * és megpróbálja elhelyezi magát a Student inventoryában.
	 * Meghívja a Student AddToInventory metódusát, aminek átadja a Transistort.
	 * Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie a Transistort a Student -nek.
	 *
	 * @param st A felvevő hallgató
	 * @return Igaz, ha sikerült felvenni és hamis ha nem.
	 */
	@Override
	public boolean PickedUpStudent(Student st) {
		Logger.startedModel(this, "PickedUpStudent", st);
		boolean isAdded = st.AddToInventory(this);
		if(ivItemUpdate != null){
			if (isAdded){
				ivItemUpdate.SetOwner(st.GetIVStudentUpdate());
				ivItemUpdate.PickedUpUpdate(this, true);
			}else{
				ivItemUpdate.PickedUpUpdate(this, false);
			}
		}
		Logger.finishedModel(this, "PickedUpStudent", st);
		return isAdded;
	}

	/**
	 * Ezen metódus referenciaként átveszi hogy melyik Instructor vette fel
	 * és megpróbálja elhelyezi magát az Instructor inventoryában.
	 * Meghívja az Instructor AddToInventory metódusát, aminek átadja a Transistort.
	 * Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie a Transistort az Instructor -nak.
	 *
	 * @param i A felvevő oktató
	 * @return Igaz, ha sikerült felvenni és hamis ha nem.
	 */
	@Override
	public boolean PickedUpInstructor(Instructor i) {
		Logger.startedModel(this, "PickedUpInstructor", i);
		boolean isAdded = i.AddToInventory(this);
		if(ivItemUpdate != null){
			if (isAdded){
				ivItemUpdate.SetOwner(i.GetIVInstructorUpdate());
				ivItemUpdate.PickedUpUpdate(this, true);
			}else{
				ivItemUpdate.PickedUpUpdate(this, false);
			}
		}
		Logger.finishedModel(this, "PickedUpInstructor", i);
		return isAdded;
	}

	/**
	 * Ezen metódus akkor hívódik meg, ha a Person el szeretné
	 * távolítani az inventoryából az adott Transistor tárgyat.
	 * Abban az esetben, ha az adott Transistor -nak van párja,
	 * mindketten aktívak és a párja már le lett rakva egy másik szobában,
	 * akkor a Person lerakja a tranzisztort, azaz a RemoveFromInventory meghívásra kerül,
	 * majd megjelenik abban a szobában ahol a Transistor párja van és
	 * mindkettő Transistor deaktiválásra kerül.
	 * Ellenkező esetben csupán a Person lerakja a Transistor -t,
	 * azaz a RemoveFromInventory meghívásra kerül.
	 *
	 * @param p a Transistort eldobó személy
	 */
	@Override
	public void Thrown(Person p) {
		Logger.startedModel(this, "Thrown", p);
		if(pair != null && isActive && pair.isActive &&
				pair.GetRoom() != null && pair.GetRoom() != p.GetRoom()){
			this.Activate();
			pair.Activate();
			p.RemoveFromInventory(this);
			p.AppearInRoom(pair.GetRoom());
			if(ivItemUpdate != null){
				ivItemUpdate.ThrownUpdate(this, true);
			}
		}else{
			p.RemoveFromInventory(this);
			if(ivItemUpdate != null){
				ivItemUpdate.ThrownUpdate(this, false);
			}
		}
		Logger.finishedModel(this, "Thrown", p);
	}

	/**
	 * Ezen metódus akkor hívódik meg, ha egy Student szeretne
	 * használni egy az inventoryában lévő Transistor -t.
	 * A Transistor ez esetben aktiválásra kerül, azaz meghívódik az Activate metódus.
	 *
	 * @param s A Transistor használó Hallgató
	 */
	@Override
	public void UsedByStudent(Student s) {
		Logger.startedModel(this, "UsedByStudent", s);
		if(Activate()){
			if(ivItemUpdate != null){
				ivItemUpdate.UsedUpdate(this, true);
			}
		}else{
			if(ivItemUpdate != null){
				ivItemUpdate.UsedUpdate(this, false);
			}
		}
		Logger.finishedModel(this, "UsedByStudent", s);
	}

	/**
	 *  Ezen metódus akkor hívódik meg, ha egy Instructor szeretne
	 *  használni egy az inventoryában lévő Transistor -t.
	 *  Instructor nem képes a Transistor használatára, ezért e metódus egyből hamissal tér vissza.
	 *
	 * @param i A Transistor használni kívánó oktató
	 */
	@Override
	public void UsedByInstructor(Instructor i) {
		Logger.startedModel(this, "UsedByInstructor", i);
		if(ivItemUpdate != null){
			ivItemUpdate.UsedUpdate(this, false);
		}
		Logger.finishedModel(this, "UsedByInstructor", i);
	}


	@Override
	public IVRoom GetIVRoom() {
        Logger.startedModel(this, "GetIVRoom");
        Logger.finishedModel(this, "GetIVRoom");
        return this.room;
    }
}
