package viewmodel;

import view.VInstructor;

public interface IVInstructor {
    public String GetID();
    public IVRoom GetIVRoom();
    public void SetIVInstructorUpdate(IVInstructorUpdate ivInstructorUpdate);
    public VInstructor GetIVInstructorUpdate();
    public boolean GetIsFainted();
    public boolean GetIsStunned();
}
