package view;


import viewmodel.ICRoom;
import viewmodel.IVRoom;

import javax.swing.*;
import java.util.ArrayList;

/**
 * A pálya részét jelenítetti meg a játéknak
 */
public class GamePanel extends JPanel implements ICRoom {
    private ArrayList<VRoom> rooms = new ArrayList<>();
    private ArrayList<VPerson> people = new ArrayList<>();
    private VRoom currentRoom;

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

    /**
     * Változás esetén érdemes meghívni, újra rajzolja a szobát
     */
    public void Redraw(){
        if (currentRoom != null){
            Draw(currentRoom);
        }
    }

    /**
     * Kirajzoltatja a szobát
     * @param room a kirajzolandó VRoom
     */

    public void Draw(VRoom room){
        if (room == null){
            throw new RuntimeException("Null roomot nem lehet kirajzolni");
        }
        currentRoom = room;
        currentRoom.Draw(this);
    }

    //TODO:az itemek és az emberek lehet nem jó lesznek átrakva

    /**
     *  Felveszi az új szobát, nem lesz jó így
     * @param ivRoom the old room, already exists
     * @param _new the new room, needs to be created
     */
    @Override
    public void Split(IVRoom ivRoom, IVRoom _new) {
        VRoom newRoom = new VRoom(_new);
        rooms.add(newRoom);
    }

    /**
     * Osszeolvasztja a ket szobat, pontosabban az ivRoom2-t olvasztja bele az ivRoom1-be. Igy az ivRoom2 megszunik.
     * @param ivRoom1
     * @param ivRoom2
     */
    @Override
    public void Merge(IVRoom ivRoom1, IVRoom ivRoom2) {
        rooms.remove(ivRoom2.GetVRoom());
    }
}
