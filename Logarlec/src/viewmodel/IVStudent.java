package viewmodel;

import model.Room;
import view.VStudent;

public interface IVStudent extends IVPerson{
    public boolean IsActiveTurn();
    public IVRoom GetIVRoom();
    public void SetIVStudentUpdate(IVStudentUpdate ivRoomUpdate);
    public VStudent GetIVStudentUpdate();
    public void EndTurn();
    public String GetID();
    public Room GetRoom();
    public boolean GetIsActiveTurn();
    public boolean GetIsAlive();
    public boolean GetIsFainted();
    public boolean IVMove(IVDoorSide d);
}
