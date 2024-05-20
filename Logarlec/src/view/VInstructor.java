package view;

import util.Logger;
import viewmodel.IVInstructor;
import viewmodel.IVInstructorUpdate;

public class VInstructor extends VPerson implements IVInstructorUpdate {
    IVInstructor ivInstructor;

    public VInstructor(IVInstructor ivI, ControlPanel cP){
        super(cP);
        ivInstructor = ivI;
        ivInstructor.SetIVInstructorUpdate(this);
        ivInstructor.SetIVPersonUpdate(this);
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

    @Override
    public void Stunned(int duration) {
        controlPanel.LogEvent(GetID() + " oktatót elkábult ennyi kör idejéig: " + duration + "\n");
    }
}
