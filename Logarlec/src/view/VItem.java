package view;

import util.Logger;
import viewmodel.IVItem;
import viewmodel.IVItemUpdate;

import javax.swing.*;


public abstract class VItem implements IVItemUpdate {
    private String ID;
    private VRoom room;
    public String GetID(){
        Logger.startedModel(this, "GetID");
        Logger.finishedModel(this, "GetID");
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
        Logger.startedView(this, "ThrownUpdate");
        Logger.finishedView(this, "ThrownUpdate");
        // TODO: implement
        // Általános:
        // Ha currentStudent dobta el ne rajzolódjon ki az inventoryban
        // Rajzolódjon ki a szobában, (ha az a megjelenített szoba?)
        // Transistor?? --> Minden redraw, inv -ből eltűnik,szoba lehet változik
    }

    @Override
    public void PickedUpUpdate() {
        Logger.startedView(this, "PickedUpUpdate");
        Logger.finishedView(this, "PickedUpUpdate");
        // TODO: implement
        throw new UnsupportedOperationException("Unimplemented method 'PickedUpUpdate'");
    }

    @Override
    public void UsedUpdate() {
        Logger.startedView(this, "UsedUpdated");
        Logger.finishedView(this, "UsedUpdated");
        // TODO:
        // Általános:
        // Ne rajzolódjon ki a szobában, szoba redraw (ha az a megjelenített szoba?)
        // Ha currentStudent vette fel, akkor rajzolja ki az inventoryban, inv redraw
        // SlideRule?? --> ez már jelezve van szerintem, itt nem kell kitérni rá
    }

}
