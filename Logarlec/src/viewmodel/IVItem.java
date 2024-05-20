package viewmodel;


import view.VItem;


public interface IVItem {
    public String GetID();
    public IVRoom GetIVRoom();
    public void SetIVItemUpdate(IVItemUpdate ivItemUpdate);
    VItem GetIVItemUpdate();
}
