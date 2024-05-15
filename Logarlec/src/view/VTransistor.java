package view;

import viewmodel.IVTransistor;

public class VTransistor extends VItem{
    IVTransistor ivTransistor;

    public VTransistor(IVTransistor ivTransistor){
        this.ivTransistor = ivTransistor;
        this.ivTransistor.SetIVItemUpdate(this);
    }
}
