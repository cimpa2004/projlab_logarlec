package view;

import controller.Game;
import viewmodel.ICRoom;
import viewmodel.IVRoom;

import javax.swing.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ICRoom {

    private ArrayList<VRoom> rooms = new ArrayList<>();
    private ArrayList<VPerson> people = new ArrayList<VPerson>();
    public GamePanel(){

    }

    public void AddVPerson(VPerson _new){
        people.add(_new);
    }
    public void AddVRoom(VRoom _new){
        rooms.add(_new);
    }
    public ArrayList<VRoom> GetRooms(){
        return rooms;
    }

    //TODO: impelemt these
    @Override
    public void Split(IVRoom ivRoom) {

    }

    @Override
    public void Merge(IVRoom ivRoom1, IVRoom ivRoom2) {

    }
}
