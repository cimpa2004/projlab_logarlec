package view;

import viewmodel.IVFFP2Mask;

import javax.swing.*;
import java.awt.*;

public class VFFP2Mask extends VItem{
    IVFFP2Mask ivFFP2Mask;
    VStudent owner;
    public VFFP2Mask(IVFFP2Mask ivffp2Mask){
        this.ivFFP2Mask = ivffp2Mask;
        this.ivFFP2Mask.SetIVItemUpdate(this);
    }

    /**
     *
     */
    @Override
    public void PickedUp() {
        owner.input.PickupItem(owner.getID(), ivFFP2Mask);
    }

    /**
     *
     */
    @Override
    public CirclePanel DrawOnMap() {
        return new CirclePanel(new Color(255,255,255));
    }

    /**
     * @param panel
     */
    @Override
    public void DrawInInventory(JPanel panel, VStudent student) {
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(new Color(255,255,255), true, false, this);

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
        owner.input.UseItem(owner.getID(), ivFFP2Mask);
    }

    /**
     *
     */
    @Override
    public void Thrown() {
        owner.input.ThrowItem(owner.getID(), ivFFP2Mask);
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
