package viewmodel;

import view.VPerson;

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
     * @param item az az IVItem amit hasznaltakk
     * @param success jelzi, hogy a hasznalat sikeres volt-e: bizonyos esetben jelenthet deaktivaciot stb
     */
    public void UsedUpdate(IVItem item, boolean success);
    public void Activated(IVItem ivItem, ActivateResult result);
    public void SetOwner(VPerson vP);
    public void Decremented(IVItem itemDecremented, int currentAvailability);
}
