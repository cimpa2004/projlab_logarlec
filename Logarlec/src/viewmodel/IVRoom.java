package viewmodel;

import model.Room;

public interface IVRoom {
    public String GetID();
    public Room GetRoom();
    public void SetIVRoomUpdate(IVRoomUpdate ivRoomUpdate);
}
