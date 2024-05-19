package view;

import util.Logger;
import viewmodel.IVItem;
import viewmodel.IVItemUpdate;

import javax.swing.*;


public abstract class VItem implements IVItemUpdate {
    private String ID;
    private VRoom room;
    VStudent owner;
    public String GetID(){
        Logger.startedModel(this, "GetID");
        Logger.finishedModel(this, "GetID");
        return ID;
    }
    public abstract void PickedUp(VStudent owner);
    public abstract CirclePanel DrawOnMap();
    public abstract void DrawInInventory(JPanel panel, VStudent student);
    public abstract boolean HasNullable();
    public abstract void Used();
    public abstract void Thrown();
    public abstract void Connected();
    public abstract VTransistor GetClickedT();
    public abstract void SetClickedT(VTransistor t);

    /***
     * Redraws control and game panel
     */
    @Override
    public void ThrownUpdate(IVItem item) {
        Logger.startedView(this, "ThrownUpdate");
        owner.GetControlPanel().LogEvent(owner.GetID() + " eldobta a " + item.GetID() + " tárgyat.\n");
        owner.GetControlPanel().Update();
        Logger.finishedView(this, "ThrownUpdate");
    }

    @Override
    public void PickedUpUpdate(IVItem item) {
        Logger.startedView(this, "PickedUpUpdate");
        owner.GetControlPanel().LogEvent(owner.GetID() + " felvette a " + item.GetID() + " tárgyat.\n");
        owner.GetControlPanel().Update();
        Logger.finishedView(this, "PickedUpUpdate");
    }

    @Override
    public void UsedUpdate(IVItem item, boolean success) {
        Logger.startedView(this, "UsedUpdated");
        if(success){
            owner.GetControlPanel().LogEvent(owner.GetID() + " használta a " + item.GetID() + " tárgyat.\n");
            owner.GetControlPanel().Update();
        }else{
            owner.GetControlPanel().LogEvent(owner.GetID() + " megpróbálta használni a " + item.GetID() + " tárgyat, de nem sikerült.\n");
        }
        Logger.finishedView(this, "UsedUpdated");
    }

    public void SetOwner(VStudent vst){
        owner = vst;
    }
}
