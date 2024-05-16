package view;

import viewmodel.IVWetTableCloth;

import javax.swing.*;
import java.awt.*;

public class VWetTableCloth extends VItem {
    IVWetTableCloth ivWetTableCloth;
    VStudent owner;

    public VWetTableCloth(IVWetTableCloth ivWetTableCloth) {
        this.ivWetTableCloth = ivWetTableCloth;
        ivWetTableCloth.SetIVItemUpdate(this);
    }

    /**
     *
     */
    @Override
    public void PickedUp() {
        owner.input.PickupItem(owner.getID(), ivWetTableCloth);
    }

    /**
     *
     */
    @Override
    public CirclePanel DrawOnMap() {
        return new CirclePanel(new Color(153,0,153));
    }

    /**
     * @param panel
     */
    @Override
    public void DrawInInventory(JPanel panel, VStudent student) {
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(new Color(153,0,153), true, false, this);

        panel.add(itemPanel);
    }

    /**
     * @return
     */
    @Override
    public boolean HasNullable() {
        return false;
    }

    /**
     *
     */
    @Override
    public void Used() {
        owner.input.UseItem(owner.getID(), ivWetTableCloth);
    }

    /**
     *
     */
    @Override
    public void Thrown() {
        owner.input.ThrowItem(owner.getID(), ivWetTableCloth);
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
