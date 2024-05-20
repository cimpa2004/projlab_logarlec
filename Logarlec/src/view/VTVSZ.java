package view;

import util.Logger;
import viewmodel.IVItem;
import viewmodel.IVTVSZ;

import javax.swing.*;
import java.awt.*;

public class VTVSZ extends VItem {
    IVTVSZ ivTVSZ;
    VStudent owner;

    public VTVSZ(IVTVSZ ivtvsz) {
        ivTVSZ = ivtvsz;
        ivTVSZ.SetIVItemUpdate(this);
    }

    /**
     * Visszaadja a tárgy színét.
     * */
    @Override
    public Color GetColor() {
        return new Color(242, 70, 38);
    }

    @Override
    public IVItem GetIVItem() {
        return ivTVSZ;
    }

    @Override
    public String GetID() {
        return ivTVSZ.GetID();
    }

    @Override
    public void PickedUp(VStudent st) {
        Logger.startedView(this, "PickedUp");
        owner = st;
        owner.input.PickupItem(owner.GetID(), ivTVSZ);
        Logger.finishedView(this, "PickedUp");
    }

    @Override
    public void DrawInInventory(JPanel panel, VStudent student, boolean buttons) {
        Logger.startedView(this, "DrawInInventory", panel, student);
        Logger.finishedView(this, "DrawInInventory", panel, student);
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(
                GetColor(), false, false, this, "TVSZ", buttons);

        panel.add(itemPanel);
    }

    @Override
    public boolean HasNullable() {
        Logger.startedView(this, "HasNullable");
        Logger.finishedView(this, "HasNullable");
        return false;
    }

    /**
     *
     */
    @Override
    public void Used() {
        Logger.startedView(this, "Used");
        Logger.finishedView(this, "Used");
    }

    @Override
    public void Thrown() {
        Logger.startedView(this, "Thrown");
        Logger.finishedView(this, "Thrown");
        owner.input.ThrowItem(owner.GetID(), ivTVSZ);
    }

    /**
     *
     */
    @Override
    public void Connected() {
        Logger.startedView(this, "Connected");
        Logger.finishedView(this, "Connected");
    }

    /**
     * @return
     */
    @Override
    public VTransistor GetClickedT() {
        Logger.startedView(this, "GetClickedT");
        Logger.finishedView(this, "GetClickedT");
        return null;
    }

    /**
     * @param t
     */
    @Override
    public void SetClickedT(VTransistor t) {
        Logger.startedView(this, "SetClickedT", t);
        Logger.finishedView(this, "SetClickedT", t);
    }
}
