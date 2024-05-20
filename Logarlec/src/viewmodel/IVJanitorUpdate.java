package viewmodel;

import java.util.ArrayList;

public interface IVJanitorUpdate {
    public void Moved();
    public void MadeThemLeave(ArrayList<IPerson> studentsLeft, IVRoom fromIvRoom);
}
