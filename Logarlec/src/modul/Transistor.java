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

	public Transistor(){
		isActive = false;
		pair = null;
	}

	/**
	 * Aktiválja a tárgyat
	 * @return ha már aktív akkor falsal tér vissza, egyébként true
	 */
	@Override
	public boolean Activate() {
		Logger.started(this, "Activate");
		if(!isActive)
			isActive = true;
		else
			isActive = false;
		Logger.finished(this, "Activate");
		return isActive;
	}

	/**
	 *Vissza adja, hogy aktivalva volt-e mar
	 * @return igaz/hamis ertek ami jelzi hogy aktivalva van e
	 */
	@Override
	public boolean GetIsActive() {
		return isActive;
	}


	/**
	 * A két transistor párosítja, mindekettőn meghívja a SetPair-t
	 * @param t a másik transistor
	 */
	public void SetPairs(Transistor t) {
		Logger.started(this, "SetPairs", t);
		this.SetPair(t);
		t.SetPair(this);
		Logger.finished(this, "SetPairs", t);
	}

	/**
	 * Beállítja a paramétert a transistor párjának
	 * @param t a transistor új párja
	 */
	public void SetPair(Transistor t) {
		Logger.started(this, "SetPair", t);
		pair = t;
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
		boolean isAdded = st.AddToInventory(this);
		Logger.finished(this, "PickedUpStudent", st);
		return isAdded;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az i inventoryában
	 * @param i az őt felvevő oktató
	 * @return true ha sikerült felvenni, false ha nem
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
	 * @param p a Transistort eldobó személy
	 */
	@Override
	public void Thrown(Person p) {
		Logger.started(this, "Thrown", p);
		if(pair != null && isActive && pair.isActive &&
				pair.GetRoom() != null && pair.GetRoom() != p.GetRoom()){
			p.RemoveFromInventory(this);
			p.AppearInRoom(pair.GetRoom());
			this.Activate();
			pair.Activate();
		}else{
			p.RemoveFromInventory(this);
		}
		Logger.finished(this, "Thrown", p);
	}

	/**
	 * Kezeli a használatot
	 * @param s A Transistor használó Hallgató
	 */
	@Override
	public void UsedByStudent(Student s) {
		Logger.started(this, "UsedByStudent", s);
		this.Activate();
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
