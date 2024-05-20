package view;

import model.Room;
import util.Logger;
import viewmodel.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VStudent extends VPerson implements IVStudentUpdate {
    private IVStudent ivStudent;
    private ControlPanel controlPanel;

    public ICInput input;

    private Dimension position;

    /**
     * Vissza adja a VStudent poziciojat a jelenlegi szobaban
     * @return a pozicioja
     */
    public Dimension GetPosition(){
        return this.position;
    }

    /**
     * Beallithato a VStudent pozioja a szobaban
     * @param position az uj pozicio
     */
    public void SetPosition(Dimension position){
        this.position = position;
    }

    public List<VItem> GetItems(){
        Logger.startedView(this, "GetItems");
        Logger.finishedView(this, "GetItems");

        // TODO ez jó?
        List<IVItem> ivItems = input.GetInventory(GetID());
        List<VItem> vItems = new ArrayList<>();

        for(IVItem item : ivItems){
            vItems.add(item.GetIVItemUpdate());
        }

        return vItems;
    }
    public boolean GetIsActiveTurn(){
        Logger.startedView(this, "GetIsActiveTurn");
        boolean isActiveTurn = ivStudent.IsActiveTurn();
        Logger.finishedView(this, "GetIsActiveTurn");
        return isActiveTurn;
    }
    public void EndTurn(){
        Logger.startedView(this, "EndTurn");
        ivStudent.EndTurn();
        Logger.finishedView(this, "EndTurn");
    }
    public boolean Move(IVDoorSide d) {
        Logger.startedView(this, "Move");
        Logger.finishedView(this, "Move");
        boolean isMoved = ivStudent.IVMove(d);
        if (isMoved) this.position = null;
        return isMoved;
    }

    /**
     * Control panelben használom, a gombnak kell
     * @param item : a felvenni kívánt tárgy
     */
    public void Pickup(VItem item) {
        Logger.startedView(this, "Pickup", item);
        item.PickedUp(this);
        Logger.finishedView(this, "Pickup", item);
    }


    @Override
    public String toString(){
        return ivStudent.GetID();
    }

    public VStudent(IVStudent ivS,ControlPanel cP, ICInput in){
        controlPanel = cP;
        input = in;
        controlPanel.AddVStudent(this);
        ivStudent = ivS;
        ivStudent.SetIVStudentUpdate(this);
    }

    public Room GetRoom(){
        Logger.startedView(this, "GetRoom");
        Logger.finishedView(this, "GetRoom");
        return ivStudent.GetRoom();
    }

    @Override
    public String GetID() {
        Logger.startedView(this, "GetID");
        Logger.finishedView(this, "GetID");
        return ivStudent.GetID();
    }

    public ControlPanel GetControlPanel(){
        return controlPanel;
    }

    /**
     * amikor die következik be
     */
    @Override
    public void Died() {
        controlPanel.LogEvent(GetID() + " játékost megölte egy oktató!\n");
        controlPanel.UpdateAll();
    }
}
