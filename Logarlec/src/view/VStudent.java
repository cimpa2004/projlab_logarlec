package view;

import model.Room;
import util.Logger;
import viewmodel.ICInput;
import viewmodel.IVStudent;

import java.util.List;

public class VStudent extends VPerson{
    private IVStudent ivStudent;
    private ControlPanel controlPanel;
    public ICInput input;

    public List<VItem> GetItems(){
        Logger.startedView(this, "GetItems");
        Logger.finishedView(this, "GetItems");

        return null;
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

    /**
     * Control panelben használom, a gombnak kell
     * @param item : a felvenni kívánt tárgy
     */
    public void Pickup(VItem item) {
        Logger.startedView(this, "Pickup", item);
        Logger.finishedView(this, "Pickup", item);
    }


    @Override
    public String toString(){
        return ivStudent.GetID();
    }

    public VStudent(IVStudent ivStudent,ControlPanel controlPanel, ICInput input){
        this.controlPanel = controlPanel;
        this.input = input;
        //NE töröld
        this.controlPanel.AddVStudent(this);
        this.ivStudent = ivStudent;
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
}
