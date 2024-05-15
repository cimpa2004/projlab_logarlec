package viewmodel;

public interface ICInput {
    /**
     * Ezt a fuggvenyt a Controlleren hivja meg a View, amikor a bemenetrol egy parancs erkezett.
     * @param personID A Person aki vegrehajta a parancsot
     * @param item Az item amit eldob
     */
    public void ThrowItem(String personID, IVItem item);
    /**
     * Ezt a fuggvenyt a Controlleren hivja meg a View, amikor a bemenetrol egy parancs erkezett.
     * @param personID A Person aki vegrehajta a parancsot
     * @param item Az Item amit felvehet a megadott Person
     */
    public void PickupItem(String personID, IVItem item);
    /**
     * Ezt a fuggvenyt a Controlleren hivja meg a View, amikor a bemenetrol egy parancs erkezett.
     * @param personID A Person aki vegrehajta a parancsot
     * @param doorSide A DoorSide amibe bele szeretne menni
     */
    public void Move(String personID, IVDoorSide doorSide);
    /**
     * Ezt a fuggvenyt a Controlleren hivja meg a View, amikor a bemenetrol egy parancs erkezett. Jelzi, hogy veget
     * ert a kore.
     * @param personID A Person aki vegrehajta a parancsot, es vege lett a kore
     */
    public void EndTurn(String personID);
    /**
     * Ezt a fuggvenyt a Controlleren hivja meg a View, amikor a bemenetrol egy parancs erkezett.
     * @param personID A Person aki vegrehajta a parancsot
     * @param item Az item amit hasznalna a Person
     */
    public void UseItem(String personID, IVItem item);
}
