package viewmodel;

public interface IControl {

    /**
     * Ezt a fuggvenyt a View-n hivja meg a Controller, ahol jelzi, hogy egy uj Studentnek kezdodik a kore
     */
    public void StudentStartedTurn();

    /**
     * Ezt a fuggvenyt a View-n hivja meg a Controller, amiben jelzi, hogy az Instructorok nyertek
     */
    public void InstructorWin();
    /**
     * Ezt a fuggvenyt a View-n hivja meg a Controller, amiben jelzi, hogy az studentek nyertek
     */
    public void StudentWin();
}
