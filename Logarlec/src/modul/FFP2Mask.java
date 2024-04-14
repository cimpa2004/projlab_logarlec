package modul;

import util.Logger;
import util.Reader;

/**
 * Az FFP2 maszk reprezentációja
 * Tárolja hogy hány körig használható még és, hogy aktív e.
 * Ha nem aktív vagy már nem használható akkor nem engedi a használatot.
 * Elhelyezi magát az őt felvevők inventoryába, és megvalósítja a használatot.
 * {@inheritDoc}
 */
public class FFP2Mask extends Item implements Usable, Defendable {
	/**
	 * Tárolja hogy még hányszor véd meg a gáztól
	 */
	private int durability;

	/**
	 * Tárolja hogy aktiválva van e az FFP2 maszk
	 */
	private boolean isActivated;

	/**
	 * Ezen változó tárolja, hogy az adott FFP2Mask igazi-e.
	 * */
	private boolean isFake;

	public FFP2Mask(){
		isActivated = false;
		durability = 3;
	}

	public void SetIsFake(boolean b){
		isFake = b;
	}

	/** Aktivalja az FFP2Mask-ot. Ezutan minden korben amit egy gazos szobaban tolt, csokkenti az FFP2 maszk durabilityjat*/
	public boolean Activate() {
		Logger.started(this, "Activate");
		Logger.finished(this, "Activate");

		if(isFake) return false;

		if(!isActivated && durability > 0){
			isActivated = true;
			return true;
		}else {
			return false;
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
	 * Csökkenti a durability értékét 1-el
	 */
	public void Decrement() {
		Logger.started(this, "Decrement");
		if(isActivated) {
			if (durability > 0) durability = durability - 1;
			else isActivated = false;
		}
		Logger.finished(this, "Decrement");
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az st inventoryában
	 * @param st A felvevő hallgató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	public boolean PickedUpStudent(Student st) {
		Logger.started(this, "PickedUpStudent", st);
		boolean isAdded = st.AddToInventory(this);
		Logger.finished(this, "PickedUpStudent", st);
		return isAdded;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az i inventoryában
	 * @param i A felvevő oktató
	 * @return true ha sikerült felvenni, false ha nem
	 */
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
	public void Thrown(Person p) {
		Logger.started(this, "Thrown", p);
		p.RemoveFFP2Mask(this);
		p.RemoveFromInventory(this);
		Logger.finished(this, "Thrown", p);
	}

	/**
	 * Kezeli a használatot
	 * @param s Az FFP2-t használó hallgató
	 */
	public void UsedByStudent(Student s) {
		Logger.started(this, "UsedByStudent", s);
		Activate();
		if (isActivated) s.AddFFP2Mask(this);
		Logger.finished(this, "UsedByStudent", s);
	}

	/**
	 * Kezeli a használatot
	 * @param i Az FFP2-t használó oktató
	 */
	public void UsedByInstructor(Instructor i) {
		Logger.started(this, "UsedByInstructor", i);
		Activate();
		if (isActivated) i.AddFFP2Mask(this);
		Logger.finished(this, "UsedByInstructor", i);
	}

	@Override
	public boolean CanDefend() {
		Logger.started(this, "CanDefend");
		Logger.finished(this, "CanDefend");
		return isActivated && durability > 0;
	}

}

