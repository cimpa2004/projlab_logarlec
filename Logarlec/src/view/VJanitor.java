package view;

import util.Logger;
import viewmodel.IVJanitor;
import viewmodel.IVJanitorUpdate;

public class VJanitor extends VPerson implements IVJanitorUpdate{
    IVJanitor ivJanitor;

    public VJanitor(IVJanitor ivJanitor){
        this.ivJanitor = ivJanitor;
    }

    @Override
    public String GetID() {
        Logger.startedView(this, "GetID");
        Logger.finishedView(this, "GetID");
        return ivJanitor.GetID();
    }
}
