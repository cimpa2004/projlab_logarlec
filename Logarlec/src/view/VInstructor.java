package view;

import util.Logger;
import viewmodel.IVInstructor;

public class VInstructor extends VPerson{
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
