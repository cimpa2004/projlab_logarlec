package view;

import viewmodel.IVRoom;
import viewmodel.IVRoomUpdate;

import javax.swing.*;
import java.util.ArrayList;

public class VRoom extends JPanel implements IVRoomUpdate {
    private ArrayList<VItem> items = new ArrayList<>();
    private IVRoom ivRoom;

    /**
     * Inithez kell
     * @param ivRoom
     */
    public VRoom(IVRoom ivRoom){
        this.ivRoom = ivRoom;
        ivRoom.SetIVRoomUpdate(this);
    }
    public void AddDoorSide(VDoorSide vDoorSide){

    }

    /**
     * Kirajzolató függvény
     * @param panelToDrawOn a panel amire rajzolja magát
     */
    public void Draw(JPanel panelToDrawOn){

    }

    public ArrayList<VItem> GetItems(){
        return items;
    }
    public void AddVItem(VItem item){
        items.add(item);
    }

    public IVRoom GetIVRoom(){
        return ivRoom;
    }

    @Override
    public VRoom GetVRoom() {
        return this;
    }
}
