package modul;



import util.Reader;

import java.util.Scanner;
import util.Reader;

/**
 * Az FFP2 maszk reprezentációja
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

	public FFP2Mask(){
		isActivated = false;
	}

	/** */
	public boolean Activate() {
		System.out.println("STARTED: " + this + ".Activate()");
		isActivated = Reader.GetBooleanInput("Sikerült aktiválni az FFP2Mask-ot?");
		System.out.println("FINISHED: " + this + ".Activate()");
		return isActivated;
	}

	/**
	 * Csökkenti a durability értékét 1-el
	 */
	public void Decrement() {
		System.out.println("STARTED: " + this + ".Decrement()");
		if(durability > 0) durability = durability - 1;
		System.out.println("FINISHED: " + this + ".Decrement()");
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az st inventoryában
	 * @param st A felvevő hallgató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	public boolean PickedUpStudent(Student st) {
		System.out.println("STARTED: " + this + ".PickedUpStudent(" + st +")");
		boolean isAdded = st.AddToInventory(this);
		System.out.println("FINISHED: " + this + ".PickedUpStudent(" + st +")");
		return isAdded;
	}

	/**
	 * Kezeli a felvételt, elhelyezi magát az i inventoryában
	 * @param i A felvevő oktató
	 * @return true ha sikerült felvenni, false ha nem
	 */
	public boolean PickedUpInstructor(Instructor i) {
		System.out.println("STARTED: " + this + ".PickedUpInstructor(" + i + ")");
		boolean isAdded = i.AddToInventory(this);
		System.out.println("FINISHED: " + this + ".PickedUpInstructor(" + i + ")");
		return isAdded;
	}

	/**
	 * Kezeli az eldobást
	 * @param p Az eldobó személy
	 */
	public void Thrown(Person p) {
		System.out.println("STARTED: " + this + ".Thrown(" + p +")");

		System.out.println("FINISHED: " + this + ".Thrown(" + p +")");
	}

	/**
	 * Kezeli a használatot
	 * @param s Az FFP2-t használó hallgató
	 */
	public void UsedByStudent(Student s) {
		System.out.println("STARTED: " + this + ".UsedByStudent(" + s +")");
		Activate();
		if (isActivated) s.AddFFP2Mask(this);
		System.out.println("FINISHED: " + this + ".UsedByStudent(" + s +")");
	}

	/**
	 * Kezeli a használatot
	 * @param i Az FFP2-t használó oktató
	 */
	public void UsedByInstructor(Instructor i) {
		System.out.println("STARTED: " + this + ".UsedByInstructor(" + i + ")");
		Activate();
		if (isActivated) i.AddFFP2Mask(this);
		System.out.println("FINISHED: " + this + ".UsedByInstructor(" + i + ")");
	}

	@Override
	public boolean CanDefend() {
		System.out.println("STARTED: " + this + ".CanDefend()");
		System.out.println("FINISHED: " + this + ".CanDefend()");
		durability = Reader.GetIntInput("Hanyszor hasznalhato meg az FFP2 maszk?");
		return isActivated && durability > 0;
	}

}
