package modul;

import util.Reader;

/**
 * A tranzisztorok reprezentálása
 * Nyilvántartja, hogy ki a párja (ha van) és hogy, be van e kapcsolva.
 * Elhelyezi magát az őt felvevők inventoryába, és megvalósítja a használatot.
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
		System.out.println("STARTED: " + this + ".Activate()");
		boolean returnValue = Reader.GetBooleanInput("Adja meg a visszatérési értéket");
		System.out.println("FINISHED: " + this + ".Activate()");
		return returnValue;
	}


	/**
	 * A két transistor párosítja, mindekettőn meghívja a SetPair-t
	 * @param t1 az egyik transistor
	 * @param t2 a másik transistor
	 */
	public void SetPairs(Transistor t1, Transistor t2) {
		System.out.println("STARTED: " + this + ".SetPairs(" + t1 + ", " + t2 +")");

		System.out.println("FINISHED: " + this + ".SetPairs(" + t1 + ", " + t2 +")");
	}

	/**
	 * Beállítja a paramétert a transistor párjának
	 * @param t a transistor új párja
	 */
	public void SetPair(Transistor t) {
		System.out.println("STARTED: " + this + ".SetPair(" + t +")");

		System.out.println("FINISHED: " + this + ".SetPair(" + t +")");
	}

	/**
	 * Megadja a párját
	 * @return Pair értéke, null ha nincs párja
	 */
	public Transistor GetPair() {
		System.out.println("STARTED: " + this + ".GetPair()");
		System.out.println("FINISHED: " + this + ".GetPair()");
		return this.pair;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az st inventoryában
	 * @param st a Transistort felvevő hallgató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	@Override
	public boolean PickedUpStudent(Student st) {
		System.out.println("STARTED: " + this + ".PickedUpStudent(" + st +")");
		boolean returnValue = Reader.GetBooleanInput("Adja meg a visszatérési értéket");
		System.out.println("FINISHED: " + this + ".PickedUpStudent(" + st +")");
		return returnValue;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az i inventoryában
	 * @param i az őt felvevő oktató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	@Override
	public boolean PickedUpInstructor(Instructor i) {
		System.out.println("STARTED: " + this + ".PickedUpInstructor(" + i +")");
		boolean returnValue = Reader.GetBooleanInput("Adja meg a visszatérési értéket");
		System.out.println("FINISHED: " + this + ".PickedUpInstructor(" + i +")");
		return returnValue;
	}

	/**
	 * Kezeli az eldobást
	 * @param p a Transistort eldobó személy
	 */
	@Override
	public void Thrown(Person p) {
		System.out.println("STARTED: " + this + ".Thrown(" + p +")");

		System.out.println("FINISHED: " + this + ".Thrown(" + p +")");
	}

	/**
	 * Kezeli a használatot
	 * @param s A Transistor használó Hallgató
	 */
	@Override
	public void UsedByStudent(Student s) {
		System.out.println("STARTED: " + this + ".UsedByStudent(" + s +")");

		System.out.println("FINISHED: " + this + ".UsedByStudent(" + s +")");
	}

	/**
	 * Kezeli a használatot (nem engedi)
	 * @param i A Transistor használni kívánó oktató
	 */
	@Override
	public void UsedByInstructor(Instructor i) {
		System.out.println("STARTED: " + this + ".UsedByInstructor(" + i +")");

		System.out.println("FINISHED: " + this + ".UsedByInstructor(" + i +")");
	}
	

}
