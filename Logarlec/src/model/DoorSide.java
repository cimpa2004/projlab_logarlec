package model;


import util.Logger;
import viewmodel.IVDoorSide;

import java.util.UUID;

/** */
public class DoorSide implements IVDoorSide {
	/**
	 * Az adott DoorSidet egyertelmuen azonositja
	 */
	protected String id;

	/**
	 * Azt jelöli, hogy az ajtó kinyitható-e erről azt oldalról.
	 * Ha nem, akkor semmilyen személy nem tud rajta átmenni ebből az irányból.
	 * */
	private boolean canBeOpened;

	/**
	 * Azt jelöli, hogy az ajtó látható-e erről az oldalról.
	 * Ha nem, akkor semmilyen személy nem tud rajta átmenni ebből az irányból.
	 * */
	private boolean isVisible;

	/**
	 * Az ajtó ezen oldlához kapcsolódó szoba. Ettől függ, hogy az ajtó éppen látható-e.
	 * */
	private Room room;

	/**
	 * Az ajtó másik oldala. Ez alapján vannak összekötve a szobák, a mozgás során ezáltal található
	 * meg az ajtó túloldalához tartozó szoba.
	 * */
	private DoorSide pair;

	public DoorSide(String id){
		this.id = id;
		isVisible = true;
	}

	public DoorSide(){
		this.id = UUID.randomUUID().toString();
		isVisible = true;
	}

	/**
	 * Az ajtó attribútumait (max kapacitás, mérgesgáz ideje, átkozottság, szomszédos szobák)
	 * másolja át a paraméterben megadott ajtóra. A szobák egyesülésénél/osztódásánál hívódik meg.
	 * */
	public void CloneAttributes(DoorSide d) {
		Logger.started(this, "CloneAttributes", d);
		this.canBeOpened = d.canBeOpened;
		this.isVisible = d.isVisible;
		this.room = d.room;
		Logger.finished(this, "CloneAttributes", d);
	}

	/**
	 * Összekapcsolja a paraméterben kapott ajtó-oldalt a sajátjával, egy teljes ajtót alkotva, azaz egymáson hívják
	 * meg a SetPair() függvényeket. A szobák egyesülésénél/osztódásánál hívódik meg.
	 * */
	public void ConnectDoors(DoorSide d) {
		Logger.started(this, "ConnectDoors", d);
		this.SetPair(d);
		d.SetPair(this);
		Logger.finished(this, "ConnectDoors", d);
	}

	/**
	 * Visszaadja, hogy az ajtó-oldal melyik másikhoz tartozik, azaz ami már a szomszéd szobából látszik.
	 * */
	public DoorSide GetPair() {
		Logger.started(this, "GetPair");
		Logger.finished(this, "GetPair");
		return pair;
	}

	/**
	 * Beállítja az ajtó-oldal párját. A szobák egyesülésénél/osztódásánál hívódik meg.
	 * */
	public void SetPair(DoorSide d) {
		Logger.started(this, "SetPair", d);
		pair = d;
		Logger.finished(this, "SetPair", d);
	}

	/**
	 * Visszaadja, hogy az ajtó oldala melyik szobához tartozik.
	 * */
	public Room GetRoom() {
		Logger.started(this, "GetRoom");
		Logger.finished(this, "GetRoom");
		return room;
	}

	/**
	 * Beállítja, hogy az ajtó oldala melyik szobához tartozzon.
	 * */
	public void SetRoom(Room r) {
		Logger.started(this, "SetRoom", r);
		room = r;
		if(r != null){
			r.AddDoor(this);
		}
		Logger.finished(this, "SetRoom", r);
	}

	/**
	 * Beállítja, hogy az ajtót ki lehessen-e nyitni, azaz zárt-e ebből az irányból.
	 * */
	public void SetCanBeOpened(boolean b) {
		Logger.started(this, "SetCanBeOpened", b);
		this.canBeOpened = b;
		Logger.finished(this, "SetCanBeOpened", b);
	}

	/**
	 * Beállítja, hogy az ajtó látható legyen. (Elátkozott szobákban)
	 * */
	public void SetIsVisible(boolean b) {
		Logger.started(this, "SetIsVisible", b);
		this.isVisible = b;
		Logger.finished(this, "SetIsVisible", b);
	}

	/**
	 * Visszaadja, hogy az ajtót ki lehet-e nyitni, azaz zárt-e ebből az irányból.
	 * */
	public boolean GetCanBeOpened() {
		Logger.started(this, "GetCanBeOpened");
		Logger.finished(this, "GetCanBeOpened");
		return this.canBeOpened;
	}

	/**
	 * Visszaadja, hogy az ajtó látható-e. (Elátkozott szobákban)
	 * */
	public boolean GetIsVisible() {
		Logger.started(this, "GetIsVisible");
		Logger.finished(this, "GetIsVisible");
		return this.isVisible;
	}


	public String GetID() {
		return id;
	}

	/**
	 *	Használható e az ajtó
	 * @return igaz, ha az ajtón valóban át lehet menni
	 */
	public boolean IsDoorUseable(){
		return (this.GetCanBeOpened() &&
				this.GetIsVisible()&&
				this.GetPair() != null&&
				this.GetPair().GetRoom() != null&&
				this.GetPair().GetRoom().GetMaxCapacity() > this.GetPair().GetRoom().GetCurrentCapacity());
	}

}
