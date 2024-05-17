package viewmodel;

import model.Room;

public interface IVStudent {
    public boolean IsActiveTurn();
    public IVRoom GetIVRoom();
    public void SetIVStudentUpdate(IVStudentUpdate ivRoomUpdate);
    public void EndTurn();
    public String GetID();
    public Room GetRoom();
}
