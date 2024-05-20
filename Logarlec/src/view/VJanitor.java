package view;

import util.Logger;
import viewmodel.*;

import java.util.ArrayList;

public class VJanitor extends VPerson implements IVJanitorUpdate {
    IVJanitor ivJanitor;

    public VJanitor(IVJanitor ivJ, ControlPanel cP){
        super(cP);
        ivJanitor = ivJ;
        ivJanitor.SetIVJanitorUpdate(this);
        ivJanitor.SetIVPersonUpdate(this);
    }

    @Override
    public String GetID() {
        Logger.startedView(this, "GetID");
        Logger.finishedView(this, "GetID");
        return ivJanitor.GetID();
    }


    @Override
    public void Moved() {
        this.position = null;
    }

    @Override
    public void MadeThemLeave(ArrayList<IPerson> personsLeft, IVRoom ivRoom) {
        StringBuilder strPersons = new StringBuilder();
        for(IPerson person : personsLeft){
            strPersons.append(person.GetID());
            if(person != personsLeft.get(personsLeft.size()-1)) strPersons.append(", ");
        }
        controlPanel.LogEvent(GetID() + " kitessékelte a " + ivRoom.GetID() + " szobából a következő személyeket: " + strPersons + "\n");
    }

    @Override
    public void CleanedRoom(IVRoom cleanedRoom) {
        controlPanel.LogEvent(GetID() + " kitakarította a " + cleanedRoom.GetID() + " szobát." + "\n");
    }
}
