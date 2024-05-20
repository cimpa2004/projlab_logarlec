package viewmodel;

import model.DoorSide;

public interface IVStudentUpdate {

    void Died();
    void Moved(IVDoorSide doorFromInNewRoom);
}
