package modul;

/**
 * Tárolja, hogy aktív e. Ha nem aktív akkor nem engedi a használatot.
 * Elhelyezi magát az őt felvevők inventoryába, és megvalósítja a használatot.
 * {@inheritDoc}
 */
public class Camembert extends Item implements Usable {
	/**
	 * Tárolja hogy aktív e
	 */
	private boolean isActivated;

	/**
	 * Aktiválja az Camembert-t
	 * @return True ha sikerült False, ha nem/ már használva volt
	 */
	@Override
	public boolean Activate() {
        return false;
    }

	/**
	 * Kezeli a felvételt, elhelyezi magát az st inventoryában
	 * @param st A felvevő hallgató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	@Override
	public boolean PickedUpStudent(Student st) {
		return false;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az i inventoryában
	 * @param i A felvevő oktató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	@Override
	public boolean PickedUpInstructor(Instructor i) {
		return false;
	}

	/**
	 * Kezeli az eldobást
	 * @param p Az eldobó személy
	 */
	@Override
	public void Thrown(Person p) {
	}

	/**
	 * Kezeli a használatot
	 * @param s Az Camembert-t használó hallgató
	 */
	@Override
	public void UsedByStudent(Student s) {
	}

	/**
	 * Kezeli a használatot
	 * @param i A Camembert-t használő oktató
	 */
	@Override
	public void UsedByInstructor(Instructor i) {
	}
	


}
