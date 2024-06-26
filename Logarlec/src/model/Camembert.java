package model;
import util.Logger;
import viewmodel.ActivateResult;
import viewmodel.IVCamembert;
import viewmodel.IVRoom;

import java.awt.*;
import java.util.UUID;

/**
 * A Camembert osztály a Dobozolt Camembert tárgy reprezentációja.
 * Eltárolja egy Camembertről, hogy aktiválva van-e és képes aktiválni azt.
 * Felelőssége továbbá, hogy egy Camembert példány felvételekor, eldobásakor és használatakor
 * frissítse a Camembert változóit és kezelje, hogy ezen cselekvések,
 * hogy hatnak ki a Camembert környezetére (owner és room).
 * {@inheritDoc}
 */
public class Camembert extends Item implements Usable, IVCamembert {

	/**
	 * Ez a boolean típusú változó tárolja az adott Camembert példányról,
	 * hogy aktiválva van-e vagy nem.
	 */
	private boolean isActivated;

	/**
	 * A Camembert osztály konstruktora.
	 * Az isActivated értékét hamisra állítja.
	 * Az id értékét a paraméterként kapott értékre állítja.
	 *
	 * @param id Az id változó értékét erre az értékre állítja.
	 */
	public Camembert(String id){
		super(id);
		isActivated = false;
	}

	/**
	 * A Camembert osztály konstruktora.
	 * Az isActivated értékét hamisra állítja.
	 * Az id-t beállítja egy random értékre.
	 */
	public Camembert(){
		super("Camembert-"+UUID.randomUUID());
		isActivated = false;
	}


	/**
	 * 	Ezen metódus a paraméterként kapott értékre állítja a Camembert isActivated változóját.
	 *
	 * @param isActivated Az isActivated értékét erre az értékre állítja.
	 * */
	public void SetIsActivated(boolean isActivated) {
		Logger.startedModel(this, "SetIsActivated", isActivated);
		this.isActivated = isActivated;
		Logger.finishedModel(this, "SetIsActivated", isActivated);
	}

	/**
	 * Visszaadja, hogy az adott Camembert	 hamis-e.
	 * Egy Camembert nem lehet hamis, tehát
	 * mindig hamisat fog visszaadni.
	 *
	 * @return Mindig hamis.
	 * */
	@Override
	public boolean GetIsFake() {
		Logger.startedModel(this, "GetIsFake");
		Logger.finishedModel(this, "GetIsFake");
		return false;
	}

	/**
	 * Ez a metódus, abban az esetben lesz meghívva,
	 * ha egy Camembert tárgyat aktiválni szeretnénk.
	 * Ha a tárgy már használva volt - azaz az isActivated már igazra lett állítva -
	 * akkor a metódus hamissal tér vissza.
	 * Ellenkező esetben igazra állítja az isActivated változót és igazzal tér vissza.
	 *
	 * @return True ha sikerült és False, ha nem vagy már használva volt.
	 */
	@Override
	public boolean Activate() {
		Logger.startedModel(this, "Activate");
		if(isActivated){
			if(ivItemUpdate!=null) ivItemUpdate.Activated(this, ActivateResult.ALREADY_ACTIVATED);
			Logger.finishedModel(this, "Activate");
			return false;
		}else{
			if(ivItemUpdate!=null) ivItemUpdate.Activated(this, ActivateResult.SUCCESSFULLY_ACTIVATED);
			isActivated = true;
			Logger.finishedModel(this, "Activate");
			return true;
		}
		
	}


	/**
	 * Ezen metódussal le lehet kérdezni, hogy egy Camembert példány aktív-e.
	 *
	 * @return az isActivated változó értéke.
	 */
	@Override
	public boolean GetIsActive() {
        Logger.startedModel(this, "GetIsActive");
        Logger.finishedModel(this, "GetIsActive");
        return isActivated;
    }

	/**
	 * Ezen metódussal le lehet kérdezni, hogy az
	 * adott Transistor  -nak melyik Transistor  a párja.
	 *
	 * @return Mindig null, mert a Camembert nem Transistor.
	 */
	@Override
	public Transistor GetPair() {
		Logger.startedModel(this, "GetPair");
		Logger.finishedModel(this, "GetPair");
		return null;
	}

	/**
	 * Ezen metódus referenciaként átveszi hogy melyik Student vette fel
	 * és megpróbálja elhelyezi magát a Student inventoryában.
	 * Meghívja a Student AddToInventory metódusát, aminek átadja a Camambertet.
	 * Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie a Camembertet a Student -nek.
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
	 * Meghívja az Instructor AddToInventory metódusát, aminek átadja a Camembertet.
	 * Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie a Camembertet az Instructor -nak.
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
	 * távolítani az inventoryából az adott Camembert tárgyat.
	 * A metódus eltávolítja a Camembertet a Person inventoryából
	 * a Person RemoveFromInventory metódus meghívásával.
	 *
	 * @param p Az eldobó személy
	 */
	@Override
	public void Thrown(Person p) {
		Logger.startedModel(this, "Thrown", p);
		p.RemoveFromInventory(this);
		if(ivItemUpdate != null){
			ivItemUpdate.ThrownUpdate(this, false);
		}
		Logger.finishedModel(this, "Thrown", p);
	}
	/**
	 * Ezen metódus akkor hívódik meg, ha egy Student szeretne használni egy
	 * az inventoryában lévő Camembertet. Ekkor először aktiválja, tehát meghívja az Activate metódust.
	 * Amennyiben az Activate metódus igaz értékkel tér vissza, akkor sikerült aktiválni.
	 * Ekkor a szobát, amelyben a Student tartózkodik mérges gáz lepi el 5 körig,
	 * tehát a szoba poisonDuration változóját 5 re állítja.
	 *
	 * @param s Az Camembert-t használó hallgató
	 */
	@Override
	public void UsedByStudent(Student s) {
		Logger.startedModel(this, "UsedByStudent", s);
		if(Activate()) s.GetRoom().SetPoisonDuration(5);
		if(s.GetIsActiveTurn() && s.GetIsFainted()) s.EndTurn();
		Logger.finishedModel(this, "UsedByStudent", s);
	}

	/**
	 * Ezen metódus akkor hívódik meg, ha egy Instructor szeretne használni egy
	 * az inventoryában lévő Camembertet. Ekkor először aktiválja, tehát meghívja az Activate metódust.
	 * Amennyiben az Activate metódus igaz értékkel tér vissza, akkor sikerült aktiválni.
	 * Ekkor a szobát, amelyben az Instructor tartózkodik mérges gáz lepi el 5 körig,
	 * tehát a szoba poisonDuration változóját 5 re állítja.
	 *
	 * @param i Az Camembert-t használó oktató
	 */
	@Override
	public void UsedByInstructor(Instructor i) {
		Logger.startedModel(this, "UsedByInstructor", i);
		if(Activate()) {
			i.GetRoom().SetPoisonDuration(5);
			// ajuljon el ha magara robbantotta es nem tudja megvedeni magat
			if(!i.GetFFP2Masks().isEmpty()){
				Defendable ffp2Mask = i.GetRandomActive(i.GetFFP2Masks());
				if(ffp2Mask != null){
					ffp2Mask.Decrement();
					// ha mar a vedes utan tobbet nem tud vedeni, akkor kiszedjuk a listabol
					if(!ffp2Mask.CanDefend()) i.RemoveFFP2Mask(ffp2Mask);
				}else{
					i.SetIsFainted(true);
					if (i.GetIsActiveTurn()){
						i.EndTurn();
						Logger.finishedModel(this, "UsedByInstructor", i);
						return;
					}
				}
			}else{
				i.SetIsFainted(true);
				if (i.GetIsActiveTurn()){
					i.EndTurn();
					Logger.finishedModel(this, "UsedByInstructor", i);
					return;
				}
			}
		}
		Logger.finishedModel(this, "UsedByInstructor", i);
	}

	@Override
	public String GetID() {
		return id;
	}

	@Override
	public IVRoom GetIVRoom() {
        Logger.startedModel(this, "GetIVRoom");
        Logger.finishedModel(this, "GetIVRoom");
        return this.room;
    }
}
