package view;

import model.Transistor;
import viewmodel.IVTransistor;

import javax.swing.*;
import java.awt.*;

public class VTransistor extends VItem{

    private static VTransistor ClickedT;
    IVTransistor ivTransistor;
    VStudent owner;

    public VTransistor(IVTransistor ivTransistor){
        this.ivTransistor = ivTransistor;
        this.ivTransistor.SetIVItemUpdate(this);
    }

    @Override
    public void PickedUp() {
        owner.input.PickupItem(owner.getID(), ivTransistor);
    }

    @Override
    public CirclePanel DrawOnMap() {
        return new CirclePanel(new Color(0,0,0));
    }

    @Override
    public void DrawInInventory(JPanel panel, VStudent student) {
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(new Color(0,0,0), true, true, this);

        panel.add(itemPanel);
    }

    @Override
    public boolean HasNullable() {
        return true;
    }

    /**
     *
     */
    @Override
    public void Used() {
        owner.input.UseItem(owner.getID(), ivTransistor);
    }

    @Override
    public void Thrown() {
        owner.input.ThrowItem(owner.getID(), ivTransistor);
    }

    /**
     *
     */
    @Override
    public void Connected() {
        owner.input.Connect(owner.getID(), ivTransistor, ClickedT.ivTransistor);
        SetClickedT(null);
    }

    public VTransistor GetClickedT(){return ClickedT;}

    /**
     * @param t
     */
    @Override
    public void SetClickedT(VTransistor t) {
        ClickedT = t;
    }
}
