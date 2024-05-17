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
        // Általános:
        // Ha currentStudent dobta el ne rajzolódjon ki az inventoryban
        // Rajzolódjon ki a szobában, (ha az a megjelenített szoba?)
        // Transistor?? --> Minden redraw, inv -ből eltűnik,szoba lehet változik
    }

    @Override
    public void PickedUpUpdate() {
        // TODO:
        // Általános:
        // Ne rajzolódjon ki a szobában, szoba redraw (ha az a megjelenített szoba?)
        // Ha currentStudent vette fel, akkor rajzolja ki az inventoryban, inv redraw
        // SlideRule?? --> ez már jelezve van szerintem, itt nem kell kitérni rá
    }

    @Override
    public void UsedUpdate() {
        // TODO: implement
        // Általános:
        // Nem csinál semmit igazából
        // AirFreshener?? --> szoba redraw
        // Camembert?? --> szoba redraw
    }
}
