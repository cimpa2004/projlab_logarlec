package view;

import model.Room;
import org.w3c.dom.css.Rect;
import viewmodel.ICInput;
import viewmodel.IVStudent;
import viewmodel.IVTVSZ;

import java.util.List;

public class VStudent extends VPerson{
    private IVStudent ivStudent;
    private ControlPanel controlPanel;
    private ICInput input;

    public List<VItem> getItems(){
        return null;
    }
    public boolean getIsActiveTurn(){
        return ivStudent.isActiveTurn(); 
    }
    public void EndTurn(){
        ivStudent.EndTurn();
    }

    /**
     * Control panelben használom, a gombnak kell
     * @param item : a felvenni kívánt tárgy
     */
    public void Pickup(VItem item){

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
        return ivStudent.GetRoom();
    }

    @Override
    public String GetID() {
        return null;
    }
}
