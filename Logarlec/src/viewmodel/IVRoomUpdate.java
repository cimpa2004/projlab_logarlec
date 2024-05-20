package viewmodel;

import view.VRoom;

public interface IVRoomUpdate {
    public VRoom GetVRoom();
    public void RoomSetPoisonous(int duration);
}
