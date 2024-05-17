package viewmodel;

public interface IVInstructor {
    public String GetID();
    public IVRoom GetIVRoom();
    public void SetIVInstructorUpdate(IVInstructorUpdate ivInstructorUpdate);
    public boolean GetIsFainted();
    public boolean GetIsStunned();
}
