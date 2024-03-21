package modul;



/**
 * Nyilvántartja, hogy ki a párja (ha van) és hogy, be van e kapcsolva.
 * Elhelyezi magát az őt felvevők inventoryába, és megvalósítja a használatot.
 *
 *  {@inheritDoc}
 */
public class Transistor extends Item implements Usable {
	/**
	 *Tárolja hogy aktiválva van e
	 */
	private boolean isActive;

	/**
	 * Tárolja a párját
	 */
	private Transistor pair;


	/**
	 * Aktiválja a tárgyat
	 * @return ha már aktív akkor falsal tér vissza, egyébként true
	 */
	@Override
	public boolean Activate() {
		return false;
	}


	
	/** */
	public void SetPairs(Transistor t1, Transistor t2) {
	}
	
	/** */
	public void SetPair(Transistor t) {
	}

	/**
	 * Megadja a párját
	 * @return Pair értéke
	 */
	public Transistor GetPair() {
		return null;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az st inventoryában
	 * @param st a Transistort felvevő hallgató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	@Override
	public boolean PickedUpStudent(Student st) {
		return false;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az i inventoryában
	 * @param i az őt felvevő oktató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	@Override
	public boolean PickedUpInstructor(Instructor i) {
		return false;
	}

	/**
	 * Kezeli az eldobást
	 * @param p a Transistort eldobó személy
	 */
	@Override
	public void Thrown(Person p) {
	}

	/**
	 * Kezeli a használatot
	 * @param s A Transistor használó Hallgató
	 */
	@Override
	public void UsedByStudent(Student s) {
	}

	/**
	 * Kezeli a használatot (nem engedi)
	 * @param i A Transistor használni kívánó oktató
	 */
	@Override
	public void UsedByInstructor(Instructor i) {
	}
	

}
