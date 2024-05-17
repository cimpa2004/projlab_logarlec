package view;

import util.Logger;
import viewmodel.IVSlideRule;

import javax.swing.*;
import java.awt.*;

public class VSlideRule extends VItem {
    IVSlideRule ivSlideRule;
    VStudent owner;

    //ezt a konstruktort használd Roomban megírtam onnan lehet példát venni
    public VSlideRule(IVSlideRule ivSlideRule){
        this.ivSlideRule = ivSlideRule;
        this.ivSlideRule.SetIVItemUpdate(this);
    }

    /**
     *
     */
    @Override
    public void PickedUp() {
        Logger.startedView(this, "PickedUp");
        Logger.finishedView(this, "PickedUp");
        owner.input.PickupItem(owner.GetID(), ivSlideRule);
    }

    /**
     *
     */
    @Override
    public CirclePanel DrawOnMap() {
        Logger.startedView(this, "DrawOnMap");
        Logger.finishedView(this, "DrawOnMap");
        return new CirclePanel(new Color(0,255,0));
    }

    /**
     * @param panel
     */
    @Override
    public void DrawInInventory(JPanel panel, VStudent student) {
        Logger.startedView(this, "DrawInInventory", panel, student);
        Logger.finishedView(this, "DrawInInventory", panel, student);
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(new Color(0,255,0), false, false, this);

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
    }

    /**
     *
     */
    @Override
    public void Thrown() {
        Logger.startedView(this, "Thrown");
        Logger.finishedView(this, "Thrown");
        owner.input.ThrowItem(owner.GetID(), ivSlideRule);
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
