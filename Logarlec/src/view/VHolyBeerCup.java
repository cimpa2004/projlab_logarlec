package view;

import viewmodel.IVHolyBeerCup;

import javax.swing.*;
import java.awt.*;

public class VHolyBeerCup extends VItem {
    IVHolyBeerCup ivHolyBeerCup;
    VStudent owner;

    public VHolyBeerCup(IVHolyBeerCup ivHolyBeerCup) {
        this.ivHolyBeerCup = ivHolyBeerCup;
        this.ivHolyBeerCup.SetIVItemUpdate(this);
    }

    /**
     *
     */
    @Override
    public void PickedUp() {
        owner.input.PickupItem(owner.getID(), ivHolyBeerCup);
    }

    /**
     *
     */
    @Override
    public CirclePanel DrawOnMap() {
        return new CirclePanel(new Color(153,0,76));
    }

    /**
     * @param panel
     */
    @Override
    public void DrawInInventory(JPanel panel, VStudent student) {
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(new Color(153,0,76), true, false, this);

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
        owner.input.UseItem(owner.getID(), ivHolyBeerCup);

    }

    /**
     *
     */
    @Override
    public void Thrown() {
        owner.input.ThrowItem(owner.getID(), ivHolyBeerCup);

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
