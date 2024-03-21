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

	/**
	 *Egy hallgató használta a tárgyat
	 * @param s implementáció a megvalósításban
	 */
	public void UsedByStudent(Student s);

	/**
	 * Egy oktató használta a tárgyat
	 * @param i implementáció a megvalósításban
	 */
	public void UsedByInstructor(Instructor i);
}
