package modul;


import util.Logger;
import util.Reader;

import java.util.UUID;

/**
 * A TVSZ osztály a TVSZ reprezentációja.
 * A TVSZ a tárgyak egyike, amelyeket a játékosok
 * a szobákban találhatnak. Ezeket a tárgyakat fel tudják venni, illetve eldobni.
 * A HolyBeerCup -ot Instructorok is fel tudják venni, illetve eldobni.
 * A TVSZ a Student -ek kezében képes megvédeni őket az Instructor -októl bizonyos számú alkalommal.
 * */
public class TVSZ extends Item implements Defendable{

	/**
	 * Ezen változó tárolja, hogy hányszor tudja még megmenteni a Student -et.
	 * */
	private int usesLeft;

	/**
	 * Ezen változó tárolja, hogy az adott TVSZ igazi-e.
	 * */
	private boolean isFake;

	/**
	 * A TVSZ osztály konstruktora, amiben meg lehet adni, hogy hányszor
	 * legyen képes megvédeni a Student -et.
	 * */
	public TVSZ(String id){
		super(id);
		usesLeft = 3;
	}

	public TVSZ(){
		super(UUID.randomUUID().toString());
		usesLeft = 3;
	}

	@Override
	public boolean GetIsFake() {
		return isFake;
	}

	public void SetIsFake(boolean b){
		isFake = b;
	}

	/**
	 * Ezen metódus akkor kerül meghívásra, ha a TVSZ megmenti a
	 * Student -et egy Instructor -tól.
	 * A metódus eggyel csökkenti a usesLeft változót.
	 * */
	@Override
	public void Decrement() {
		Logger.started(this, "Decrement");
		if (usesLeft > 0) usesLeft = usesLeft - 1;
		Logger.finished(this, "Decrement");
	}

	@Override
	public int GetDurability() {
		return usesLeft;
	}

	/**
	 * Ezen metódus meghívásakor a paraméterként megkapott
	 * Student inventárjában elhelyezi magát a TVSZ
	 * és átállítja az owner változóját a paraméterként kapott Student -re.
	 * Igaz értékkel fog visszatérni, ha sikeresen fel tudta venni
	 * az adott tárgyat a Student és hamissal, ha nem.
	 * Abben az esetben nem sikerülhet felvenni a tárgyat,
	 * ha a Student inventárja már tele van.
	 *
	 * @param	st	Azon Student, aki megpróbálja felvenni az adott TVSZ -t.
	 * */
	public boolean PickedUpStudent(Student st) {
		Logger.started(this, "PickedUpStudent", st);
		boolean isAdded = st.AddToInventory(this);
		if(isAdded && usesLeft > 0){
			st.AddTVSZ(this);
		}
		Logger.finished(this, "PickedUpStudent", st);
		return isAdded;
	}

	/**
	 * Ezen metódus meghívásakor a paraméterként megkapott
	 * Instructor inventárjában elhelyezi magát a TVSZ
	 * és átállítja az owner változóját a paraméterként kapott Instructor -ra.
	 * Igaz értékkel fog visszatérni, ha sikeresen fel tudta venni
	 * az adott tárgyat az Instructor és hamissal, ha nem.
	 * Abben az esetben nem sikerülhet felvenni a tárgyat,
	 * ha az Instructor inventárja már tele van.
	 *
	 * @param	i	Azon Instructor, aki megpróbálja felvenni az adott TVSZ -ot.
	 * */
	public boolean PickedUpInstructor(Instructor i) {
		Logger.started(this, "PickedUpInstructor", i);
		boolean isAdded = i.AddToInventory(this);
		Logger.finished(this, "PickedUpInstructor", i);
		return isAdded;
	}

	/**
	 * Ezen metódus a Person Throw függvényéből hívódik meg.
	 * Kezeli az eldobást a TVSZ szemszögéből, törli magát a
	 * Person tvszs listájából.
	 *
	 * @param p	Azon Person, aki eldobta az adott tárgyat.
	 * */
	public void Thrown(Person p) {
		Logger.started(this, "Thrown", p);
		p.RemoveTVSZ(this);
		p.RemoveFromInventory(this);
		Logger.finished(this, "Thrown", p);
	}

	/**
	 * Ezen metódus annak a kiderítésére szolgál, hogy képes-e még megvédeni
	 * a Student -et a TVSZ.
	 * Igazat ad vissza, ha a TVSZ -nek a usesLeft változója legalább 1.
	 * */
	@Override
	public boolean CanDefend() {
		Logger.started(this, "CanDefend");
		Logger.finished(this, "CanDefend");
		if(isFake) return false;
		return usesLeft > 0;
	}
}
