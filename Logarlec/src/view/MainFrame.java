package view;

import model.DoorSide;
import model.Item;
import viewmodel.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

    //TODO: .equalsokban lehet hiba
    @Override
    public void CreateVInstructor(IVInstructor ivInstructor) {
        gamePanel.AddVPerson(new VInstructor(ivInstructor));
    }

    @Override
    public void CreateVDoorSide(IVDoorSide ivDoorSide) {
        for (VRoom room: gamePanel.GetRooms()) {
            for (DoorSide doorSide:room.GetRoom().GetDoors()) {
                if (doorSide.equals(ivDoorSide)){
                    room.AddDoorSide(new VDoorSide(ivDoorSide));
                    break;
                }
            }
        }
    }

    @Override
    public void CreateVAirFreshener(IVAirFreshener ivAirFreshener) {
        for (VRoom room: gamePanel.GetRooms()) {
            for (Item item:room.GetRoom().GetItems()) {
                if (item.equals(ivAirFreshener)){
                    room.AddVItem(new VAirFreshener(ivAirFreshener));
                    break;
                }
            }
        }

    }

    @Override
    public void CreateVTVSZ(IVTVSZ ivTVSZ) {
        for (VRoom room: gamePanel.GetRooms()) {
            for (Item item:room.GetRoom().GetItems()) {
                if (item.equals(ivTVSZ)){
                    room.AddVItem(new VTVSZ(ivTVSZ));
                    break;
                }
            }
        }
    }

    @Override
    public void CreateVHolyBeerCup(IVHolyBeerCup ivHolyBeerCup) {
        for (VRoom room: gamePanel.GetRooms()) {
            for (Item item:room.GetRoom().GetItems()) {
                if (item.equals(ivHolyBeerCup)){
                    room.AddVItem(new VHolyBeerCup(ivHolyBeerCup));
                    break;
                }
            }
        }
    }

    @Override
    public void CreateVWetTableCloth(IVWetTableCloth ivWetTableCloth) {
        for (VRoom room: gamePanel.GetRooms()) {
            for (Item item:room.GetRoom().GetItems()) {
                if (item.equals(ivWetTableCloth)){
                    room.AddVItem(new VWetTableCloth(ivWetTableCloth));
                    break;
                }
            }
        }
    }

    @Override
    public void CreateVCamembert(IVCamembert ivCamembert) {
        for (VRoom room: gamePanel.GetRooms()) {
            for (Item item:room.GetRoom().GetItems()) {
                if (item.equals(ivCamembert)){
                    room.AddVItem(new VCamembert(ivCamembert));
                    break;
                }
            }
        }
    }

    @Override
    public void CreateVFFP2Mask(IVFFP2Mask ivFFP2Mask) {
        for (VRoom room: gamePanel.GetRooms()) {
            for (Item item:room.GetRoom().GetItems()) {
                if (item.equals(ivFFP2Mask)){
                    room.AddVItem(new VFFP2Mask(ivFFP2Mask));
                    break;
                }
            }
        }
    }

    @Override
    public void CreateVSlideRule(IVSlideRule ivSlideRule) {
        for (VRoom room: gamePanel.GetRooms()) {
            for (Item item:room.GetRoom().GetItems()) {
                if (item.equals(ivSlideRule)){
                    room.AddVItem(new VSlideRule(ivSlideRule));
                    break;
                }
            }
        }
    }

    @Override
    public void CreateVRoom(IVRoom ivRoom) {
        gamePanel.AddVRoom(new VRoom(ivRoom));
    }

    @Override
    public void CreateVTransistor(IVTransistor ivTransistor) {
        for (VRoom room: gamePanel.GetRooms()) {
            for (Item item:room.GetRoom().GetItems()) {
                if (item.equals(ivTransistor)){
                    room.AddVItem(new VTransistor(ivTransistor));
                    break;
                }
            }
        }
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
