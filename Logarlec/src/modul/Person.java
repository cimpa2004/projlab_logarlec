package modul;


import java.util.List;

/**
 * Ez egy abstract osztály ami játékban szereplő személyeket reprezentálja általánosan és 
 * összefoglalja azokat a funkciókat, attribútumokat amiket a Hallgatók és Oktatók is hordoznak.
*/
public abstract class Person {
	/**
	 * Hz az adott Person egy gázos szobában tartózkodik, akkor ez a változó mutatja, hogy
	 * el van kábulva. 
	*/
	private boolean isFainted;
	
	/**
	 * Ez a változó mutatja, hogy az adott Personnak jelenleg aktív egy a köre. Amikor a Person
	 * köre megkezdődik, akor ez a változó true lesz, amikor vége a köre, akkor false. 
	*/
	private boolean activeTurn;
	
	/** 
	 * Ez mutatja, hogy az adott Person rendelkezik-e WetTableCloth-tal. Ennek segítségével bizonyos
	 * helyzetekben ez hatással lehet az adott személyre (megvédheti), vagy más személyre a szobában
	 * ahol a Person tartózkodik. 
	*/
	private boolean hasWetTableCloth;
	
	/** 
	 * Ez mutatja, hogy az adott Person rendelkezik-e TVSZ-szel. Ezzel bizonyos helyzetekben hatással lehet
	 * lehet a bizonyos akciókra, példáúl megvédheti az adott Person-t.
	*/
	private boolean hasTVSZ;
	
	/** 
	 * Azt mutatja, hogy az adott Person rendelkezik-e FFP2Mask-kal. Ez megvédheti a kábulástól 
	 * olyan helyzetekben, amikor gázos szobába lép, vagy a szobában ahol tartózkodik gázos lesz.
	*/
	private boolean hasFFP2Mask;
	
	/** 
	 * Mutatja, hogy az adott Person rendelkezik-e HolyBeerCup-pal. Ez az Item megvédheti az
	 * adott Person-t bizonyos helyzetekben.
	*/
	public boolean hasHolyBeerCup;
	
	/** 
	 * Item-ek listája. Ez a lista tartalmazza az adott Person-nél lévő Itemeket. Egy Personnél
	 * maximum csak 5 Item lehet egyszerre.
	*/
	private List<Item> inventory;

	/** 
	 * Ez mutatja, hogy az adott Person épp melyik szobába helyezkedik el.
	*/
	private Room room;
	
	/** 
	 * Egy lista ami tartalmazza az adott Personnél lévő WetTableCloth tárgyakat. Amennyiben van ebben
	 * a listában, akkor bizonyos helyzetekben hatással lehet a személyre, vagy más személyre a szobában, 
	 * illetve a hasWetTableCloth true. Minden itt lévő tárgy benne van a Person inventory-jában is.
	*/
	private List<Defendable> wetTableClothes;
	
	/** 
	 * Egy lista ami tárolja az adott Person-nál lévő TVSZ-eket. A TVSZ bizonyos helyzetben hatással lehet
	 * a Person-re, megvédheti azt. Minden TVSZ ami ebben a listában benne van, a Person inventory-jában is 
	 * szerepel. Másrészt a hasTVSZ ekkor true.
	*/
	private List<Defendable> tvszs;
	
	/** 
	 * Egy lista ami a Person-nél lévő HolyBeerCup-okat tárolja, ez a tárgy is megvédheti a Person-t bizonyos
	 * helyzetekben. Minden HolyBeerCup ami ebben a listában benne van, a Person inventory-jában is szerepel. 
	 * Másrészt a hasHolyBeerCup ekkor true.
	*/
	private List<Defendable> holyBeerCups;
	
	/** 
	 * Egy lista ami tartalmazza a Person-nél lévő FFP2Mask-okat. Minden tárgy ami ebben a listában van az
	 * szerepel a Person inventoryjában is. Ekkor a hasFFP2Mask true. Ez megvédheti a kábulástól olyan helyzetekben, 
	 * amikor gázos szobába lép, vagy a szobában ahol tartózkodik gázos lesz.
	*/
	private List<Defendable> ffp2Masks;
	
	/**
	 * Ez a függvény arra szolgál, hogy beállítsa az adott Person isFainted változójának értékét a paraméterként
	 * megadott boolean változó értéke alapján. Elkábulás esetén ezzel a függvénnyel állítódik be az isFainted változó,
	 * vagy amennyiben a kábulás elmúlik hasonlóan ez a függvény csinálja meg a változást a Person-ban.
	 *
	 * @param  b  Egy boolean típusú változó, mely az adja meg, hogy az isFainted értéke mire változzon.
	 */
	public void SetIsFainted(boolean b) {
	}
		
	/** 
	 * A Person abstract függvénye, mely arra szolgál, hogy a kiválasztott szobában megjelenjen. Ez a függvény
	 * a leszármazott osztályokban van implementálva attól függően, hogy milyen tulajdonságú a származott osztály.
	 * 
	 *  @param  r  Az a szoba, amelyben a Person meg szeretne jelenni
	*/
	public abstract void AppearInRoom(Room r);
	
	/** 
	 * Ennek segítségével lehet az adott Person-nal a szobák között mozogni. A kiválaszott ajtóba megpróbál belépni.
	 * Lehetséges, hogy az adott ajtón nem lehet belépni valamiért, de amennyiben tud, akkor a Person megjelenhet
	 * az ajtó által definiált másik szobában.
	 * 
	 *  @param  d  Egy ajtó, amelyen a Person megpróbál átlépni egy másik szobába
	*/
	public abstract void Move(DoorSide d);
	
	/**
	 * A Person felszed egy tárgyat. Ez egy abstract függvény, mely a származott osztályokban van implementálva úgy, 
	 * ha az adott Person képes felvenni a tárgyat, van hely az inventoryjában, akkor ez belekerül az inventoryjába.
	 * Előfordulhat, hogy nem kerül benne az inventoryjába, ha nincs több helye, ekkor false-al tér vissza más esetben true-val.
	 *
	 * @param  i  Az az Item, amelyet a Person fel szeretne venni.
	 * @return    Visszatérési érték egy boolean, ami jelzi, hogy sikerült-e felvenni az i Itemet vagy sem
	*/
	public abstract boolean Pickup(Item i);
	
	/** 
	 * A Person a kiválasztott Item-et eldobja. Ha a paraméterként megadott Item nem szerepel a Person inventoryjában, 
	 * akkor nem történik semmi. Ellenkező esetben az Item törlődik a Person inventoryjából és abba a szobába kerül ahol
	 * épp tartózkodik. Az itemen minden esetben meghívódik az Item Thrown függvénye, mely az Itemtől függően törli a Person
	 * védelmi Itemjei közül is a megadott Itemet.
	 * 
	 *  @param  i  A válaszott Item, melyet a Person eldob 
	*/
	public void Throw(Item i) {
	}
	
	/** 
	 * Ez a függvény a Person inventoryjában lévő minden Item-re meghívja a Throw(Item i) függvényét, és így 
	 * kiürül az adott Person inventoryja.
	*/
	public void ThrowAllItems() {
	}
	
	/** 
	 * Egy absract függvény, mely a kiválasztott Usable-t aktiválja. Ez a származott osztályokban van leimplementálva, mert
	 * attól függően változik egy Usable használata, hogy ki használja.
	*/
	public abstract void UseItem(Usable u);

	/** 
	 * Beállítja, hogy az adott Person melyik szobában tartózkodjon.
	 * 
	 *  @param  r  Az a szoba, amely szobában a Person tartózkodni fog
	*/
	public void SetRoom(Room r) {
	}
	
	/** 
	 * Ez az a függvény amelyet a Game hív meg, és ezen belül implementálhatja az adott származott, hogy milyen lépéseket
	 * tesz az aktív körében. Ekkor az activeTurn true értékre is változik.
	*/
	public abstract void StartTurn();
	
	/** 
	 * Egy származott osztály implementálja, és ezzel jelzi, hogy az adott köre véget ért. Ekkor az activeTurn értékre false
	 * lesz.
	*/
	public abstract void EndTurn();
	
	/** 
	 * A paraméterként megadott Item-et hozzáadja a Person inventoryjába. Attól függően, hogy van-e több hely az inventoryjában,
	 * előfordulhat, hogy nem adja hozzá, ekkor false értékkel tér vissza, ellenkező esetben true-val.
	 * 
	 *  @param  i  A paraméterként megadott Item, melyet az inventoryba adhat hozzá
	 *  @return    A visszatérési érték, ami mutatja, hogy sikerült-e hozzáadni a paraméterként megadott Itemet az inventoryhoz
	*/
	public boolean AddToInventory(Item i) {
		return false;
	}
	
	/** 
	 * Ez a függvény kitörli a paraméterként megadott Itemet a Person inventoryjából amennyiben ez benne van. Ha benne van és sikerül
	 * kitörölni, akkor true értékkel tér vissza, ellenkező esetben false-al.
	 * 
	 *  @param  i  A paraméterként megadott Item, melyet az inventoryjából törölhet ki
	 *  @return    A visszatérési érték, ami mutatja, hogy sikerült-e törölni az i Itemet az inventoryból
	*/
	public boolean RemoveFromInventory(Item i) {
		return false;
	}
	
	/** 
	 * Ez a függvény megvédheti az adott Persont a gyilkosságtól. Attól függ, hogy sikerül-e megvédeni, hogy milyen Defendable itemek találhatók
	 * a Person inventoryjában, megfelelő listákban. Amennyiben sikerül megvédenie, trueval tér vissza, egyébként falseal.
	 * 
	 *  @return    Boolean visszatérési érték mely jelzi, hogy sikerült-e megvédeni a Person-t a gyilkosságtól.
	*/
	public boolean DefendFromKill() {
		return false;
	}
	
	/** 
	 * Ez a függvény megvédheti az adott Persont a kábulástól. Attól függ, hogy sikerül-e megvédeni, hogy milyen Defendable itemek találhatók
	 * a Person inventoryjában, megfelelő listkában. Amennyiben sikerül megvédenie, trueval tér vissza, egyébként falseal.
	 * 
	 *  @return    Boolean visszatérési érték mely jelzi, hogy sikerült-e megvédeni a Person-t a kábulástól.
	*/
	public boolean DefendFromGas() {
		return false;
	}
	
	/** 
	 * A paraméterként megadott WetTableClothot hozzáveszi a Person wetTableCloths listájához.
	 * 
	 *  @param  w  A WetTableCloth melyet hozzá ad a listához
	*/
	public void AddWetTableCloth(Defendable w) {
	}
	
	/** 
	 * A paraméterként megadott TVSZ-t hozzáveszi a Person tvszs listájához.
	 * 
	 *  @param  w  A TVSZ melyet hozzá ad a listához
	*/
	public void AddTVSZ(Defendable t) {
	}
	
	/** 
	 * A paraméterként megadott FFP2Maskot hozzáveszi a Person ffp2Masks listájához.
	 * 
	 *  @param  w  Az FFP2Mask melyet hozzá ad a listához
	*/
	public void AddFFP2Mask(Defendable f) {
	}
	
	/** 
	 * A paraméterként megadott HolyBeerCupot hozzáveszi a Person holyBeerCups listájához.
	 * 
	 *  @param  w  A HolyBeerCup melyet hozzá ad a listához
	*/
	public void AddHolyBeerCup(Defendable h) {
	}
	
	/** 
	 * A paraméterként megadott WetTableClothot kiveszi a Person wetTableCloths listájából.
	 * 
	 *  @param  w  A WetTableCloth melyet kivesz a listából
	*/
	public void RemoveWetTableCloth(Defendable w) {
	}
	
	/** 
	 * A paraméterként megadott TVSZ-t kiveszi a Person tvszs listájából.
	 * 
	 *  @param  w  A TVSZ melyet kivesz a listából
	*/
	public void RemoveTVSZ(Defendable t) {
	}
	
	/** 
	 * A paraméterként megadott FFP2Maskot kiveszi a Person ffp2Masks listájából.
	 * 
	 *  @param  w  Az FFP2Mask melyet kivesz a listából
	*/
	public void RemoveFFP2Mask(Defendable f) {
	}
	
	/** 
	 * A paraméterként megadott HolyBeerCupot kiveszi a Person holyBeerCups listájából.
	 * 
	 *  @param  w  A HolyBeerCup melyet kivesz a listából
	*/
	public void RemoveHolyBeerCup(Defendable h) {
	}
	
	/** 
	 * Egy függvény, mely vissza ad a paraméterként megadott Defendable listából egy olyat, amelyre igaz,
	 * hogy még meg tudja védeni a Person-t.
	 * 
	 *  @param  list  Az a lista amelyből kiválasz egy olyan Defendable, ami még képes megvédeni.
	*/
	public Defendable GetRandomActive(List<Defendable> list) {
		return null;
	}
}
