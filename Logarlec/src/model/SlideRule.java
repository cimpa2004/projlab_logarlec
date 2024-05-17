package model;
import controller.Game;
import util.*;
import viewmodel.IVSlideRule;
import viewmodel.IVRoom;

import java.util.UUID;

/**
 * A SlideRule osztály a Logarléc tárgy reprezentációja.
 * Tárol egy referenciát a Game -re, amin keresztül, ha Student vette fel a tárgyat,
 * akkor véget tud vetni a játéknak.
 * Eltárolja továbbá, hogy az adott SlideRule -ról, hogy igazi-e vagy nem.
 * Felelőssége, hogy kezelje mit történik a tárggyal és a környezetével (game),
 * abban az esetben, ha a tárgyat fel akarja valaki venni, vagy eldobni.
 */
public class SlideRule extends Item implements IVSlideRule {
	/**
	 * A játék ami számon tartja a játék paramétereit.
	 * A logarléc képes így üzenni a játéknak ha megtalálta őt egy hallgató, hogy így véget ér a játék.
	 * */
	private Game game;

	/**
	 * Ez a boolean típusú változó eltárolja az adott SlideRule példányról, hogy igazi-e vagy hamis.
	 * */
	private boolean isFake;

	/**
	 * A SlideRule osztály konstruktora.
	 * Az id értékét a paraméterben kapott értékre állítja.
	 *
	 * @param id Az id változó értékét erre az értékre állítja.
	 * */
	public SlideRule(String id, Game game) {
		super(id);
		this.game = game;
	}

	/**
	 * A SlideRule osztály konstruktora.
	 * Az id-t beállítja egy random értékre.
	 */
	public SlideRule() {
		super(UUID.randomUUID().toString());
	}

	/**
	 * Visszaadja, hogy az adott SlideRule hamis-e.
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
	 * Ezen metódussal le lehet kérdezni, hogy egy SlideRule példány aktív-e.
	 *
	 * @return Mindig hamis, mert egy SlideRule -t nem lehet/kell aktiválni.
	 */
	@Override
	public boolean GetIsActive() {
		Logger.startedModel(this, "GetIsFake");
		Logger.finishedModel(this, "GetIsFake");
		return false;
	}

	/**
	 * Ezen metódussal le lehet kérdezni, hogy az
	 * adott Transistor  -nak melyik Transistor  a párja.
	 *
	 * @return Mindig null, mert a SlideRule nem Transistor.
	 */
	@Override
	public Transistor GetPair() {
		Logger.startedModel(this, "GetPair");
		Logger.finishedModel(this, "GetPair");
		return null;
	}

	/**
	 * 	Ezen metódus a paraméterként kapott értékre állítja a SlideRule isFake változóját.
	 *
	 * @param b Az isFake leendő értéke.
	 * */
	public void SetIsFake(boolean b){
		Logger.startedModel(this, "SetIsFake", b);
		isFake = b;
		Logger.finishedModel(this, "SetIsFake", b);
	}

	/**
	 * Ezen metódus referenciaként átveszi hogy melyik Student vette fel
	 * és megpróbálja elhelyezi magát a Student inventoryában.
	 * Meghívja a Student AddToInventory metódusát, aminek átadja a SlideRule -t.
	 * Ezen metódus visszatérési értéke megadja,
	 * hogy sikerült-e felvennie a SlideRule -t a Student -nek.
	 * Ha sikerült felvenni és nem hamis a SlideRule, akkor
	 * a hallgatók megnyerték a játékot és meghívódik az
	 * EndGame metódus.
	 *
	 * @param st A felvevő hallgató
	 * @return Igaz, ha sikerült felvenni és hamis ha nem.
	 */
	public boolean PickedUpStudent(Student st) {
		Logger.startedModel(this, "PickedUpStudent", st);
		boolean isAdded = st.AddToInventory(this);
		if(isAdded && !isFake) game.EndGame(true);
		Logger.finishedModel(this, "PickedUpStudent", st);
		return isAdded;
	}

	/**
	 * A logarlécet a paraméterben kapott Oktató venné fel, de mivel
	 * ez nem megengedett, így mindig hamis értékkel visszatér.
	 *
	 * @param i Az az Oktató, aki felvenné a logarlécet
	 * @return mindig hamis érték
	 * */
	public boolean PickedUpInstructor(Instructor i) {
		Logger.startedModel(this, "PickedUpInstructor", i);
		Logger.finishedModel(this, "PickedUpInstructor", i);
		return false;
	}

	/**
	 * Ezen metódus akkor hívódik meg, ha a Person el szeretné
	 * távolítani az inventoryából az adott SlideRule tárgyat.
	 * A metódus eltávolítja a SlideRule -t a Person inventoryából
	 * a Person RemoveFromInventory metódus meghívásával.
	 *
	 * @param p Az eldobó személy
	 */
	public void Thrown(Person p) {
		Logger.startedModel(this, "Thrown", p);
		p.RemoveFromInventory(this);
		Logger.finishedModel(this, "Thrown", p);
	}

	/**
	 * Egy SlideRule -t nem lehet használni, ezért ez egy üres metódus.
	 *
	 * @param s Azon Student, aki használná az adott tárgyat.
	 */
	@Override
	public void UsedByStudent(Student s) {
		Logger.startedModel(this, "UsedByStudent");
		Logger.finishedModel(this, "UsedByStudent");
	}

	/**
	 * Egy SlideRule -t nem lehet használni, ezért ez egy üres metódus.
	 *
	 * @param i Azon Instructor, aki használná az adott tárgyat.
	 */
	@Override
	public void UsedByInstructor(Instructor i) {
		Logger.startedModel(this, "UsedByInstructor");
		Logger.finishedModel(this, "UsedByInstructor");
	}

	@Override
	public IVRoom GetIVRoom() {
        Logger.startedModel(this, "GetIVRoom");
        Logger.finishedModel(this, "GetIVRoom");
        return this.room;
    }
}
