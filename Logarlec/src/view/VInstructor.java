package view;

import util.Logger;
import viewmodel.IVInstructor;
import viewmodel.IVInstructorUpdate;

public class VInstructor extends VPerson implements IVInstructorUpdate {
    IVInstructor ivInstructor;

    public VInstructor(IVInstructor ivInstructor){
        this.ivInstructor = ivInstructor;
    }

    @Override
    public String GetID() {
        Logger.startedView(this, "GetID");
        Logger.finishedView(this, "GetID");
        return ivInstructor.GetID();
    }
}
