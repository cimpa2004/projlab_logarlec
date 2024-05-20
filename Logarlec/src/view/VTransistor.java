package view;

import model.Transistor;
import util.Logger;
import viewmodel.IVTransistor;

import javax.swing.*;
import java.awt.*;

public class VTransistor extends VItem{

    private static VTransistor ClickedT;
    IVTransistor ivTransistor;
    VStudent owner;

    public VTransistor(IVTransistor ivT){
        ivTransistor = ivT;
        ivTransistor.SetIVItemUpdate(this);
    }

    @Override
    public void PickedUp(VStudent st) {
        Logger.startedView(this, "PickedUp");
        owner = st;
        owner.input.PickupItem(owner.GetID(), ivTransistor);
        Logger.finishedView(this, "PickedUp");
    }

    @Override
    public void DrawInInventory(JPanel panel, VStudent student, boolean buttons) {
        Logger.startedView(this, "DrawInInventory", panel, student);
        Logger.finishedView(this, "DrawInInventory", panel, student);
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(
                ivTransistor.GetColor(), true, true, this, "Tranzisztor", buttons);

        panel.add(itemPanel);
    }

    @Override
    public boolean HasNullable() {
        Logger.startedView(this, "HasNullable");
        Logger.finishedView(this, "HasNullable");
        return true;
    }

    /**
     *
     */
    @Override
    public void Used() {
        Logger.startedView(this, "Used");
        Logger.finishedView(this, "Used");
        owner.input.UseItem(owner.GetID(), ivTransistor);
    }

    @Override
    public void Thrown() {
        Logger.startedView(this, "Thrown");
        Logger.finishedView(this, "Thrown");
        owner.input.ThrowItem(owner.GetID(), ivTransistor);
    }

    /**
     *
     */
    @Override
    public void Connected() {
        Logger.startedView(this, "Connected");
        Logger.finishedView(this, "Connected");
        owner.input.Connect(owner.GetID(), ivTransistor, ClickedT.ivTransistor);
        SetClickedT(null);
    }

    public VTransistor GetClickedT() {
        Logger.startedView(this, "GetClickedT");
        Logger.finishedView(this, "GetClickedT");
        return ClickedT;
    }

    /**
     * @param t
     */
    @Override
    public void SetClickedT(VTransistor t) {
        Logger.startedView(this, "SetClickedT", t);
        Logger.finishedView(this, "SetClickedT", t);
        ClickedT = t;
    }
}
