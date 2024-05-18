package viewmodel;


import view.VItem;

import java.awt.*;

public interface IVItem {
    public String GetID();
    public IVRoom GetIVRoom();
    public void SetIVItemUpdate(IVItemUpdate ivItemUpdate);
    public Color GetColor();

    VItem GetIVItemUpdate();
}
