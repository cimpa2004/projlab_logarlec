package view;

import javax.swing.*;
import java.awt.*;

public abstract class VItem {

    private VRoom room;
    private String ID;
    public String GetID(){
        return ID;
    }
    public abstract void PickedUp();
    public abstract CirclePanel DrawOnMap();
    public abstract void DrawInInventory(JPanel panel);

    public abstract boolean HasNullable();

    public abstract void Throw();
}
