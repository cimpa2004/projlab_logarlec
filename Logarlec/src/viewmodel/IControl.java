package viewmodel;

public interface IControl {
    /**
     * Ezt a fuggvenyt a View-n hivja meg a Controller, ahol Updatel jelez, amikor egy uj kovetkezik
     */
    public void Update(); // TODO maybe later give some parameters here, i guess

    /**
     * Ezt a fuggvenyt a View-n hivja meg a Controller, amiben jelzi, hogy az Instructorok nyertek
     */
    public void InstructorWin();
    /**
     * Ezt a fuggvenyt a View-n hivja meg a Controller, amiben jelzi, hogy az studentek nyertek
     */
    public void StudentWin();
}
