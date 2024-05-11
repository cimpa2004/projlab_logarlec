package model;


import util.Logger;

import java.util.UUID;

/**
 * A WetTableCloth osztály a Nedves Táblatörlő Rongy tárgy reprezentációja.
 * Eltárolja egy WetTableCloth -ról, hogy  aktív-e és hány körig aktív még.
 * Felelőssége továbbá, hogy egy WetTableCloth példány felvételekor, eldobásakor és használatakor
 * frissítse a WetTableCloth változóit és kezelje, hogy ezen cselekvések,
 * hogy hatnak ki a WetTableCloth környezetére (owner és room).
 * Továbbá meg tudja állapítani egy WetTableCloth -ról, hogy képes -e megvédeni egy Student-t egy Instructor -tól.
 * */
public class WetTableCloth extends Item implements Usable, Defendable {

	/**
	 * Ez az integer típusú változó eltárolja, hogy még hány körig képes a
	 * WetTableCloth megvédeni a Studentet, akihez tartozik
	 * */
	private int effectDuration;

	/**
	 * Ez a boolean típusú változó tárolja az adott WetTableCloth példányról,
	 * hogy aktiválva van-e vagy nem.
	 */
	private boolean isActivated;

	/**
	 * A WetTableCloth osztály konstruktora.
	 * Az isActivated értékét hamisra állítja.
	 * Az id értékét a paraméterként kapott értékre állítja.
	 * Az effetcDuration értékét háromra állítja.
	 *
	 * @param id Az id változó értékét erre az értékre állítja.
	 */
	public WetTableCloth(String id){
		super(id);
		isActivated = false;
		effectDuration = 3;
	}

	/**
	 * A WetTableCloth osztály konstruktora.
	 * Az isActivated értékét hamisra állítja.
	 * Az effetcDuration értékét háromra állítja.
	 * Az id-t beállítja egy random értékre.
	 */
	public WetTableCloth(){
		super(UUID.randomUUID().toString());
		isActivated = false;
		effectDuration = 3;
	}

	/**
	 * Visszaadja, hogy az adott WetTableCloth hamis-e.
	 * Egy WetTableCloth nem lehet hamis, tehát
	 * mindig hamisat fog visszaadni.
	 *
	 * @return Mindig hamis.
	 * */
	@Override
	public boolean GetIsFake() {
		return false;
	}

	/**
	 * Ez a metódus, abban az esetben lesz meghívva ha egy HolyBeerCup tárgyat aktiválni szeretnénk.
	 * Ha a tárgy már használva volt - azaz az isActivated már igazra lett állítva -
	 * akkor a metódus hamissal tér vissza.
	 * Ellenkező esetben igazra állítja az isActivated változót és igazzal tér vissza.
	 *
	 * @return Igaz ha sikerült aktiválni és hamis ha nem.
	 * */
	public boolean Activate() {
		Logger.started(this, "Activate");
		Logger.finished(this, "Activate");
		if(isActivated){
			return false;
		}else{
			isActivated = true;
			return true;
		}
	}

	/**
	 * Ezen metódussal le lehet kérdezni, hogy egy WetTableCloth példány aktív-e.
	 *
	 * @return az isActivated változó értéke.
	 */
	@Override
	public boolean GetIsActive() {
		return isActivated;
	}

	/**
	 * Ezen metódussal le lehet kérdezni, hogy az
	 * adott Transistor  -nak melyik Transistor  a párja.
	 *
	 * @return Mindig null, mert a WetTableCloth nem Transistor.
	 */
	@Override
	public Transistor GetPair() {
		return null;
	}

	/**
	 *  Ez a metódus az adott WetTableCloth effectDuration változójának értékét csökkenti eggyel,
	 *  akkor ha az a WetTableCloth aktiválva volt, azaz az isActivated igaz,
	 *  és még tud védelmet biztosítani azaz az effectDuration nullánál nagyobb.
	 *  Ha aktiválva van, de az effectDuration már nullára csökkent,
	 *  akkor el lett használva a WetTableCloth és az isActivated értékét hamisra állítja.
	 * */
	@Override
	public void Decrement() {
		Logger.started(this, "Decrement");
		if(isActivated){
			if(effectDuration>0) effectDuration = effectDuration - 1;
			else isActivated = false;
		}
		Logger.finished(this, "Decrement");
	}

	/**
	 * Visszaadja, hogy az adott WetTableCloth hány körig hatásos még.
	 *
	 * @return Az effectDuration változó értéke.
	 */
	@Override
	public int GetDurability() {
		return effectDuration;
	}

	/**
	 * Az effectDuration értékét a paraméterben kapott értékre állítja.
	 *
	 * @param durability Erre az értékre lesz állítva az effectDuration változó.
	 */
	@Override
	public void SetDurability(int durability) {
		this.effectDuration = durability;
	}

	/**
	 * Ezen metódus referenciaként átveszi hogy melyik Student vette fel
	 * és megpróbálja elhelyezi magát a Student inventoryában.
	 * Meghívja a Student AddToInventory metódusát, aminek átadja a WetTableClothot.
	 * Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie a WetTableClothot a Student -nek.
	 *
	 * @param st A felvevő hallgató
	 * @return Igaz, ha sikerült felvenni és hamis ha nem.
	 */
	public boolean PickedUpStudent(Student st) {
		Logger.started(this, "PickedUpStudent", st);
		boolean isAdded = st.AddToInventory(this);
		Logger.finished(this, "PickedUpStudent", st);
		return isAdded;
	}

	/**
	 * Ezen metódus referenciaként átveszi hogy melyik Instructor vette fel
	 * és megpróbálja elhelyezi magát az Instructor inventoryában.
	 * Meghívja az Instructor AddToInventory metódusát, aminek átadja a WetTableClothot.
	 * Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie a WetTableClothot az Instructor -nak.
	 *
	 * @param i A felvevő oktató
	 * @return Igaz, ha sikerült felvenni és hamis ha nem.
	 */
	public boolean PickedUpInstructor(Instructor i) {
		Logger.started(this, "PickedUpInstructor", i);
		boolean isAdded = i.AddToInventory(this);
		Logger.finished(this, "PickedUpInstructor", i);
		return isAdded;
	}

	/**
	 * 	Ezen metódus a paraméterként kapott értékre állítja a WetTableCloth isActivated változóját.
	 *
	 * @param isActivated Az isActivated értékét erre az értékre állítja.
	 * */
	public void SetIsActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	/**
	 *  Ezen metódus akkor hívódik meg, ha a Person el szeretné
	 *  távolítani az inventoryából az adott WetTableCloth tárgyat.
	 *  A metódus eltávolítja a WetTableCloth -ot a Person inventoryából
	 *  és a Person wetTableClothes listájából
	 *  a RemoveFromInventory és a RemoveWetTableCloth  metódusok meghívásával.
	 *
	 * @param p	Azon Person, aki eldobta az adott tárgyat.
	 * */
	public void Thrown(Person p) {
		Logger.started(this, "Thrown", p);
		p.RemoveFromInventory(this);
		p.RemoveWetTableCloth(this);
		Logger.finished(this, "Thrown", p);
	}

	/**
	 *  Ezen metódus akkor hívódik meg, ha egy Student szeretne
	 *  használni egy az inventoryában lévő WetTableCloth -ot.
	 *  Ekkor először aktiválja, tehát meghívja az Activate metódust.
	 *  Amennyiben az Activate metódus igaz értékkel tér vissza, akkor sikerült aktiválni.
	 *  Ekkor hozzáadja az adott WetTableCloth -ot a Student wetTableClothes listájához .
	 *
	 * @param s Az a Student, aki használni szeretné a WetTableCloth -ot.
	 * */
	public void UsedByStudent(Student s) {
		Logger.started(this, "UsedByStudent", s);
		Activate();
		if (isActivated) {
			s.AddWetTableCloth(this);
		}
		Logger.finished(this, "UsedByStudent", s);
	}


	/**
	 * Ezen metódus akkor hívódik meg, ha egy Instructor szeretne
	 * használni egy az inventoryában lévő WetTableCloth -ot.
	 * Instructor nem képes a WetTableCloth használatára, ezért e metódus egyből hamissal tér vissza.
	 *
	 * @param i Az a Instructor, aki használni szeretné a WetTableCloth -ot.
	 * */
	public void UsedByInstructor(Instructor i) {
		Logger.started(this, "UsedByInstructor", i);
		Logger.finished(this, "UsedByInstructor", i);
	}

	/**
	 * Ez a metódus megállapítja, hogy az adott WetTableCloth képes-e még megvédeni a Person -t,
	 * akihez tartozik egy Instructor-tól. Abban az esetben tudja,
	 * ha aktiválva van és a effectDuration változója nagyobb nullánál.
	 * Ha képes megvédeni igazzal tér vissza, ellenkező esetben hamissal.
	 *
	 * @return Igaz ha képes megvédeni és hamis ha nem.
	 * */
	@Override
	public boolean CanDefend() {
		Logger.started(this, "CanDefend");
		Logger.finished(this, "CanDefend");
		return isActivated && effectDuration > 0;
	}
}
