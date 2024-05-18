package view;


import util.Logger;
import viewmodel.ICRoom;
import viewmodel.IVRoom;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * A pálya részét jelenítetti meg a játéknak
 */
public class GamePanel extends JPanel implements ICRoom {
    private ArrayList<VRoom> rooms = new ArrayList<>();
    private ArrayList<VPerson> people = new ArrayList<>();
    private VRoom currentRoom;
    private ArrayList<RectPanel> rects = new ArrayList<>();
    private ArrayList<MapCirclePanel> circles = new ArrayList<>();
    private ArrayList<JButton> itemButtons = new ArrayList<>();
    private ArrayList<JButton> doorButtons = new ArrayList<>();
    //private JPanel buttonsPanel;

    public GamePanel(){
        setLayout(null);
    }

    public void AddVPerson(VPerson _new){
        Logger.startedView(this, "AddVPerson", _new);
        people.add(_new);
        Logger.finishedView(this, "AddVPerson", _new);
    }
    public void AddVRoom(VRoom _new){
        Logger.startedView(this, "AddVRoom", _new);
        rooms.add(_new);
        Logger.finishedView(this, "AddVRoom", _new);
    }
    public ArrayList<VRoom> GetRooms(){
        Logger.startedView(this, "GetRooms");
        Logger.finishedView(this, "GetRooms");
        return rooms;
    }

    /**
     * Változás esetén érdemes meghívni, újra rajzolja a szobát
     */
    public void Redraw(){
        Logger.startedView(this, "Redraw");
        if (currentRoom != null){
            Draw(currentRoom);
        }
        Logger.finishedView(this, "Redraw");
    }

    /**
     * Kirajzoltatja a szobát
     * @param room a kirajzolandó VRoom
     */

    public void Draw(VRoom room){
        Logger.startedView(this, "Draw", room);
        if (room == null){
            throw new RuntimeException("Null roomot nem lehet kirajzolni");
        }
        currentRoom = room;
        ResetRoom();
        currentRoom.Draw(this);
        Logger.finishedView(this, "Draw", room);
    }

    private void ResetRoom(){
        Logger.startedView(this, "ResetMap");
        this.rects.clear();
        this.circles.clear();
        Logger.finishedView(this, "ResetMap");
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(RectPanel rect : rects) {
            rect.draw(g);
        }
        for(MapCirclePanel circle : circles) {
            circle.draw(g);
        }
    }

    //TODO:az itemek és az emberek lehet nem jól lesznek átrakva

    /**
     *  Felveszi az új szobát, nem lesz jó így
     * @param ivRoom the old room, already exists
     * @param _new the new room, needs to be created
     */
    @Override
    public void Split(IVRoom ivRoom, IVRoom _new) {
        Logger.startedView(this, "Split", ivRoom, _new);
        VRoom newRoom = new VRoom(_new);
        rooms.add(newRoom);
        Logger.finishedView(this, "Split", ivRoom, _new);
    }

    public void AddRect(RectPanel rect) { rects.add(rect); }

    public void RemoveRect(RectPanel rect) {
        rects.remove(rect);
    }

    public void AddCircle(MapCirclePanel circle) {
        circles.add(circle);
    }

    public void RemoveCircle(MapCirclePanel circle) {
        circles.remove(circle);
    }

    public void AddDoorButton(JButton button) { doorButtons.add(button); }

    public void RemoveDoorButton(JButton button) {
        doorButtons.remove(button);
    }

    public void AddItemButton(JButton button) { itemButtons.add(button); }

    public void RemoveItemButton(JButton button) {
        itemButtons.remove(button);
    }
    /*public JPanel GetButtonsPanel() {
        return buttonsPanel;
    }*/

    /**
     * Osszeolvasztja a ket szobat, pontosabban az ivRoom2-t olvasztja bele az ivRoom1-be. Igy az ivRoom2 megszunik.
     * @param ivRoom1
     * @param ivRoom2
     */
    @Override
    public void Merge(IVRoom ivRoom1, IVRoom ivRoom2) {
        Logger.startedView(this, "Merge", ivRoom1, ivRoom2);
        rooms.remove(ivRoom2.GetVRoom());
        Logger.finishedView(this, "Merge", ivRoom1, ivRoom2);
    }
}
