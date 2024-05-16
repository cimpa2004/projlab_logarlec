package view;

import viewmodel.IVCamembert;

import javax.swing.*;
import java.awt.*;

public class VCamembert extends VItem{
    IVCamembert ivCamembert;

    VStudent owner;

    public VCamembert(IVCamembert ivCamembert){
        this.ivCamembert = ivCamembert;
        this.ivCamembert.SetIVItemUpdate(this);
    }

    /**
     *
     */
    @Override
    public void PickedUp() {
        owner.input.PickupItem(owner.getID(), ivCamembert);
    }

    /**
     *
     */
    @Override
    public CirclePanel DrawOnMap() {
        return new CirclePanel(new Color(255,255,0));
    }

    /**
     * @param panel
     */
    @Override
    public void DrawInInventory(JPanel panel, VStudent student) {
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(new Color(255,255,0), true, false, this);

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
        owner.input.UseItem(owner.getID(), ivCamembert);
    }

    /**
     *
     */
    @Override
    public void Thrown() {
        owner.input.ThrowItem(owner.getID(), ivCamembert);
    }

    /**
     *
     */
    @Override
    public void Connected(){}

    /**
     * @return
     */
    @Override
    public VTransistor GetClickedT() { return null; }

    /**
     * @param t
     */
    @Override
    public void SetClickedT(VTransistor t) {}
}
