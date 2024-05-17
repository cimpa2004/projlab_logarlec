package model;

import util.Logger;
import viewmodel.IVFFP2Mask;
import viewmodel.IVRoom;

import java.util.UUID;

/**
 * Az FFP2Mask az FFP2 Maszk tárgy reprezentációja. E
 * ltárolja egy FFP2 Maskról, hogy hány körig használható még,
 * hogy aktív-e és hogy igazi vagy hamis maszk-e.
 * Felelőssége továbbá, hogy egy FFP2Mask példány felvételekor,
 * eldobásakor és használatakor frissítse
 * az FFP2Mask változóit és kezelje, hogy ezen cselekvések,
 * hogy hatnak ki az FFP2Mask környezetére (owner és room).
 * Továbbá meg tudja állapítani egy FFP2Mask -ról,
 * hogy képes -e megvédeni egy Person -t egy gázos szobától.
 * {@inheritDoc}
 */
public class FFP2Mask extends Item implements Usable, Defendable, IVFFP2Mask {
	/**
	 * Ez az integer típusú változó eltárolja, hogy az adott FFP2Mask
	 * még hány körig használható, azaz hány körig tudja még a Person -t
	 * megvédeni egy gázos szobában az elájulástól.
	 */
	private int durability;

	/**
	 * Ez a boolean típusú változó tárolja az adott FFP2Mask példányról,
	 * hogy aktiválva van-e vagy nem.
	 */
	private boolean isActivated;

	/**
	 * Ezen változó tárolja, hogy az adott FFP2Mask igazi-e  vagy hamis.
	 * */
	private boolean isFake;

	/**
	 * Az FFP2Mask osztály konstruktora. A durability értékét háromra, az isActivated értékét hamisra állítja.
	 * Az id értékét a paraméterben kapott értékre állítja.
	 *
	 * @param id Az id változó értékét erre az értékre állítja.
	 * */
	public FFP2Mask(String id){
		super(id);
		isActivated = false;
		durability = 3;
	}

	/**
	 * Az FFP2Mask osztály konstruktora.
	 * Az isActivated értékét hamisra állítja,
	 * a durability értékét pedig háromra.
	 * Az id-t beállítja egy random értékre.
	 */
	public FFP2Mask(){
		super(UUID.randomUUID().toString());
		isActivated = false;
		durability = 3;
	}

	/**
	 * Visszaadja, hogy az adott FFP2Mask hamis-e.
	 *
	 * @return Az isFake változó értéke.
	 * */
	@Override
	public boolean GetIsFake() {
        Logger.startedModel(this, "GetIsFake");
        Logger.finishedModel(this, "GetIsFake");
        return isFake;
    }

	/**
	 * 	Ezen metódus a paraméterként kapott értékre állítja a FFP2Mask isFake változóját.
	 *
	 * @param b Az isFake leendő értéke.
	 * */
	public void SetIsFake(boolean b){
		Logger.startedModel(this, "SetIsFake", b);
		isFake = b;
		Logger.finishedModel(this, "SetIsFake", b);
	}

	/**
	 * 	Ezen metódus a paraméterként kapott értékre állítja a FFP2Mask durability változóját.
	 *
	 * @param durability A durability értékét erre az értékre állítja.
	 * */
	public void SetDurability(int durability) {
		Logger.startedModel(this, "SetDurability", durability);
		this.durability = durability;
		Logger.finishedModel(this, "SetDurability", durability);
	}

	/**
	 * 	Ezen metódus a paraméterként kapott értékre állítja a FFP2Mask isActivated változóját.
	 *
	 * @param isActivated Az isActivated értékét erre az értékre állítja.
	 * */
	public void SetIsActivated(boolean isActivated) {
		Logger.startedModel(this, "SetIsActivated", isActivated);
		this.isActivated = isActivated;
		Logger.finishedModel(this, "SetIsActivated", isActivated);
	}

	/**
	 * Ez a metódus, abban az esetben lesz meghívva ha egy FFP2Mask tárgyat aktiválni szeretnénk.
	 * Ha egy tárgy hamis, azaz az isFake változó igaz, akkor nem lehet aktiválni és a
	 * metódus hamis értékkel tér vissza. Ha a tárgy még nem volt használva,
	 * azaz az isActivated hamis és a durability nullánál nagyobb, akkor az isActivated értékét igazra állítja
	 * és igazzal tér vissza. Minden más esetben hamissal tér vissza.
	 *
	 * @return Igaz ha sikerült aktiválni és hamis ha nem.
	 */
	public boolean Activate() {
		Logger.startedModel(this, "Activate");
		Logger.finishedModel(this, "Activate");

		if(isFake) return false;

		if(!isActivated && durability > 0){
			isActivated = true;
			return true;
		}else {
			return false;
		}
	}

	/**
	 * Ezen metódussal le lehet kérdezni, hogy egy FFP2Mask példány aktív-e.
	 *
	 * @return az isActivated változó értéke.
	 */
	@Override
	public boolean GetIsActive() {
        Logger.startedModel(this, "GetIsActive");
        Logger.finishedModel(this, "GetIsActive");
        return isActivated;
    }

	/**
	 * Ezen metódussal le lehet kérdezni, hogy az
	 * adott Transistor  -nak melyik Transistor  a párja.
	 *
	 * @return Mindig null, mert az FFP2Mask nem Transistor.
	 */
	@Override
	public Transistor GetPair() {
		Logger.startedModel(this, "GetPair");
		Logger.finishedModel(this, "GetPair");
		return null;
	}

	/**
	 * Ez a metódus az adott FFP2Mask durability változójának értékét csökkenti eggyel,
	 * akkor ha az az FFP2Mask aktiválva volt, azaz az isActivated igaz,
	 * és még lehet használni azaz a durability nullánál nagyobb.
	 * Ha aktiválva van, de a durability már nullára csökkent,
	 * akkor el lett használva az FFP2Mask és az isActivated értékét hamisra állítja.
	 */
	public void Decrement() {
		Logger.startedModel(this, "Decrement");
		if(isActivated) {
			if (durability > 0) durability = durability - 1;
			else isActivated = false;
		}
		Logger.finishedModel(this, "Decrement");
	}

	/**
	 * Visszaadja, hogy az adott FFP2Mask mennyiszer
	 * tud még megvédeni egy gázos szobától egy Persont.
	 *
	 * @return a durability változó értéke
	 */
	@Override
	public int GetDurability() {
		Logger.startedModel(this, "GetDurability");
		Logger.finishedModel(this, "GetDurability");
		return durability;
	}

	/**
	 * Ezen metódus referenciaként átveszi hogy melyik Student vette fel
	 * és megpróbálja elhelyezi magát a Student inventoryában.
	 * Meghívja a Student AddToInventory metódusát, aminek átadja az FFP2Maskot.
	 * Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie az FFP2Maskot a Student -nek.
	 *
	 * @param st A felvevő hallgató
	 * @return Igaz, ha sikerült felvenni és hamis ha nem.
	 */
	public boolean PickedUpStudent(Student st) {
		Logger.startedModel(this, "PickedUpStudent", st);
		boolean isAdded = st.AddToInventory(this);
		if (isAdded && ivItemUpdate != null){
			ivItemUpdate.PickedUpUpdate();
		}
		Logger.finishedModel(this, "PickedUpStudent", st);
		return isAdded;
	}

	/**
	 * Ezen metódus referenciaként átveszi hogy melyik Instructor vette fel
	 * és megpróbálja elhelyezi magát az Instructor inventoryában.
	 * Meghívja az Instructor AddToInventory metódusát, aminek átadja az FFP2Maskot.
	 * Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie az FFP2Maskot az Instructor -nak.
	 *
	 * @param i A felvevő oktató
	 * @return Igaz, ha sikerült felvenni és hamis ha nem.
	 */
	public boolean PickedUpInstructor(Instructor i) {
		Logger.startedModel(this, "PickedUpInstructor", i);
		boolean isAdded = i.AddToInventory(this);
		if (isAdded && ivItemUpdate != null){
			ivItemUpdate.PickedUpUpdate();
		}
		// ha az oktato felvesz egy FFP2Maskot akkor egybol aktivalja
		if (isAdded) {
			Activate();
			if (isActivated) i.AddFFP2Mask(this);
		}
		Logger.finishedModel(this, "PickedUpInstructor", i);
		return isAdded;
	}

	/**
	 * Ezen metódus akkor hívódik meg, ha a Person el szeretné
	 * távolítani az inventoryából az adott FFP2Mask tárgyat.
	 * A metódus eltávolítja a FFP2Mask -ot a Person inventoryából és az ffp2Masks listából
	 * a RemoveFromInventory és a RemoveFFP2Mask metódusok meghívásával.
	 *
	 * @param p Az eldobó személy
	 */
	public void Thrown(Person p) {
		Logger.startedModel(this, "Thrown", p);
		p.RemoveFFP2Mask(this);
		p.RemoveFromInventory(this);
		if(ivItemUpdate != null){
			ivItemUpdate.ThrownUpdate();
		}
		Logger.finishedModel(this, "Thrown", p);
	}

	/**
	 *  Ezen metódus akkor hívódik meg, ha egy Student szeretne használni
	 *  egy az inventoryában lévő FFP2Mask -ot.
	 *  Ekkor először aktiválja, tehát meghívja az Activate metódust.
	 *  Amennyiben az Activate metódus igaz értékkel tér vissza, akkor sikerült aktiválni.
	 *  Ekkor hozzáadja az adott FFP2Mask -ot a Student ffp2Masks listájához.
	 *
	 * @param s Az FFP2-t használó hallgató
	 */
	public void UsedByStudent(Student s) {
		Logger.startedModel(this, "UsedByStudent", s);
		Activate();
		if (isActivated) s.AddFFP2Mask(this);
		Logger.finishedModel(this, "UsedByStudent", s);
	}

	/**
	 *  Ezen metódus akkor hívódik meg, ha egy Instructor szeretne használni
	 *  egy az inventoryában lévő FFP2Mask -ot.
	 *  Ekkor először aktiválja, tehát meghívja az Activate metódust.
	 *  Amennyiben az Activate metódus igaz értékkel tér vissza, akkor sikerült aktiválni.
	 *  Ekkor hozzáadja az adott FFP2Mask -ot az Instructor ffp2Masks listájához.
	 *
	 * @param i Az FFP2-t használó oktató
	 */
	public void UsedByInstructor(Instructor i) {
		Logger.startedModel(this, "UsedByInstructor", i);
		Activate();
		if (isActivated) i.AddFFP2Mask(this);
		Logger.finishedModel(this, "UsedByInstructor", i);
	}

	/**
	 * Ez a metódus megállapítja, hogy az adott FFP2Mask képes-e még megvédeni a Person -t,
	 * akihez tartozik egy gázos szobától. Abban az esetben tudja,
	 * ha aktiválva van és a durability változója nagyobb nullánál.
	 *
	 * @return Ha képes megvédeni igazzal tér vissza, ellenkező esetben hamissal.
	 */
	@Override
	public boolean CanDefend() {
		Logger.startedModel(this, "CanDefend");
		Logger.finishedModel(this, "CanDefend");
		if(isFake) return false;
		return isActivated && durability > 0;
	}

	@Override
	public IVRoom GetIVRoom() {
        Logger.startedModel(this, "GetIVRoom");
        Logger.finishedModel(this, "GetIVRoom");
        return this.room;
    }

}

