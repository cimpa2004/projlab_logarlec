package view;

import viewmodel.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame implements ICInit {
    private GamePanel gamePanel = new GamePanel();
    private ControlPanel controlPanel = new ControlPanel();


    public MainFrame(){
        setTitle("Logarléc");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        add(gamePanel);
        add(controlPanel);

    }

    //TODO: implement these
    @Override
    public void CreateVInstructor(IVInstructor ivInstructor) {

    }

    @Override
    public void CreateVDoorSide(IVDoorSide ivDoorSide) {

    }

    @Override
    public void CreateVAirFreshener(IVAirFreshener ivAirFreshener) {

    }

    @Override
    public void CreateVTVSZ(IVTVSZ ivTVSZ) {

    }

    @Override
    public void CreateVHolyBeerCup(IVHolyBeerCup ivHolyBeerCup) {

    }

    @Override
    public void CreateVWetTableCloth(IVWetTableCloth ivWetTableCloth) {

    }

    @Override
    public void CreateVCamembert(IVCamembert ivCamembert) {

    }

    @Override
    public void CreateVFFP2Mask(IVFFP2Mask ivFFP2Mask) {

    }

    @Override
    public void CreateVSlideRule(IVSlideRule ivSlideRule) {

    }

    @Override
    public void CreateVRoom(IVRoom ivRoom) {

    }

    @Override
    public void CreateVTransistor(IVTransistor ivTransistor) {

    }

    @Override
    public void CreateVJanitor(IVJanitor ivJanitor) {

    }

    @Override
    public void CreateVStudent(IVStudent ivStudent) {

    }
}
