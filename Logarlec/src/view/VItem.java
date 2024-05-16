package view;

import viewmodel.IVItemUpdate;

import javax.swing.*;


public abstract class VItem implements IVItemUpdate {
    private String ID;

    private VRoom room;

    public String GetID(){
        return ID;
    }
    public abstract void PickedUp();
    public abstract CirclePanel DrawOnMap();
    public abstract void DrawInInventory(JPanel panel, VStudent student);
    public abstract boolean HasNullable();
    public abstract void Used();
    public abstract void Thrown();
    public abstract void Connected();
    public abstract VTransistor GetClickedT();
    public abstract void SetClickedT(VTransistor t);
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
