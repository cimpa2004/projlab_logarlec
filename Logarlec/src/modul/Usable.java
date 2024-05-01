package modul;


/**
 * A használható tárgyak inteface
 *Három kötelezően megvalósítandó függvényt nyújt az őt megvalósítóknak.
 *Ezzel egységesen használhatóvá (Usable-é) teszi őket.
 */
public interface Usable {
	/**
	 *Activálást teszi lehetővé
	 * @return implementáció a megvalósításban
	 */
	public boolean Activate();

}
