package modul;

import util.Logger;
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
		Logger.started(this, "Activate");
		boolean returnValue = Reader.GetBooleanInput("Adja meg a visszatérési értéket");
		Logger.finished(this, "Activate");
		return returnValue;
	}


	/**
	 * A két transistor párosítja, mindekettőn meghívja a SetPair-t
	 * @param t1 az egyik transistor
	 * @param t2 a másik transistor
	 */
	public void SetPairs(Transistor t1, Transistor t2) {
		Logger.started(this, "SetPairs", t1, t2);
		Logger.finished(this, "SetPairs", t1, t2);
	}

	/**
	 * Beállítja a paramétert a transistor párjának
	 * @param t a transistor új párja
	 */
	public void SetPair(Transistor t) {
		Logger.started(this, "SetPair", t);
		Logger.finished(this, "SetPair", t);
	}

	/**
	 * Megadja a párját
	 * @return Pair értéke, null ha nincs párja
	 */
	public Transistor GetPair() {
		Logger.started(this, "GetPair");
		Logger.finished(this, "GetPair");
		return this.pair;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az st inventoryában
	 * @param st a Transistort felvevő hallgató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	@Override
	public boolean PickedUpStudent(Student st) {
		Logger.started(this, "PickedUpStudent", st);
		boolean returnValue = Reader.GetBooleanInput("Adja meg a visszatérési értéket");
		Logger.finished(this, "PickedUpStudent", st);
		return returnValue;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az i inventoryában
	 * @param i az őt felvevő oktató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	@Override
	public boolean PickedUpInstructor(Instructor i) {
		Logger.started(this, "PickedUpInstructor", i);
		boolean returnValue = Reader.GetBooleanInput("Adja meg a visszatérési értéket");
		Logger.finished(this, "PickedUpInstructor", i);
		return returnValue;
	}

	/**
	 * Kezeli az eldobást
	 * @param p a Transistort eldobó személy
	 */
	@Override
	public void Thrown(Person p) {
		Logger.started(this, "Thrown", p);
		Logger.finished(this, "Thrown", p);
	}

	/**
	 * Kezeli a használatot
	 * @param s A Transistor használó Hallgató
	 */
	@Override
	public void UsedByStudent(Student s) {
		Logger.started(this, "UsedByStudent", s);
		Logger.finished(this, "UsedByStudent", s);
	}

	/**
	 * Kezeli a használatot (nem engedi)
	 * @param i A Transistor használni kívánó oktató
	 */
	@Override
	public void UsedByInstructor(Instructor i) {
		Logger.started(this, "UsedByInstructor", i);
		Logger.finished(this, "UsedByInstructor", i);
	}
	

}
