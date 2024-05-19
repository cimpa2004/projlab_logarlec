package viewmodel;

public interface IVItemUpdate {
    /**
     * Ezzel jelez az Item a Modelbol a Viewnak, amikor azt eldobtak
     */
    public void ThrownUpdate(IVItem item);

    /**
     * Ezzel jelez az Item a Modelbol a Viewnak, amikor azt felvettek
     */
    public void PickedUpUpdate(IVItem item);

    /**
     * Ezzel jelez az Item a Modelbol a Viewnak, amikor azt hasznaltak
     */
    public void UsedUpdate(IVItem item, boolean success);
}
