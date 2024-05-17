package view;

import util.Logger;
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
        Logger.startedView(this, "AddDoorSide", vDoorSide);
        Logger.finishedView(this, "AddDoorSide", vDoorSide);
    }

    /**
     * Kirajzolató függvény
     * @param panelToDrawOn a panel amire rajzolja magát
     */
    public void Draw(JPanel panelToDrawOn){
        Logger.startedView(this, "Draw", panelToDrawOn);
        Logger.finishedView(this, "Draw", panelToDrawOn);
    }

    public ArrayList<VItem> GetItems(){
        Logger.startedView(this, "GetItems");
        Logger.finishedView(this, "GetItems");
        return items;
    }
    public void AddVItem(VItem item){
        Logger.startedView(this, "AddVItem", item);
        Logger.finishedView(this, "AddVItem", item);
        items.add(item);
    }

    public IVRoom GetIVRoom(){
        Logger.startedView(this, "GetIVRoom");
        Logger.finishedView(this, "GetIVRoom");
        return ivRoom;
    }

    @Override
    public VRoom GetVRoom() {
        Logger.startedView(this, "GetVRoom");
        Logger.finishedView(this, "GetVRoom");
        return this;
    }
}
