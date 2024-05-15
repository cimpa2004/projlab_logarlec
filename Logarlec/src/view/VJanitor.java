package view;

import viewmodel.IVJanitor;
import viewmodel.IVJanitorUpdate;

public class VJanitor extends VPerson implements IVJanitorUpdate{
    IVJanitor ivJanitor;

    public VJanitor(IVJanitor ivJanitor){
        this.ivJanitor = ivJanitor;
    }
}
