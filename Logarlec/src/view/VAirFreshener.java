package view;

import viewmodel.IVAirFreshener;

import javax.swing.*;
import java.awt.*;

public class VAirFreshener extends VItem {
    IVAirFreshener ivAirFreshener;

    VStudent owner;

    public VAirFreshener(IVAirFreshener ivAirFreshener) {
        this.ivAirFreshener = ivAirFreshener;
        this.ivAirFreshener.SetIVItemUpdate(this);
    }

    /**
     *
     */
    @Override
    public void PickedUp() {
        owner.input.PickupItem(owner.getID(), ivAirFreshener);
    }

    /**
     *
     */
    @Override
    public CirclePanel DrawOnMap() {
        return new CirclePanel(new Color(0,0,255));
    }

    /**
     * @param panel
     */
    @Override
    public void DrawInInventory(JPanel panel, VStudent student) {
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(new Color(0,0,255), true, false, this);

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
        owner.input.UseItem(owner.getID(), ivAirFreshener);
    }

    /**
     *
     */
    @Override
    public void Thrown() {
        owner.input.ThrowItem(owner.getID(), ivAirFreshener);
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
    public void SetClickedT(VTransistor t) {}
}
