package viewmodel;

import view.VDoorSide;

public interface IVDoorSide {
    public String GetID();
    public IVRoom GetIVRoom();
    public boolean GetIsVisible();
    public boolean GetCanBeOpened();
    public IVDoorSide GetIVPair();

    public void SetIVDoorSideUpdate(IVDoorSideUpdate ivDoorSide);
    public IVDoorSideUpdate GetIVDoorSideUpdate();
}
