package modul;



import util.Logger;
import util.Reader;

import java.util.ArrayList;
import java.util.List;

/**
 * Ez egy abstract osztály ami játékban szereplő személyeket reprezentálja általánosan és 
 * összefoglalja azokat a funkciókat, attribútumokat amiket a Hallgatók és Oktatók is hordoznak.
*/
public abstract class Person implements IPerson{
	/**
	 * Hz az adott Person egy gázos szobában tartózkodik, akkor ez a változó mutatja, hogy
	 * el van kábulva. 
	*/
	protected boolean isFainted;
	
	/**
	 * Ez a változó mutatja, hogy az adott Personnak jelenleg aktív egy a köre. Amikor a Person
	 * köre megkezdődik, akor ez a változó true lesz, amikor vége a köre, akkor false. 
	*/
	protected boolean activeTurn;
	
	/** 
	 * Ez mutatja, hogy az adott Person rendelkezik-e WetTableCloth-tal. Ennek segítségével bizonyos
	 * helyzetekben ez hatással lehet az adott személyre (megvédheti), vagy más személyre a szobában
	 * ahol a Person tartózkodik. 
	*/
	protected boolean hasWetTableCloth;
	
	/** 
	 * Ez mutatja, hogy az adott Person rendelkezik-e TVSZ-szel. Ezzel bizonyos helyzetekben hatással lehet
	 * lehet a bizonyos akciókra, példáúl megvédheti az adott Person-t.
	*/
	protected boolean hasTVSZ;
	
	/** 
	 * Azt mutatja, hogy az adott Person rendelkezik-e FFP2Mask-kal. Ez megvédheti a kábulástól 
	 * olyan helyzetekben, amikor gázos szobába lép, vagy a szobában ahol tartózkodik gázos lesz.
	*/
	protected boolean hasFFP2Mask;
	
	/** 
	 * Mutatja, hogy az adott Person rendelkezik-e HolyBeerCup-pal. Ez az Item megvédheti az
	 * adott Person-t bizonyos helyzetekben.
	*/
	public boolean hasHolyBeerCup;
	
	/** 
	 * Item-ek listája. Ez a lista tartalmazza az adott Person-nél lévő Itemeket. Egy Personnél
	 * maximum csak 5 Item lehet egyszerre.
	*/
	protected List<Item> inventory;

	/** 
	 * Ez mutatja, hogy az adott Person épp melyik szobába helyezkedik el.
	*/
	protected Room room;
	
	/** 
	 * Egy lista ami tartalmazza az adott Personnél lévő WetTableCloth tárgyakat. Amennyiben van ebben
	 * a listában, akkor bizonyos helyzetekben hatással lehet a személyre, vagy más személyre a szobában, 
	 * illetve a hasWetTableCloth true. Minden itt lévő tárgy benne van a Person inventory-jában is.
	*/
	protected List<Defendable> wetTableClothes;
	
	/** 
	 * Egy lista ami tárolja az adott Person-nál lévő TVSZ-eket. A TVSZ bizonyos helyzetben hatással lehet
	 * a Person-re, megvédheti azt. Minden TVSZ ami ebben a listában benne van, a Person inventory-jában is 
	 * szerepel. Másrészt a hasTVSZ ekkor true.
	*/
	protected List<Defendable> tvszs;
	
	/** 
	 * Egy lista ami a Person-nél lévő HolyBeerCup-okat tárolja, ez a tárgy is megvédheti a Person-t bizonyos
	 * helyzetekben. Minden HolyBeerCup ami ebben a listában benne van, a Person inventory-jában is szerepel. 
	 * Másrészt a hasHolyBeerCup ekkor true.
	*/
	protected List<Defendable> holyBeerCups;
	
	/** 
	 * Egy lista ami tartalmazza a Person-nél lévő FFP2Mask-okat. Minden tárgy ami ebben a listában van az
	 * szerepel a Person inventoryjában is. Ekkor a hasFFP2Mask true. Ez megvédheti a kábulástól olyan helyzetekben, 
	 * amikor gázos szobába lép, vagy a szobában ahol tartózkodik gázos lesz.
	*/
	protected List<Defendable> ffp2Masks;

	public Person(){
		inventory = new ArrayList<>();
		wetTableClothes = new ArrayList<>();;
		tvszs = new ArrayList<>();
		holyBeerCups = new ArrayList<>();
		ffp2Masks = new ArrayList<>();
	}
	
	/**
	 * Ez a függvény arra szolgál, hogy beállítsa az adott Person isFainted változójának értékét a paraméterként
	 * megadott boolean változó értéke alapján. Elkábulás esetén ezzel a függvénnyel állítódik be az isFainted változó,
	 * vagy amennyiben a kábulás elmúlik hasonlóan ez a függvény csinálja meg a változást a Person-ban.
	 *
	 * @param  b  Egy boolean típusú változó, mely az adja meg, hogy az isFainted értéke mire változzon.
	 */
	public void SetIsFainted(boolean b) {
		Logger.started(this, "SetIsFainted", b);

		if(b){
			ThrowAllItems();
		}

		Logger.finished(this, "SetIsFainted", b);
	}
		
	/** 
	 * A Person abstract függvénye, mely arra szolgál, hogy a kiválasztott szobában megjelenjen. Ez a függvény
	 * a leszármazott osztályokban van implementálva attól függően, hogy milyen tulajdonságú a származott osztály.
	 * 
	 *  @param  r  Az a szoba, amelyben a Person meg szeretne jelenni
	*/
	public abstract void AppearInRoom(Room r);


	public void Throw(Item i) {
		Logger.started(this, "Throw", i);

		if(inventory.contains(i)){
			i.Thrown(this);
			RemoveFromInventory(i);
			this.GetRoom().AddItem(i);
		}

		Logger.finished(this, "Throw", i);
	}
	
	/** 
	 * Ez a függvény a Person inventoryjában lévő minden Item-re meghívja a Throw(Item i) függvényét, és így 
	 * kiürül az adott Person inventoryja.
	*/
	public void ThrowAllItems() {
		Logger.started(this, "ThrowAllItems");

		ArrayList<Item> inventoryCopy = new ArrayList<>(inventory);
		for(Item item : inventoryCopy){
			Throw(item);
		}

		Logger.finished(this, "ThrowAllItems");
	}


	/** 
	 * Beállítja, hogy az adott Person melyik szobában tartózkodjon.
	 * 
	 *  @param  r  Az a szoba, amely szobában a Person tartózkodni fog
	*/
	public void SetRoom(Room r) {
		Logger.started(this, "SetRoom", r);
		room = r;
		Logger.finished(this, "SetRoom", r);
	}

	/**
	 * Vissza adja azt a szobát amiben a Person jelenleg tartózkodik.
	 *
	 *  @return  Az a szoba ahol jenleg tartózkodik
	 */
	public Room GetRoom() {
		Logger.started(this, "GetRoom");
		Logger.finished(this, "GetRoom");
		return room;
	}

	
	/** 
	 * A paraméterként megadott Item-et hozzáadja a Person inventoryjába. Attól függően, hogy van-e több hely az inventoryjában,
	 * előfordulhat, hogy nem adja hozzá, ekkor false értékkel tér vissza, ellenkező esetben true-val.
	 * 
	 *  @param  i  A paraméterként megadott Item, melyet az inventoryba adhat hozzá
	 *  @return    A visszatérési érték, ami mutatja, hogy sikerült-e hozzáadni a paraméterként megadott Itemet az inventoryhoz
	*/
	public boolean AddToInventory(Item i) {
		Logger.started(this, "AddToInventory", i);
		boolean canAdd = inventory.size() < 5;
		if(canAdd) {
			inventory.add(i);
			i.SetRoom(null);
			i.SetOwner(this);
		}
		Logger.finished(this, "AddToInventory", i);
		return canAdd;
	}
	
	/** 
	 * Ez a függvény kitörli a paraméterként megadott Itemet a Person inventoryjából amennyiben ez benne van. Ha benne van és sikerül
	 * kitörölni, akkor true értékkel tér vissza, ellenkező esetben false-al.
	 * 
	 *  @param  i  A paraméterként megadott Item, melyet az inventoryjából törölhet ki
	 *  @return    A visszatérési érték, ami mutatja, hogy sikerült-e törölni az i Itemet az inventoryból
	*/
	public boolean RemoveFromInventory(Item i) {
		Logger.started(this, "RemoveFromInventory", i);
		boolean canRemove = inventory.remove(i);
		if(canRemove){
			i.SetRoom(room);
			i.SetOwner(null);
		}
		Logger.finished(this, "RemoveFromInventory", i);
		return canRemove;
	}
	
	/** 
	 * Ez a függvény megvédheti az adott Persont a gyilkosságtól. Attól függ, hogy sikerül-e megvédeni, hogy milyen Defendable itemek találhatók
	 * a Person inventoryjában, megfelelő listákban. Amennyiben sikerül megvédenie, trueval tér vissza, egyébként falseal.
	 * 
	 *  @return    Boolean visszatérési érték mely jelzi, hogy sikerült-e megvédeni a Person-t a gyilkosságtól.
	*/
	public boolean DefendFromKill(Instructor instructor) {
		Logger.started(this, "DefendFromKill");
		if(hasWetTableCloth) {
			Defendable wtc = GetRandomActive(wetTableClothes);
			instructor.Stun(3);
			Logger.finished(this, "DefendFromKill");
			return true;
		}
		else if(hasHolyBeerCup) {
			// TODO ekkor kene valszeg eldobni egy random itemet
			Logger.finished(this, "DefendFromKill");
			return true;
		}
		else if(hasTVSZ) {
			Defendable tvsz = GetRandomActive(tvszs);
			tvsz.Decrement();
			Logger.finished(this, "DefendFromKill");
			return true;
		}

		Logger.finished(this, "DefendFromKill");
		return false;
	}
	
	/** 
	 * Ez a függvény megvédheti az adott Persont a kábulástól. Attól függ, hogy sikerül-e megvédeni, hogy milyen Defendable itemek találhatók
	 * a Person inventoryjában, megfelelő listkában. Amennyiben sikerül megvédenie, trueval tér vissza, egyébként falseal.
	 * 
	 *  @return    Boolean visszatérési érték mely jelzi, hogy sikerült-e megvédeni a Person-t a kábulástól.
	*/
	public boolean DefendFromGas() {
		Logger.started(this, "DefendFromGas");
		Defendable randActive = GetRandomActive(ffp2Masks);
		if (randActive != null) {
			randActive.Decrement();
			Logger.finished(this, "DefendFromGas");
			return true;
		}
		Logger.finished(this, "DefendFromGas");
		return false;
	}
	
	/** 
	 * A paraméterként megadott WetTableClothot hozzáveszi a Person wetTableCloths listájához.
	 * 
	 *  @param  w  A WetTableCloth melyet hozzá ad a listához
	*/
	public void AddWetTableCloth(Defendable w) {
		Logger.started(this, "AddWetTableCloth", w);
		wetTableClothes.add(w);
		hasWetTableCloth = true;
		Logger.finished(this, "AddWetTableCloth", w);
	}
	
	/** 
	 * A paraméterként megadott TVSZ-t hozzáveszi a Person tvszs listájához.
	 * 
	 *  @param  t  A TVSZ melyet hozzá ad a listához
	*/
	public void AddTVSZ(Defendable t) {
		Logger.started(this, "AddTVSZ", t);
		tvszs.add(t);
		hasTVSZ = true;
		Logger.finished(this, "AddTVSZ", t);
	}
	
	/** 
	 * A paraméterként megadott FFP2Maskot hozzáveszi a Person ffp2Masks listájához.
	 * 
	 *  @param  f  Az FFP2Mask melyet hozzá ad a listához
	*/
	public void AddFFP2Mask(Defendable f) {
		Logger.started(this, "AddFFP2Mask", f);
		ffp2Masks.add(f);
		hasFFP2Mask = true;
		Logger.finished(this, "AddFFP2Mask", f);
	}
	
	/** 
	 * A paraméterként megadott HolyBeerCupot hozzáveszi a Person holyBeerCups listájához.
	 * 
	 *  @param  h  A HolyBeerCup melyet hozzá ad a listához
	*/
	public void AddHolyBeerCup(Defendable h) {
		Logger.started(this, "AddHolyBeerCup", h);
		this.holyBeerCups.add(h);
		hasHolyBeerCup = true;
		Logger.finished(this, "AddHolyBeerCup", h);
	}
	
	/** 
	 * A paraméterként megadott WetTableClothot kiveszi a Person wetTableCloths listájából.
	 * 
	 *  @param  w  A WetTableCloth melyet kivesz a listából
	*/
	public void RemoveWetTableCloth(Defendable w) {
		Logger.started(this, "RemoveWetTableCloth", w);
		wetTableClothes.remove(w);
		if(wetTableClothes.isEmpty()) hasWetTableCloth = false;
		Logger.finished(this, "RemoveWetTableCloth", w);
	}
	
	/** 
	 * A paraméterként megadott TVSZ-t kiveszi a Person tvszs listájából.
	 * 
	 *  @param  t  A TVSZ melyet kivesz a listából
	*/
	public void RemoveTVSZ(Defendable t) {
		Logger.started(this, "RemoveTVSZ", t);
		tvszs.remove(t);
		if(tvszs.isEmpty()) hasTVSZ = false;
		Logger.finished(this, "RemoveTVSZ", t);
	}


	/** 
	 * A paraméterként megadott FFP2Maskot kiveszi a Person ffp2Masks listájából.
	 * 
	 *  @param  f  Az FFP2Mask melyet kivesz a listából
	*/
	public void RemoveFFP2Mask(Defendable f) {
		Logger.started(this, "RemoveFFP2Mask", f);
		ffp2Masks.remove(f);
		if(ffp2Masks.isEmpty()) hasFFP2Mask = false;
		Logger.finished(this, "RemoveFFP2Mask", f);
	}
	
	/** 
	 * A paraméterként megadott HolyBeerCupot kiveszi a Person holyBeerCups listájából.
	 * 
	 *  @param  h  A HolyBeerCup melyet kivesz a listából
	*/
	public void RemoveHolyBeerCup(Defendable h) {
		Logger.started(this, "RemoveHolyBeerCup", h);
		holyBeerCups.remove(h);
		if(holyBeerCups.isEmpty()) hasHolyBeerCup = false;
		Logger.finished(this, "RemoveHolyBeerCup", h);
	}
	
	/** 
	 * Egy függvény, mely vissza ad a paraméterként megadott Defendable listából egy olyat, amelyre igaz,
	 * hogy még meg tudja védeni a Person-t.
	 * 
	 *  @param  list  Az a lista amelyből kiválasz egy olyan Defendable, ami még képes megvédeni.
	*/
	public Defendable GetRandomActive(List<Defendable> list) {
		Logger.started(this, "GetRandomActive", list);
		for (Defendable d : list) {
			if (d.CanDefend()) {
				Logger.finished(this, "GetRandomActive", list);
				return d;
			}
		}
		Logger.finished(this, "GetRandomActive", list);
		return null;
	}
}
