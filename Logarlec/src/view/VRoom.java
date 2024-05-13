package view;

import model.Room;
import viewmodel.IVItemUpdate;
import viewmodel.IVRoom;

import javax.swing.*;
import java.util.ArrayList;

public class VRoom extends JPanel implements IVItemUpdate {
    /**
     * Inithez kell
     * @param ivRoom
     */
    public VRoom(IVRoom ivRoom){
        this.ivRoom = ivRoom;
        ivRoom.GetRoom().SetIVRoomUpdate(this);
    }
    public void AddDoorSide(VDoorSide vDoorSide){

    }
    private ArrayList<VItem> items;
    private IVRoom ivRoom;

    public ArrayList<VItem> GetItems(){
        return items;
    }
    public void AddVItem(VItem item){
        items.add(item);
    }
    public Room GetRoom(){
        return ivRoom.GetRoom();
    }

}
