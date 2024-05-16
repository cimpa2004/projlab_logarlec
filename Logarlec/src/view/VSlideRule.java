package view;

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
        owner.input.PickupItem(owner.getID(), ivSlideRule);
    }

    /**
     *
     */
    @Override
    public CirclePanel DrawOnMap() {
        return new CirclePanel(new Color(0,255,0));
    }

    /**
     * @param panel
     */
    @Override
    public void DrawInInventory(JPanel panel, VStudent student) {
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(new Color(0,255,0), false, false, this);

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
    public void Used() {}

    /**
     *
     */
    @Override
    public void Thrown() {
        owner.input.ThrowItem(owner.getID(), ivSlideRule);
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
