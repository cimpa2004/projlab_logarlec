package viewmodel;

import model.Room;
import view.VStudent;

public interface IVStudent {
    public boolean isActiveTurn();
    public IVRoom GetIVRoom();
    public void SetIVStudentUpdate(IVStudentUpdate ivRoomUpdate);
    public void EndTurn();
    public String GetID();
    public Room GetRoom();
}
