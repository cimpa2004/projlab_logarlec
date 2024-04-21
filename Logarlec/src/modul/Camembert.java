package modul;
import util.Logger;
import util.Reader;

import java.util.UUID;

/**
 * A camebert-t reprezentációja
 * Tárolja, hogy aktív e. Ha nem aktív akkor nem engedi a használatot.
 * Elhelyezi magát az őt felvevők inventoryába, és megvalósítja a használatot.
 * {@inheritDoc}
 */
public class Camembert extends Item implements Usable {
	/**
	 * Tárolja hogy aktív e
	 */
	private boolean isActivated;

	public Camembert(String id){
		super(id);
		isActivated = false;
	}

	public Camembert(){
		super(UUID.randomUUID().toString());
		isActivated = false;
	}

	@Override
	public boolean GetIsFake() {
		return false;
	}

	/**
	 * Aktiválja az Camembert-t
	 * @return True ha sikerült False, ha nem/ már használva volt
	 */
	@Override
	public boolean Activate() {
		Logger.started(this, "Activate");

		Logger.finished(this, "Activate");
		if(isActivated){
			return false;
		}else{
			isActivated = true;
			return  true;
		}
	}


	/**
	 *Vissza adja, hogy aktivalva volt-e mar
	 * @return igaz/hamis ertek ami jelzi hogy aktivalva van e
	 */
	@Override
	public boolean GetIsActive() {
		return isActivated;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az st inventoryában
	 * @param st A felvevő hallgató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	@Override
	public boolean PickedUpStudent(Student st) {
		Logger.started(this, "PickedUpStudent", st);
		boolean isAdded = st.AddToInventory(this);
		Logger.finished(this, "PickedUpStudent", st);
		return isAdded;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az i inventoryában
	 * @param i A felvevő oktató
	 * @return True, ha sikerült felvenni; False, ha nem
	 */
	@Override
	public boolean PickedUpInstructor(Instructor i) {
		Logger.started(this, "PickedUpInstructor", i);
		boolean isAdded = i.AddToInventory(this);
		Logger.finished(this, "PickedUpInstructor", i);
		return isAdded;
	}

	/**
	 * Kezeli az eldobást
	 * @param p Az eldobó személy
	 */
	@Override
	public void Thrown(Person p) {
		Logger.started(this, "Thrown", p);
		p.RemoveFromInventory(this);
		Logger.finished(this, "Thrown", p);
	}

	/**
	 * Kezeli a használatot
	 * @param s Az Camembert-t használó hallgató
	 */
	@Override
	public void UsedByStudent(Student s) {
		Logger.started(this, "UsedByStudent", s);
		if(Activate())s.GetRoom().SetPoisonDuration(5);
		Logger.finished(this, "UsedByStudent", s);
	}

	/**
	 * Kezeli a használatot
	 * @param i A Camembert-t használó oktató
	 */
	@Override
	public void UsedByInstructor(Instructor i) {
		Logger.started(this, "UsedByInstructor", i);
		if(Activate()) i.GetRoom().SetPoisonDuration(5);
		Logger.finished(this, "UsedByInstructor", i);
	}
}
