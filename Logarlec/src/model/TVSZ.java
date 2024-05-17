package model;


import util.Logger;
import viewmodel.IVTVSZ;
import viewmodel.IVRoom;

import java.util.UUID;

/**
 * A TVSZ osztály a TVSZ tárgy reprezentációja.
 * Tárolja hogy hány alkalommal lehet még használni az adott TVSZ példányt és
 * ha ez eléri a 0-át nem teszi lehetővé a használatot.
 * Felelőssége továbbá, hogy egy TVSZ példány felvételekor és eldobásakor frissítse a TVSZ változóit
 * és kezelje, hogy ezen cselekvések, hogy hatnak ki a TVSZ környezetére (owner és room).
 * Továbbá meg tudja állapítani egy TVSZ -ről, hogy képes -e megvédeni egy Student-t egy Instructor -tól.
 * */
public class TVSZ extends Item implements Defendable, IVTVSZ {

	@Override
	public String GetID() {
		return id;
	}
	/**
	 *  Ez az integer typusú változó tárolja, hogy hány alkalommal tudja még a Student életét megmenteni.
	 * */
	private int usesLeft;

	/**
	 * Ez a boolean típusú változó eltárolja az adott TVSZ példányról, hogy igazi-e vagy hamis.
	 * */
	private boolean isFake;

	/**
	 * A TVSZ osztály konstruktora. A usesLeft értékét háromra állítja.
	 * Az id értékét a paraméterben kapott értékre állítja.
	 *
	 * @param id Az id változó értékét erre az értékre állítja.
	 * */
	public TVSZ(String id){
		super(id);
		usesLeft = 3;
	}

	/**
	 * A TVSZ osztály konstruktora.
	 * A usesLeft értékét háromra állítja.
	 * Az id-t beállítja egy random értékre.
	 */
	public TVSZ(){
		super(UUID.randomUUID().toString());
		usesLeft = 3;
	}

	/**
	 * Visszaadja, hogy az adott TVSZ hamis-e.
	 *
	 * @return Az isFake változó értéke.
	 * */
	@Override
	public boolean GetIsFake() {
		return isFake;
	}

	/**
	 * Ezen metódussal le lehet kérdezni, hogy egy TVSZ példány aktív-e.
	 *
	 * @return Mindig hamis, mert egy TVSZ -t nem lehet/kell aktiválni.
	 */
	@Override
	public boolean GetIsActive() {
		return false;
	}

	/**
	 * Ezen metódussal le lehet kérdezni, hogy az
	 * adott Transistor  -nak melyik Transistor  a párja.
	 *
	 * @return Mindig null, mert a TVSZ nem Transistor.
	 */
	@Override
	public Transistor GetPair() {
		return null;
	}

	/**
	 * 	Ezen metódus a paraméterként kapott értékre állítja a TVSZ isFake változóját.
	 *
	 * @param b Az isFake leendő értéke.
	 * */
	public void SetIsFake(boolean b){
		isFake = b;
	}

	/**
	 * Ezen metódus akkor kerül meghívásra, ha a TVSZ megmenti a
	 * Student -et egy Instructor -tól.
	 * A metódus eggyel csökkenti a usesLeft változót, ha az még nullánál nagyobb.
	 * */
	@Override
	public void Decrement() {
		Logger.started(this, "Decrement");
		if (usesLeft > 0) usesLeft = usesLeft - 1;
		Logger.finished(this, "Decrement");
	}

	/**
	 * Visszaadja, hogy az adott TVSZ mennyiszer tudja még a hallgatót megvédeni.
	 *
	 * @return a usesLeft változó értéke
	 */
	@Override
	public int GetDurability() {
		return usesLeft;
	}

	/**
	 * 	Ezen metódus a paraméterként kapott értékre állítja a TVSZ usesLeft változóját.
	 *
	 * @param durability A usesLeft értékét erre az értékre állítja.
	 * */
	@Override
	public void SetDurability(int durability) {
		this.usesLeft = durability;
	}

	/**
	 * Ezen metódus referenciaként átveszi hogy melyik Student vette fel
	 * és megpróbálja elhelyezi magát a Student inventoryában.
	 * Meghívja a Student AddToInventory metódusát, aminek átadja a TVSZt.
	 * Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie a TVSZt a Student -nek.
	 *
	 * @param st A felvevő hallgató
	 * @return Igaz, ha sikerült felvenni és hamis ha nem.
	 */
	public boolean PickedUpStudent(Student st) {
		Logger.started(this, "PickedUpStudent", st);
		boolean isAdded = st.AddToInventory(this);
		if (isAdded && ivItemUpdate != null){
			ivItemUpdate.PickedUpUpdate();
		}
		if(isAdded && usesLeft > 0){
			st.AddTVSZ(this);
		}
		Logger.finished(this, "PickedUpStudent", st);
		return isAdded;
	}

	/**
	 * Ezen metódus referenciaként átveszi hogy melyik Instructor vette fel
	 * és megpróbálja elhelyezi magát az Instructor inventoryában.
	 * Meghívja az Instructor AddToInventory metódusát, aminek átadja a TVSZt.
	 * Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie a TVSZt az Instructor -nak.
	 *
	 * @param i A felvevő oktató
	 * @return Igaz, ha sikerült felvenni és hamis ha nem.
	 */
	public boolean PickedUpInstructor(Instructor i) {
		Logger.started(this, "PickedUpInstructor", i);
		boolean isAdded = i.AddToInventory(this);
		if (isAdded && ivItemUpdate != null){
			ivItemUpdate.PickedUpUpdate();
		}
		Logger.finished(this, "PickedUpInstructor", i);
		return isAdded;
	}

	/**
	 *  Ezen metódus akkor hívódik meg, ha a Person el szeretné
	 *  távolítani az inventoryából az adott TVSZ tárgyat.
	 *  A metódus eltávolítja a TVSZ -t a Person inventoryából és a Person tvszslistájából
	 *  a RemoveFromInventory és a RemoveTVSZ metódusok meghívásával.
	 *
	 * @param p	Azon Person, aki eldobta az adott tárgyat.
	 * */
	public void Thrown(Person p) {
		Logger.started(this, "Thrown", p);
		p.RemoveTVSZ(this);
		p.RemoveFromInventory(this);
		if(ivItemUpdate != null){
			ivItemUpdate.ThrownUpdate();
		}
		Logger.finished(this, "Thrown", p);
	}

	/**
	 * Egy SlideRule -t nem lehet használni, ezért ez egy üres metódus.
	 *
	 * @param s Azon Student, aki használná az adott tárgyat.
	 */
	@Override
	public void UsedByStudent(Student s) {}

	/**
	 * Egy SlideRule -t nem lehet használni, ezért ez egy üres metódus.
	 *
	 * @param i Azon Instructor, aki használná az adott tárgyat.
	 */
	@Override
	public void UsedByInstructor(Instructor i) {}

	/**
	 * Ez a metódus megállapítja, hogy az adott TVSZ
	 * képes-e még megvédeni a Person -t, akihez tartozik egy Instructor-tól.
	 * Igazzal tér vissza, ha a TVSZ usesLeft változója nagyobb mint nulla és a TVSZ nem hamis.
	 * Minden más esetben hamissal tér vissza.
	 *
	 * @return Igaz ha még képes védelemre és hamis ha nem.
	 * */
	@Override
	public boolean CanDefend() {
		Logger.started(this, "CanDefend");
		Logger.finished(this, "CanDefend");
		if(isFake) return false;
		return usesLeft > 0;
	}
	
	@Override
	public IVRoom GetIVRoom() {
		return room;
	}
}
