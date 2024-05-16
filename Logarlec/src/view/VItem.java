package view;

import viewmodel.IVItemUpdate;

import javax.swing.*;
import java.awt.*;


public abstract class VItem implements IVItemUpdate {
    private String ID;

    private VRoom room;

    public String GetID(){
        return ID;
    }
    public abstract void PickedUp();
    public abstract CirclePanel DrawOnMap();
    public abstract void DrawInInventory(JPanel panel);

    public abstract boolean HasNullable();

    public abstract void Throw();

    @Override
    public void ThrownUpdate() {
        // TODO: implement
        throw new UnsupportedOperationException("Unimplemented method 'ThrownUpdate'");
    }

    @Override
    public void PickedUpUpdate() {
        // TODO: implement
        throw new UnsupportedOperationException("Unimplemented method 'PickedUpUpdate'");
    }

    @Override
    public void UsedUpdated() {
        // TODO: implement
        throw new UnsupportedOperationException("Unimplemented method 'UsedUpdated'");
    }
}
