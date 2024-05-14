package view;

import viewmodel.*;
import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame implements ICInit {
    private GamePanel gamePanel = new GamePanel();
    private ControlPanel controlPanel = new ControlPanel(gamePanel);

    public GamePanel GetGamePanel(){
        return gamePanel;
    }
    public ControlPanel GetControlPanel(){
        return controlPanel;
    }

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

    @Override
    public void CreateVInstructor(IVInstructor ivInstructor) {
        gamePanel.AddVPerson(new VInstructor(ivInstructor));
    }

    @Override
    public void CreateVDoorSide(IVDoorSide ivDoorSide) {
        VRoom doorSideVRoom = ivDoorSide.GetIVRoom().GetVRoom();
        doorSideVRoom.AddDoorSide(new VDoorSide(ivDoorSide));
    }

    @Override
    public void CreateVAirFreshener(IVAirFreshener ivAirFreshener) {
        VRoom airFreshenerVRoom = ivAirFreshener.GetIVRoom().GetVRoom();
        airFreshenerVRoom.AddVItem(new VAirFreshener(ivAirFreshener));
    }

    @Override
    public void CreateVTVSZ(IVTVSZ ivTVSZ) {
        VRoom tvszVRoom = ivTVSZ.GetIVRoom().GetVRoom();
        tvszVRoom.AddVItem(new VTVSZ(ivTVSZ));
    }

    @Override
    public void CreateVHolyBeerCup(IVHolyBeerCup ivHolyBeerCup) {
        VRoom holyBeerCupVRoom = ivHolyBeerCup.GetIVRoom().GetVRoom();
        holyBeerCupVRoom.AddVItem(new VHolyBeerCup(ivHolyBeerCup));
    }

    @Override
    public void CreateVWetTableCloth(IVWetTableCloth ivWetTableCloth) {
        VRoom wetTableClothVRoom = ivWetTableCloth.GetIVRoom().GetVRoom();
        wetTableClothVRoom.AddVItem(new VWetTableCloth(ivWetTableCloth));
    }

    @Override
    public void CreateVCamembert(IVCamembert ivCamembert) {
        VRoom camembertVRoom = ivCamembert.GetIVRoom().GetVRoom();
        camembertVRoom.AddVItem(new VCamembert(ivCamembert));
    }

    @Override
    public void CreateVFFP2Mask(IVFFP2Mask ivFFP2Mask) {
        VRoom ffp2MaskVRoom = ivFFP2Mask.GetIVRoom().GetVRoom();
        ffp2MaskVRoom.AddVItem(new VFFP2Mask(ivFFP2Mask));
    }

    @Override
    public void CreateVSlideRule(IVSlideRule ivSlideRule) {
        VRoom slideRuleVRoom = ivSlideRule.GetIVRoom().GetVRoom();
        slideRuleVRoom.AddVItem(new VSlideRule(ivSlideRule));
    }

    @Override
    public void CreateVRoom(IVRoom ivRoom) {
        gamePanel.AddVRoom(new VRoom(ivRoom));
    }

    @Override
    public void CreateVTransistor(IVTransistor ivTransistor) {
        VRoom transistorVRoom = ivTransistor.GetIVRoom().GetVRoom();
        transistorVRoom.AddVItem(new VTransistor(ivTransistor));
    }

    @Override
    public void CreateVJanitor(IVJanitor ivJanitor) {
        gamePanel.AddVPerson(new VJanitor(ivJanitor));
    }

    @Override
    public void CreateVStudent(IVStudent ivStudent,ICInput icInput) {
        //konstuktorban hozzá adja magát a control panelhez
        VStudent student = new VStudent(ivStudent,controlPanel,icInput);
        gamePanel.AddVPerson(student);
    }
}
