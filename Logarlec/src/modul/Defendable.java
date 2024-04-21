package modul;


/** */
public interface Defendable {
    /** */
    public boolean CanDefend();
    public void Decrement();

    /**
     * Vissz adja, hogy az adott Defendable mennyi ideig vagy hanyszor hatasos meg
     * @return
     */
    public int GetDurability();

}
