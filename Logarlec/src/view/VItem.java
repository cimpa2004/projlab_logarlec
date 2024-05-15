package view;

import viewmodel.IVItemUpdate;

import javax.swing.*;


public class VItem implements IVItemUpdate {
    private String ID;
    public void DrawInInventory(JPanel panel){

    }
    public String GetID(){
        return ID;
    }

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
