package model;

import util.Logger;
import viewmodel.IVRoom;
import viewmodel.IVAirFreshener;

import java.util.UUID;

/**
 * Az AirFreshener osztály a Légfrissítő tárgy reprezentációja.
 * Eltárolja egy AirFreshener -ról, hogy  aktív-e.
 * Felelőssége továbbá, hogy egy AirFreshener példány felvételekor, eldobásakor és használatakor
 * frissítse a AirFreshener változóit
 * és kezelje, hogy ezen cselekvések, hogy hatnak ki a AirFreshener környezetére (owner és room).
 */
public class AirFreshener extends Item implements Usable, IVAirFreshener {

    /**
     * Ez a boolean típusú változó tárolja az adott AirFreshener példányról,
     * hogy aktiválva van-e vagy nem.
     */
    private boolean isActivated;

    /**
     * Az AirFreshener osztály konstruktora.
     * Az isActivated értékét hamisra állítja.
     * Az id értékét a paraméterben kapott értékre állítja.
     *
     * @param id Az id változó értékét erre az értékre állítja.
     * */
    public AirFreshener(String id){
        super(id);
        isActivated = false;
    }

    /**
     * Az AirFreshener osztály konstruktora.
     * Az isActivated értékét hamisra állítja,
     * Az id-t beállítja egy random értékre.
     */
    public AirFreshener(){
        super(UUID.randomUUID().toString());
        isActivated = false;
    }

    /**
     * Visszaadja, hogy az adott AirFreshener hamis-e.
     * Egy AirFreshener nem lehet hamis, tehát
     * mindig hamisat fog visszaadni.
     *
     * @return Mindig hamis.
     * */
    @Override
    public boolean GetIsFake() {
        Logger.startedModel(this, "GetIsFake");
        Logger.finishedModel(this, "GetIsFake");
        return false;
    }

    /**
     * Ez a metódus, abban az esetben lesz meghívva ha egy AirFreshener tárgyat aktiválni szeretnénk.
     * Ha a tárgy már használva volt - azaz az isActivated már igazra lett állítva -
     * akkor a metódus hamissal tér vissza.
     * Ellenkező esetben igazra állítja az isActivated változót és igazzal tér vissza.
     *
     * @return Igaz ha sikerült aktiválni és hamis ha nem.
     * */
    @Override
    public boolean Activate() {
        Logger.startedModel(this, "Activate");
        Logger.finishedModel(this, "Activate");
        if(isActivated){
            return false;
        }else{
            isActivated = true;
            return  true;
        }
    }

    /**
     * 	Ezen metódus a paraméterként kapott értékre állítja az AirFreshener isActivated változóját.
     *
     * @param isActivated Az isActivated értékét erre az értékre állítja.
     * */
    public void SetIsActivated(boolean isActivated) {
        Logger.startedModel(this, "SetIsActivated", isActivated);
        this.isActivated = isActivated;
        Logger.finishedModel(this, "SetIsActivated", isActivated);
    }


    /**
     * Ezen metódussal le lehet kérdezni, hogy egy AirFreshener példány aktív-e.
     * @return Az isActivated változó értéke.
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
     * @return Mindig null, mert az AirFreshener nem Transistor.
     */
    @Override
    public Transistor GetPair() {
        return null;
    }

    /**
     * Ezen metódus referenciaként átveszi hogy melyik Student vette fel
     * és megpróbálja elhelyezi magát a Student inventoryában.
     * Meghívja a Student AddToInventory metódusát, aminek átadja a AirFreshenert.
     * Ezen metódus visszatérési értéke megadja,
     * hogy sikerült-e felvennie az AirFreshenert a Student -nek.
     *
     * @param st A felvevő hallgató
     * @return Igaz, ha sikerült felvenni és hamis ha nem.
     */
    @Override
    public boolean PickedUpStudent(Student st) {
        Logger.startedModel(this, "PickedUpStudent", st);
        boolean isAdded = st.AddToInventory(this);
        Logger.finishedModel(this, "PickedUpStudent", st);
        return isAdded;
    }

    /**
     * Ezen metódus referenciaként átveszi hogy melyik Instructor vette fel
     * és megpróbálja elhelyezi magát az Instructor inventoryában.
     * Meghívja az Instructor AddToInventory metódusát, aminek átadja a AirFreshenert.
     * Ezen metódus visszatérési értéke megadja,
     * hogy sikerült-e felvennie az AirFreshenert az Instructor -nak.
     *
     * @param i A felvevő oktató
     * @return Igaz, ha sikerült felvenni és hamis ha nem.
     */
    @Override
    public boolean PickedUpInstructor(Instructor i) {
        Logger.startedModel(this, "PickedUpInstructor", i);
        boolean isAdded = i.AddToInventory(this);
        Logger.finishedModel(this, "PickedUpInstructor", i);
        return isAdded;
    }

    /**
     * Ezen metódus akkor hívódik meg, ha a Person el szeretné
     * távolítani az inventoryából az adott AirFreshener tárgyat.
     * A metódus eltávolítja a AirFreshener -ot a Person inventoryából
     * a RemoveFromInventory metódus meghívásával.
     *
     * @param p Az eldobó személy
     */
    @Override
    public void Thrown(Person p) {
        Logger.startedModel(this, "Thrown", p);
        p.RemoveFromInventory(this);
        Logger.finishedModel(this, "Thrown", p);
    }

    /**
     *  Ezen metódus akkor hívódik meg, ha egy Student szeretne
     *  használni egy az inventoryában lévő AirFreshener -t.
     *  Ekkor először aktiválja, tehát meghívja az Activate metódust.
     *  Amennyiben az Activate metódus igaz értékkel tér vissza,
     *  akkor sikerült aktiválni és a Student szobájában semlegesíti a gázhatást,
     *  azaz a poisonDuration értékét 0-ra állítja.
     *
     * @param s Az AirFreshener-t használó hallgató
     */
    @Override
    public void UsedByStudent(Student s) {
        Logger.startedModel(this, "UsedByStudent", s);
        if(Activate()){
            s.GetRoom().SetPoisonDuration(0);
        }
        Logger.finishedModel(this, "UsedByStudent", s);
    }

    /**
     * Ezen metódus akkor hívódik meg, ha egy Instructor szeretne
     * használni egy az inventoryában lévő AirFreshener -t.
     * Ekkor először aktiválja, tehát meghívja az Activate metódust.
     * Amennyiben az Activate metódus igaz értékkel tér vissza,
     * akkor sikerült aktiválni és az Instructor szobájában semlegesíti a gázhatást,
     * azaz a poisonDuration értékét 0-ra állítja.
     *
     * @param i Az AirFreshenert-t használó oktató
     */
    @Override
    public void UsedByInstructor(Instructor i) {
        Logger.startedModel(this, "UsedByInstructor", i);
        if(Activate()){
            i.GetRoom().SetPoisonDuration(0);
        }
        Logger.finishedModel(this, "UsedByInstructor", i);
    }


    @Override
    public IVRoom GetIVRoom() {
        Logger.startedModel(this, "GetIVRoom");
        Logger.finishedModel(this, "GetIVRoom");
        return this.room;
    }
}

