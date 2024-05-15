package viewmodel;


public interface IVInit {
    /**
     * Hozza ad egy Studentet a jatekhoz
     * @param personID Hozza adando Student IDja
     */
    public void AddStudent(String personID);
    /**
     * Kivesz egy Studentet a jatekhoz
     * @param personID Torlendo Student IDja
     */
    public void RemoveStudent(String personID);

    /**
     * Elinditja a jatekot
     */
    public void StartGame();
    /**
     * Letrehoz egy jatekot, a megadott mapPathJSONbol, illetve beallitva a grafikus felulet altal imlementalt interfaceket
     */
    public void CreateGame(String mapPathJSON, ICInit icInit, IControl iControl, ICRoom icRoom);
}
