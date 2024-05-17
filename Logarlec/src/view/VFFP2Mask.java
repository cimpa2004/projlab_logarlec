package view;

import util.Logger;
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
        Logger.startedView(this, "PickedUp");
        Logger.finishedView(this, "PickedUp");
        owner.input.PickupItem(owner.GetID(), ivFFP2Mask);
    }

    /**
     *
     */
    @Override
    public CirclePanel DrawOnMap() {
        Logger.startedView(this, "DrawOnMap");
        Logger.finishedView(this, "DrawOnMap");
        return new CirclePanel(new Color(255,255,255));
    }

    /**
     * @param panel
     */
    @Override
    public void DrawInInventory(JPanel panel, VStudent student) {
        Logger.startedView(this, "DrawInInventory", panel, student);
        Logger.finishedView(this, "DrawInInventory", panel, student);
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(new Color(255,255,255), true, false, this);

        panel.add(itemPanel);
    }

    /**
     * @return
     */
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
        owner.input.UseItem(owner.GetID(), ivFFP2Mask);
    }

    /**
     *
     */
    @Override
    public void Thrown() {
        Logger.startedView(this, "Thrown");
        Logger.finishedView(this, "Thrown");
        owner.input.ThrowItem(owner.GetID(), ivFFP2Mask);
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
