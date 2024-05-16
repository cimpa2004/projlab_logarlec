package view;

import viewmodel.IVTVSZ;

import javax.swing.*;
import java.awt.*;

public class VTVSZ extends VItem {
    IVTVSZ ivTVSZ;
    VStudent owner;

    public VTVSZ(IVTVSZ ivTVSZ) {
        this.ivTVSZ = ivTVSZ;
        this.ivTVSZ.SetIVItemUpdate(this);
    }

    @Override
    public void PickedUp() {
        owner.input.PickupItem(owner.getID(), ivTVSZ);
    }

    @Override
    public CirclePanel DrawOnMap() {
        return new CirclePanel(new Color(255,0,0));
    }

    @Override
    public void DrawInInventory(JPanel panel, VStudent student) {
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(new Color(255,0,0), false, false, this);

        panel.add(itemPanel);
    }

    @Override
    public boolean HasNullable() {
        return false;
    }

    /**
     *
     */
    @Override
    public void Used() {}

    @Override
    public void Thrown() {
        owner.input.ThrowItem(owner.getID(), ivTVSZ);
    }

    /**
     *
     */
    @Override
    public void Connected() {}

    /**
     * @return
     */
    @Override
    public VTransistor GetClickedT() {
        return null;
    }

    /**
     * @param t
     */
    @Override
    public void SetClickedT(VTransistor t) {

    }
}
