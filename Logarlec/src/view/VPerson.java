package view;

import viewmodel.ICInput;

import java.awt.*;

public abstract class VPerson {

    protected Dimension position;

    protected ControlPanel controlPanel;

    public VPerson(ControlPanel cP){
        this.controlPanel = cP;
    }

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
    public ControlPanel GetControlPanel(){
        return this.controlPanel;
    }
}
