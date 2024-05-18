package viewmodel;

public interface IVDoorSide {
    public String GetID();
    public IVRoom GetIVRoom();
    public boolean GetIsVisible();
    public boolean GetCanBeOpened();
    public IVDoorSide GetIVPair();
}
