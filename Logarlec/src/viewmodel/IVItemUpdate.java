package viewmodel;

import view.VPerson;
import view.VStudent;

public interface IVItemUpdate {
    /**
     * Ezzel jelez az Item a Modelbol a Viewnak, amikor azt eldobtak
     */
    public void ThrownUpdate(IVItem item, boolean teleport);

    /**
     * Ezzel jelez az Item a Modelbol a Viewnak, amikor azt felvettek
     */
    public void PickedUpUpdate(IVItem item, boolean success);

    /**
     * Ezzel jelez az Item a Modelbol a Viewnak, amikor azt hasznaltak
     */
    public void UsedUpdate(IVItem item, boolean success);

    public void SetOwner(VPerson vP);
}
