package view;

import model.Room;
import viewmodel.IVItemUpdate;
import viewmodel.IVRoom;
import viewmodel.IVRoomUpdate;

import javax.swing.*;
import java.util.ArrayList;

public class VRoom extends JPanel implements IVRoomUpdate {
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

    /**
     * Kirajzolató függvény
     * @param panelToDrawOn a panel amire rajzolja magát
     */
    public void Draw(JPanel panelToDrawOn){

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

    @Override
    public VRoom GetVRoom() {
        return this;
    }
}
