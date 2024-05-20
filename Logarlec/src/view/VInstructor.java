package view;

import util.Logger;
import viewmodel.IVInstructor;
import viewmodel.IVInstructorUpdate;

public class VInstructor extends VPerson implements IVInstructorUpdate {
    IVInstructor ivInstructor;
    private ControlPanel controlPanel;

    public VInstructor(IVInstructor ivI, ControlPanel cP){

        ivInstructor = ivI;
        ivInstructor.SetIVInstructorUpdate(this);
    }


    public ControlPanel GetControlPanel() {
        return controlPanel;
    }
    @Override
    public String GetID() {
        Logger.startedView(this, "GetID");
        Logger.finishedView(this, "GetID");
        return ivInstructor.GetID();
    }

    @Override
    public void Moved() {
        this.position = null;
    }
}
