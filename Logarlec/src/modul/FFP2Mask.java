package modul;


/**
 *Tárolja hogy hány körig használható még és, hogy aktív e.
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
	 * Aktiválja az FFP2 maszkot
	 * @return True ha sikerült False, ha nem/ már aktív
	 */

	public boolean Activate() {
		return false;
	}

	/**
	 * Csökkenti a durability értékét 1-el
	 */
	public void Decrement() {
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az st inventoryában
	 * @param st A felvevő hallgató
	 * @return true ha sikerült felvenni, false ha nem
	 */

	public boolean PickedUpStudent(Student st) {
		return false;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az i inventoryában
	 * @param i A felvevő oktató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	public boolean PickedUpInstructor(Instructor i) {
		return false;
	}

	/**
	 * Kezeli az eldobást
	 * @param p Az eldobó személy
	 */
	public void Thrown(Person p) {
	}

	/**
	 * Kezeli a használatot
	 * @param s Az FFP2-t használó hallgató
	 */
	public void UsedByStudent(Student s) {
	}

	/**
	 * Kezeli a használatot
	 * @param i Az FFP2-t használó oktató
	 */
	public void UsedByInstructor(Instructor i) {
	}


	@Override
	public boolean CanDefend() {
		return false;
	}

}
