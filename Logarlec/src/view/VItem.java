package view;

import util.Logger;
import viewmodel.ActivateResult;
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
        owner.GetControlPanel().LogEvent(owner.GetID() + " eldobta a " + item.GetID() + " tárgyat.\n");
        if(teleport){
            owner.GetControlPanel().LogEvent(owner.GetID() + " teleportált a "
                    + owner.GetControlPanel().GetCurrentStudent().GetRoom().GetID() + " szobába.\n");
        }
        owner.GetControlPanel().UpdateAll(null);
        Logger.finishedView(this, "ThrownUpdate");
    }

    @Override
    public void PickedUpUpdate(IVItem item, boolean success) {
        Logger.startedView(this, "PickedUpUpdate");
        if(success){
            this.position = null;
            owner.GetControlPanel().LogEvent(owner.GetID() + " felvette a " + item.GetID() + " tárgyat.\n");
            owner.GetControlPanel().UpdateAll(null);
        }else{
            controlPanel.LogEvent("Nem sikerült felvenni a " + item.GetID() + " tárgyat, mivel megtelt az inventory vagy ragadós a szoba.\n");
            controlPanel.UpdateAll(null);
        }

        Logger.finishedView(this, "PickedUpUpdate");
    }

    @Override
    public void Decremented(IVItem itemDecremented, int currentAvailability){
        owner.GetControlPanel().LogEvent(owner.GetID() + "-nál lévő " +itemDecremented.GetID() + " tárgynak csökkent a hatásossága egyel. Jelenlegi hatásosság: " + currentAvailability + "\n");
    }

    @Override
    public void UsedUpdate(IVItem item, boolean success) {
        Logger.startedView(this, "UsedUpdated");
        if(success){
            owner.GetControlPanel().LogEvent(owner.GetID() + " használta a " + item.GetID() + " tárgyat.\n");
        }else{
            owner.GetControlPanel().LogEvent(owner.GetID() + " megpróbálta használni a " + item.GetID() + " tárgyat, de nem sikerült.\n");
        }
        owner.GetControlPanel().UpdateAll(null);
        Logger.finishedView(this, "UsedUpdated");
    }

    @Override
    public void Activated(IVItem ivItem, ActivateResult result) {
        Logger.startedView(this, "Activated", ivItem, result);
        owner.controlPanel.LogEvent(owner.GetID() + " használta a " + ivItem.GetID() + " tárgyat: ");
        switch (result){
            case SUCCESSFULLY_ACTIVATED:
                owner.controlPanel.LogEvent("sikeresen aktiválta a tárgyat.\n");
                break;
            case SUCCESSFULLY_DEACTIVATED:
                owner.controlPanel.LogEvent("sikeresen deaktiválta a tárgyat.\n");
                break;
            case FAKE:
                owner.controlPanel.LogEvent("a tárgy hamis, így nem volt hatása.\n");
                break;
            case ALREADY_ACTIVATED:
                owner.controlPanel.LogEvent("nem sikerült aktiválni, mert már aktiválva volt.\n");
                break;
            case CANNOT_BE_ACTIVATED:
                owner.controlPanel.LogEvent("a tárgyat nem lehet külön használni.\n");
                break;
            default:
                owner.controlPanel.LogEvent("a tárgy használata során nem várt működés történt.\n");
                break;
        }

        Logger.finishedView(this, "Activated", ivItem, result);
    }

    public void SetOwner(VPerson vp){
        owner = vp;
    }
    public void SetControlPanel(ControlPanel cp){ controlPanel = cp; }
}
