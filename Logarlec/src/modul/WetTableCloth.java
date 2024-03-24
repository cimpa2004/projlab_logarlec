package modul;


import util.Logger;
import util.Reader;

/**
 * A WetTableCloth osztály a Nedves Táblatörlő Rongy reprezentációja.
 * A WetTableCloth a tárgyak egyike, amelyeket a játékosok
 * a szobákban találhatnak. Ezeket a tárgyakat fel tudják venni, illetve eldobni.
 * A WetTableCloth tárgyat lehet aktiválni és aktiválás után egy bizonyos számú körig
 * védelmet biztosít az aktiváló Student -nek az Instructor -okkal szemben.
 * A WetTableCloth -ot Instructorok is fel tudják venni, illetve eldobni, de
 * használni nem tudják.
 * */
public class WetTableCloth extends Item implements Usable, Defendable {

	/**
	 * Ez a változó eltárolja, hogy még hány körig képes a
	 * WetTableCloth megvédeni a Studentet, akihez tartozik
	 * */
	private int effectDuration;

	/**
	 * Ez a változó eltárolja, hogy az adott WetTableCloth aktiválva van-e vagy sem.
	 * */
	private boolean isActivated;

	/**
	 * Ezen metódus meghívásakor a WetTableCloth, amire ezt a függvényt meghívták,
	 * aktivált állapotba kerül, azaz igazra állítódik az isActivated változója.
	 * */
	public boolean Activate() {
		Logger.started(this, "Activate");
		isActivated = Reader.GetBooleanInput("Sikerült aktiválni a WetTableClothot?");
		Logger.finished(this, "Activate");
		return isActivated;
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
	 * Ezen metódus meghívásakor a paraméterként megkapott
	 * Student inventárjában elhelyezi magát a WetTableCloth
	 * és átállítja az owner változóját a paraméterként kapott Student -re.
	 * Igaz értékkel fog visszatérni, ha sikeresen fel tudta venni
	 * az adott tárgyat a Student és hamissal, ha nem.
	 * Abben az esetben nem sikerülhet felvenni a tárgyat,
	 * ha a Student inventárja már tele van.
	 *
	 * @param	st	Azon Student, aki megpróbálja felvenni az adott WetTableCloth -ot.
	 * */
	public boolean PickedUpStudent(Student st) {
		Logger.started(this, "PickedUpStudent", st);
		boolean isAdded = st.AddToInventory(this);
		Logger.finished(this, "PickedUpStudent", st);
		return isAdded;
	}

	/**
	 * Ezen metódus meghívásakor a paraméterként megkapott
	 * Instructor inventárjában elhelyezi magát a WetTableCloth
	 * és átállítja az owner változóját a paraméterként kapott Instructor -ra.
	 * Igaz értékkel fog visszatérni, ha sikeresen fel tudta venni
	 * az adott tárgyat az Instructor és hamissal, ha nem.
	 * Abben az esetben nem sikerülhet felvenni a tárgyat,
	 * ha az Instructor inventárja már tele van.
	 *
	 * @param	i	Azon Instructor, aki megpróbálja felvenni az adott WetTableCloth -ot.
	 * */
	public boolean PickedUpInstructor(Instructor i) {
		Logger.started(this, "PickedUpInstructor", i);
		boolean isAdded = i.AddToInventory(this);
		Logger.finished(this, "PickedUpInstructor", i);
		return isAdded;
	}

	/**
	 * Ezen metódus a Person Throw függvényéből hívódik meg.
	 * Kezeli az eldobást a WetTableCloth szemszögéből, törli magát a
	 * Person WetTableClothes listájából.
	 *
	 * @param p	Azon Person, aki eldobta az adott tárgyat.
	 * */
	public void Thrown(Person p) {
		Logger.started(this, "Thrown", p);
		p.RemoveWetTableCloth(this);
		Logger.finished(this, "Thrown", p);
	}

	/**
	 * Ezen metódus minden kör végén meghívásra kerül, amennyiben
	 * az adott WetTableCloth aktiválva van.
	 * A metódus eggyel csökkenti az effectDuration változót.
	 * */
	@Override
	public void Decrement() {
		Logger.started(this, "Decrement");
		if (isActivated && effectDuration > 0) effectDuration = effectDuration - 1;
		Logger.finished(this, "Decrement");
	}

	/**
	 * Ezen metódus abban az esetben hívódik meg, amikor
	 * egy Student használni szeretne egy nála lévő WetTableCloth -ot.
	 * A metódus aktiválja a WetTableCloth -ot, amire meghívták, abban az esetben,
	 * ha még nem volt aktiválva.
	 *
	 * @param s Az a Student, aki használni szeretné a WetTableCloth -ot.
	 * */
	public void UsedByStudent(Student s) {
		Logger.started(this, "UsedByStudent", s);
		Activate();
		if (isActivated) {
			s.AddWetTableCloth(this);
		}
		// TODO stun Instructors in Student's Room
		Logger.finished(this, "UsedByStudent", s);
	}


	/**
	 * Ezen metódus abban az esetben hívódik meg, amikor
	 * egy Instructor használni szeretne egy nála lévő WetTableCloth -ot.
	 * A metódus nem csinál semmit, nem fogja aktiválni a WetTableCloth -ot,
	 * mivel az Instructor nem használhatja azt.
	 *
	 * @param i Az a Instructor, aki használni szeretné a WetTableCloth -ot.
	 * */
	public void UsedByInstructor(Instructor i) {
		Logger.started(this, "UsedByInstructor", i);
		Logger.finished(this, "UsedByInstructor", i);
	}

	/**
	 * Ezen metódus annak a kiderítésére szolgál, hogy képes-e még megvédeni
	 * a Student -et a WetTableCloth.
	 * Igazat ad vissza, ha a WetTableCloth aktiválva van
	 * és még legalább egy körig tart a hatása.
	 * */
	@Override
	public boolean CanDefend() {
		Logger.started(this, "CanDefend");
		effectDuration = Reader.GetIntInput("Mennyi ideig hatásos még a WetTableCloth?");
		Logger.finished(this, "CanDefend");
		return isActivated && effectDuration > 0;
	}
}
