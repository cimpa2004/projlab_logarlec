package viewmodel;

import view.VRoom;
import model.Student;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface IVRoom {
    public String GetID();
    public VRoom GetVRoom();
    public void SetIVRoomUpdate(IVRoomUpdate ivRoomUpdate);
    public int GetPoisonDuration();
    public boolean GetIsCursed();
    public ArrayList<IVStudent> GetIVStudents();
    public ArrayList<IVInstructor> GetIVInstructors();
    public ArrayList<IVJanitor> GetIVJanitors();
    public ArrayList<IVItem> GetIVItems();
    public ArrayList<IVDoorSide> GetIVDoors();
}
