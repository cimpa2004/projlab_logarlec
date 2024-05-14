package viewmodel;

import model.Room;
import view.VStudent;

public interface IVStudent {
    public boolean isActiveTurn();
    public void SetVStudent(VStudent vst);
    public void EndTurn();
    public String GetID();
    public Room GetRoom();
}
