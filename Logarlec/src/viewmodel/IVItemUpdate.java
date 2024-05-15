package viewmodel;

public interface IVItemUpdate {
    /**
     * Ezzel jelez az Item a Modelbol a Viewnak, amikor azt eldobtak
     */
    public void ThrownUpdate();

    /**
     * Ezzel jelez az Item a Modelbol a Viewnak, amikor azt felvettek
     */
    public void PickedUpUpdate();

    /**
     * Ezzel jelez az Item a Modelbol a Viewnak, amikor azt hasznaltak
     */
    public void UsedUpdated();
}
