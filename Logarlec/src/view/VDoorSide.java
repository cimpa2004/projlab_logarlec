package view;

import viewmodel.IVDoorSide;
import viewmodel.IVDoorSideUpdate;

import javax.swing.*;

public class VDoorSide extends JButton implements IVDoorSideUpdate {
    IVDoorSide ivDoorSide;

    public VDoorSide(IVDoorSide ivDS){
        ivDoorSide = ivDS;
        ivDoorSide.SetIVDoorSideUpdate(this);
    }

}
