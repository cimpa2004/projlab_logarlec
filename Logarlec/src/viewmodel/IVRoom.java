package viewmodel;

import view.VRoom;

public interface IVRoom {
    public String GetID();
    public VRoom GetVRoom();
    public void SetIVRoomUpdate(IVRoomUpdate ivRoomUpdate);
}
