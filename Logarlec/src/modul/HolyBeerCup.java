package modul;

import util.Logger;
import util.Reader;

/**
 * A HolyBeerCup osztály a Szent Söröspohár reprezentációja.
 * A HolyBeerCup a tárgyak egyike, amelyeket a játékosok
 * a szobákban találhatnak. Ezeket a tárgyakat fel tudják venni, illetve eldobni.
 * A HolyBeerCup tárgyat lehet aktiválni és aktiválás után egy bizonyos számú körig
 * védelmet biztosít az aktiváló Student -nek az Instructor -okkal szemben.
 * A HolyBeerCup -ot Instructorok is fel tudják venni, illetve eldobni, de
 * használni nem tudják.
 * */
public class HolyBeerCup extends Item implements Usable, Defendable {

	/**
	 * Ez a változó eltárolja, hogy még hány körig képes a
	 * HolyBeerCup megvédeni a Studentet, akihez tartozik
	 * */
	private int effectDuration;

	/**
	 * Ez a változó eltárolja, hogy az adott HolyBeerCup aktiválva van-e vagy sem.
	 * */
	private boolean isActivated;

	/**
	 * Ezen metódus meghívásakor a HolyBeerCup, amire ezt a függvényt meghívták,
	 * aktivált állapotba kerül, azaz igazra állítódik az isActivated változója.
	 * */
	@Override
	public boolean Activate() {
		Logger.started(this, "Activate");
		isActivated = Reader.GetBooleanInput("Sikerült aktiválni a HolyBeerCupot?");
		Logger.finished(this, "Activate");
		return isActivated;
	}

	/**
	 * Ezen metódus minden kör végén meghívásra kerül, amennyiben
	 * az adott HolyBeerCup aktiválva van.
	 * A metódus eggyel csökkenti az effectDuration változót.
	 * */
	@Override
	public void Decrement() {
		Logger.started(this, "Decrement");
		if(effectDuration>0) effectDuration = effectDuration - 1;
		Logger.finished(this, "Decrement");
	}

	/**
	 * Ezen metódus meghívásakor a paraméterként megkapott
	 * Student inventárjában elhelyezi magát a HolyBeerCup
	 * és átállítja az owner változóját a paraméterként kapott Student -re.
	 * Igaz értékkel fog visszatérni, ha sikeresen fel tudta venni
	 * az adott tárgyat a Student és hamissal, ha nem.
	 * Abben az esetben nem sikerülhet felvenni a tárgyat,
	 * ha a Student inventárja már tele van.
	 *
	 * @param	st	Azon Student, aki megpróbálja felvenni az adott HolyBeerCup -ot.
	 * */
	public boolean PickedUpStudent(Student st) {
		Logger.started(this, "PickedUpStudent", st);
		boolean isAdded = st.AddToInventory(this);
		Logger.finished(this, "PickedUpStudent", st);
		return isAdded;
	}

	/**
	 * Ezen metódus meghívásakor a paraméterként megkapott
	 * Instructor inventárjában elhelyezi magát a HolyBeerCup
	 * és átállítja az owner változóját a paraméterként kapott Instructor -ra.
	 * Igaz értékkel fog visszatérni, ha sikeresen fel tudta venni
	 * az adott tárgyat az Instructor és hamissal, ha nem.
	 * Abben az esetben nem sikerülhet felvenni a tárgyat,
	 * ha az Instructor inventárja már tele van.
	 *
	 * @param	i	Azon Instructor, aki megpróbálja felvenni az adott HolyBeerCup -ot.
	 * */
	public boolean PickedUpInstructor(Instructor i) {
		Logger.started(this, "PickedUpInstructor", i);
		boolean isAdded = i.AddToInventory(this);
		Logger.finished(this, "PickedUpInstructor", i);
		return isAdded;
	}

	/**
	 * Ezen metódus a Person Throw függvényéből hívódik meg.
	 * Kezeli az eldobást a HolyBeerCup szemszögéből, törli magát a
	 * Person HolyBeerCups listájából.
	 *
	 * @param p	Azon Person, aki eldobta az adott tárgyat.
	 * */
	public void Thrown(Person p) {
		Logger.started(this, "Thrown", p);

		p.holyBeerCups.remove(this);

		Logger.finished(this, "Thrown", p);
	}

	/**
	 * Ezen metódus abban az esetben hívódik meg, amikor
	 * egy Student használni szeretne egy nála lévő HolyBeerCup -ot.
	 * A metódus aktiválja a HolyBeerCup -ot, amire meghívták, abban az esetben,
	 * ha még nem volt aktiválva.
	 *
	 * @param s Az a Student, aki használni szeretné a HolyBeerCup -ot.
	 * */
	public void UsedByStudent(Student s) {
		Logger.started(this, "UsedByStudent", s);
		Activate();
		if(isActivated){
			s.AddHolyBeerCup(this);
		}
		Logger.finished(this, "UsedByStudent", s);
	}

	/**
	 * Ezen metódus abban az esetben hívódik meg, amikor
	 * egy Instructor használni szeretne egy nála lévő HolyBeerCup -ot.
	 * A metódus nem csinál semmit, nem fogja aktiválni a HolyBeerCup -ot,
	 * mivel az Instructor nem használhatja azt.
	 *
	 * @param i Az a Instructor, aki használni szeretné a HolyBeerCup -ot.
	 * */
	public void UsedByInstructor(Instructor i) {
		Logger.started(this, "UsedByInstructor", i);
		Logger.finished(this, "UsedByInstructor", i);
	}

	/**
	 * Ezen metódus annak a kiderítésére szolgál, hogy képes-e még megvédeni
	 * a Student -et a HolyBeerCup.
	 * Igazat ad vissza, ha a HolyBeerCup aktiválva van
	 * és még legalább egy körig tart a hatása.
	 * */
	@Override
	public boolean CanDefend() {
		Logger.started(this, "CanDefend");
		Logger.finished(this, "CanDefend");
		effectDuration = Reader.GetIntInput("Mennyi ideig hatásos még a HolyBeerCup?");
		return isActivated && effectDuration > 0;
	}
}
