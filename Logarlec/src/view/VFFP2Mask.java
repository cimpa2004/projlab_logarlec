package view;

import util.Logger;
import viewmodel.IVFFP2Mask;
import viewmodel.IVItem;

import javax.swing.*;
import java.awt.*;

public class VFFP2Mask extends VItem{
    IVFFP2Mask ivFFP2Mask;
    VStudent owner;
    public VFFP2Mask(IVFFP2Mask ivFFP2){
        ivFFP2Mask = ivFFP2;
        ivFFP2Mask.SetIVItemUpdate(this);
    }

    /**
     * Visszaadja a tárgy színét.
     * */
    @Override
    public Color GetColor() {
        return new Color(255, 255, 255);
    }

    @Override
    public IVItem GetIVItem() {
        return ivFFP2Mask;
    }

    @Override
    public String GetID() {
        return ivFFP2Mask.GetID();
    }

    /**
     *
     */
    @Override
    public void PickedUp(VStudent st) {
        Logger.startedView(this, "PickedUp");
        owner = st;
        owner.input.PickupItem(owner.GetID(), ivFFP2Mask);
        Logger.finishedView(this, "PickedUp");
    }

    /**
     * @param panel
     */
    @Override
    public void DrawInInventory(JPanel panel, VStudent student, boolean buttons) {
        Logger.startedView(this, "DrawInInventory", panel, student);
        Logger.finishedView(this, "DrawInInventory", panel, student);
        owner = student;
        InventoryItemPanel itemPanel = new InventoryItemPanel(
                GetColor(), true, false, this, "FFP2 maszk", buttons);

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
