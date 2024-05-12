package view;

import controller.Game;
import viewmodel.ICRoom;
import viewmodel.IVRoom;

import javax.swing.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ICRoom {

    private ArrayList<VRoom> rooms = new ArrayList<>();
    private ArrayList<VPerson> People = new ArrayList<VPerson>();
    public GamePanel(){

    }

    //TODO: impelemt these
    @Override
    public void Split(IVRoom ivRoom) {

    }

    @Override
    public void Merge(IVRoom ivRoom1, IVRoom ivRoom2) {

    }
}
