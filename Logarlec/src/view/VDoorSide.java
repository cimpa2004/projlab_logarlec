package view;

import viewmodel.IVDoorSide;

import javax.swing.*;

public class VDoorSide extends JButton {
    IVDoorSide ivDoorSide;

    public VDoorSide(IVDoorSide ivDS){
        ivDoorSide = ivDS;
    }
}
