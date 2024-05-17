package view;

import util.Logger;
import viewmodel.*;
import javax.swing.*;
import java.awt.*;

/**
 * A játék fő ablaka tárolja a gamePanelt és a ControlPanelt
 */
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
        setLayout(new GridLayout(0, 1));
        Image icon = Toolkit.getDefaultToolkit().getImage("sliderule.jpg");
        setIconImage(icon);
        add(gamePanel);
        add(controlPanel);

    }
    @Override
    public void CreateVRoom(IVRoom ivRoom) {
        Logger.startedView(this, "CreateVRoom", ivRoom);
        gamePanel.AddVRoom(new VRoom(ivRoom));
        Logger.finishedView(this, "CreateVRoom", ivRoom);
    }

    @Override
    public void CreateVDoorSide(IVDoorSide ivDoorSide) {
        Logger.startedView(this, "CreateVDoorSide", ivDoorSide);
        VRoom doorSideVRoom = ivDoorSide.GetIVRoom().GetVRoom();
        doorSideVRoom.AddDoorSide(new VDoorSide(ivDoorSide));
        Logger.finishedView(this, "CreateVDoorSide", ivDoorSide);
    }

    @Override
    public void CreateVAirFreshener(IVAirFreshener ivAirFreshener) {
        Logger.startedView(this, "CreateVAirFreshener", ivAirFreshener);
        VRoom airFreshenerVRoom = ivAirFreshener.GetIVRoom().GetVRoom();
        airFreshenerVRoom.AddVItem(new VAirFreshener(ivAirFreshener));
        Logger.finishedView(this, "CreateVAirFreshener", ivAirFreshener);
    }

    @Override
    public void CreateVTVSZ(IVTVSZ ivTVSZ) {
        Logger.startedView(this, "CreateVTVSZ", ivTVSZ);
        VRoom tvszVRoom = ivTVSZ.GetIVRoom().GetVRoom();
        tvszVRoom.AddVItem(new VTVSZ(ivTVSZ));
        Logger.finishedView(this, "CreateVTVSZ", ivTVSZ);
    }

    @Override
    public void CreateVHolyBeerCup(IVHolyBeerCup ivHolyBeerCup) {
        Logger.startedView(this, "CreateVHolyBeerCup", ivHolyBeerCup);
        VRoom holyBeerCupVRoom = ivHolyBeerCup.GetIVRoom().GetVRoom();
        holyBeerCupVRoom.AddVItem(new VHolyBeerCup(ivHolyBeerCup));
        Logger.finishedView(this, "CreateVHolyBeerCup", ivHolyBeerCup);
    }

    @Override
    public void CreateVWetTableCloth(IVWetTableCloth ivWetTableCloth) {
        Logger.startedView(this, "CreateVWetTableCloth", ivWetTableCloth);
        VRoom wetTableClothVRoom = ivWetTableCloth.GetIVRoom().GetVRoom();
        wetTableClothVRoom.AddVItem(new VWetTableCloth(ivWetTableCloth));
        Logger.finishedView(this, "CreateVWetTableCloth", ivWetTableCloth);
    }

    @Override
    public void CreateVCamembert(IVCamembert ivCamembert) {
        Logger.startedView(this, "CreateVCamembert", ivCamembert);
        VRoom camembertVRoom = ivCamembert.GetIVRoom().GetVRoom();
        camembertVRoom.AddVItem(new VCamembert(ivCamembert));
        Logger.finishedView(this, "CreateVCamembert", ivCamembert);
    }

    @Override
    public void CreateVFFP2Mask(IVFFP2Mask ivFFP2Mask) {
        Logger.startedView(this, "CreateVFFP2Mask", ivFFP2Mask);
        VRoom ffp2MaskVRoom = ivFFP2Mask.GetIVRoom().GetVRoom();
        ffp2MaskVRoom.AddVItem(new VFFP2Mask(ivFFP2Mask));
        Logger.finishedView(this, "CreateVFFP2Mask", ivFFP2Mask);
    }

    @Override
    public void CreateVSlideRule(IVSlideRule ivSlideRule) {
        Logger.startedView(this, "CreateVSlideRule", ivSlideRule);
        VRoom slideRuleVRoom = ivSlideRule.GetIVRoom().GetVRoom();
        slideRuleVRoom.AddVItem(new VSlideRule(ivSlideRule));
        Logger.finishedView(this, "CreateVSlideRule", ivSlideRule);
    }

    @Override
    public void CreateVTransistor(IVTransistor ivTransistor) {
        Logger.startedView(this, "CreateVTransistor", ivTransistor);
        VRoom transistorVRoom = ivTransistor.GetIVRoom().GetVRoom();
        transistorVRoom.AddVItem(new VTransistor(ivTransistor));
        Logger.finishedView(this, "CreateVTransistor", ivTransistor);
    }

    @Override
    public void CreateVJanitor(IVJanitor ivJanitor) {
        Logger.startedView(this, "CreateVJanitor", ivJanitor);
        gamePanel.AddVPerson(new VJanitor(ivJanitor));
        Logger.finishedView(this, "CreateVJanitor", ivJanitor);
    }

    @Override
    public void CreateVStudent(IVStudent ivStudent, ICInput icInput) {
        Logger.startedView(this, "CreateVStudent", ivStudent, icInput);
        //konstuktorban hozzá adja magát a control panelhez
        VStudent student = new VStudent(ivStudent,controlPanel,icInput);
        gamePanel.AddVPerson(student);
        Logger.finishedView(this, "CreateVStudent", ivStudent, icInput);
    }

    @Override
    public void CreateVInstructor(IVInstructor ivInstructor) {
        Logger.startedView(this, "CreateVInstructor", ivInstructor);
        gamePanel.AddVPerson(new VInstructor(ivInstructor));
        Logger.finishedView(this, "CreateVInstructor", ivInstructor);
    }
}
