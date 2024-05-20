package view;

import util.Logger;
import viewmodel.IVItem;
import viewmodel.IVItemUpdate;

import javax.swing.*;


public abstract class VItem implements IVItemUpdate {
    private String ID;
    private VRoom room;
    VPerson owner;
    public String GetID(){
        Logger.startedModel(this, "GetID");
        Logger.finishedModel(this, "GetID");
        return ID;
    }
    public abstract void PickedUp(VStudent owner);
    public abstract CirclePanel DrawOnMap();
    public abstract void DrawInInventory(JPanel panel, VStudent student, boolean buttons);
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
        owner.GetControlPanel().UpdateAll();
        Logger.finishedView(this, "ThrownUpdate");
    }

    @Override
    public void PickedUpUpdate(IVItem item, boolean success) {
        Logger.startedView(this, "PickedUpUpdate");
        if(success){
            owner.GetControlPanel().LogEvent(owner.GetID() + " felvette a " + item.GetID() + " tárgyat.\n");
            owner.GetControlPanel().UpdateAll();
        }else{
            // TODO itt nincs owner, de kell a controlpanel
            //owner.GetControlPanel().LogEvent(owner.GetID() + " nem tudta felvenni a " + item.GetID() + " tárgyat.\n");
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
}
