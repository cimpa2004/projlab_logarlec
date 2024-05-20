package view;

import viewmodel.ICInput;

import java.awt.*;

public abstract class VPerson {

    protected Dimension position;

    /**
     * Vissza adja a VPerson poziciojat a jelenlegi szobaban
     * @return a pozicioja
     */
    public Dimension GetPosition(){
        return this.position;
    }

    /**
     * Beallithato a VPerson pozioja a szobaban
     * @param position az uj pozicio
     */
    public void SetPosition(Dimension position){
        this.position = position;
    }

    public abstract String GetID();
    public abstract ControlPanel GetControlPanel();
}
