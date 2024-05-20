package view;

import util.Logger;
import viewmodel.IVItem;
import viewmodel.IVItemUpdate;

import javax.swing.*;
import java.awt.*;


public abstract class VItem implements IVItemUpdate {
    private Dimension position;
    private VRoom room;
    VPerson owner;
    private ControlPanel controlPanel;
    public abstract String GetID();
    public abstract void PickedUp(VStudent owner);
    public abstract void DrawInInventory(JPanel panel, VStudent student, boolean buttons);
    public abstract boolean HasNullable();
    public abstract void Used();
    public abstract void Thrown();
    public abstract void Connected();
    public abstract VTransistor GetClickedT();
    public abstract void SetClickedT(VTransistor t);
    public abstract Color GetColor();
    public abstract IVItem GetIVItem();

    /**
     * Vissza adja a VItem poziciojat a jelenlegi szobaban
     * @return a pozicioja
     */
    public Dimension GetPosition(){
        return this.position;
    }

    /**
     * Beallithato a VItem pozioja a szobaban
     * @param position az uj pozicio
     */
    public void SetPosition(Dimension position){
        this.position = position;
    }

    /***
     * Redraws control and game panel
     */
    @Override
    public void ThrownUpdate(IVItem item, boolean teleport) {
        Logger.startedView(this, "ThrownUpdate");
        if(teleport){
            owner.GetControlPanel().LogEvent(owner.GetID() + " eldobta a " + item.GetID() + " tárgyat.\n");
            owner.GetControlPanel().LogEvent(owner.GetID() + " teleportált a "
                    + owner.GetControlPanel().GetCurrentStudent().GetRoom().GetID() + " szobába.\n");
        }
        owner.GetControlPanel().UpdateAll();
        Logger.finishedView(this, "ThrownUpdate");
    }

    @Override
    public void PickedUpUpdate(IVItem item, boolean success) {
        Logger.startedView(this, "PickedUpUpdate");
        if(success){
            this.position = null;
            owner.GetControlPanel().LogEvent(owner.GetID() + " felvette a " + item.GetID() + " tárgyat.\n");
            owner.GetControlPanel().UpdateAll();
        }else{
            controlPanel.LogEvent("Nem sikerült felvenni a " + item.GetID() + " tárgyat, mivel megtelt az inventory.\n");
            controlPanel.UpdateAll();
        }

        Logger.finishedView(this, "PickedUpUpdate");
    }

    @Override
    public void UsedUpdate(IVItem item, boolean success) {
        Logger.startedView(this, "UsedUpdated");
        if(success){
            owner.GetControlPanel().LogEvent(owner.GetID() + " használta a " + item.GetID() + " tárgyat.\n");
        }else{
            owner.GetControlPanel().LogEvent(owner.GetID() + " megpróbálta használni a " + item.GetID() + " tárgyat, de nem sikerült.\n");
        }
        owner.GetControlPanel().UpdateAll();
        Logger.finishedView(this, "UsedUpdated");
    }

    public void SetOwner(VPerson vp){
        owner = vp;
    }
    public void SetControlPanel(ControlPanel cp){ controlPanel = cp; }
}
