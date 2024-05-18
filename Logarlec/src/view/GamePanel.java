package view;


import org.w3c.dom.css.Rect;
import util.Logger;
import viewmodel.ICRoom;
import viewmodel.IVDoorSide;
import viewmodel.IVRoom;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A pálya részét jelenítetti meg a játéknak
 */
public class GamePanel extends JPanel implements ICRoom, ActionListener {
    private ArrayList<VRoom> rooms = new ArrayList<>();
    private ArrayList<VPerson> people = new ArrayList<>();
    private VRoom currentRoom;
    private ArrayList<RectPanel> rects = new ArrayList<>();
    private ArrayList<RectPanel> doorRects = new ArrayList<>();
    private ArrayList<MapCirclePanel> circles = new ArrayList<>();
    private ArrayList<JButton> itemButtons = new ArrayList<>();
    private ArrayList<JButton> doorButtons = new ArrayList<>();
    //private JPanel buttonsPanel;
    private ControlPanel controlPanel;

    public GamePanel(){
        setLayout(null);
    }

    public void SetControlPanel(ControlPanel cp) {
        controlPanel = cp;
    }

    public VRoom GetCurrentRoom() {
        return currentRoom;
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
            ClearAll();
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
        currentRoom.Draw(this);
        Logger.finishedView(this, "Draw", room);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(RectPanel rect : rects) {
            rect.draw(g);
        }
        for(RectPanel rect : doorRects) {
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

    public void ClearRect() {
        rects.clear();
    }

    public void AddDoorRect(RectPanel rect) { doorRects.add(rect); }

    public void ClearDoorRect() {
        doorRects.clear();
    }

    public void AddCircle(MapCirclePanel circle) {
        circles.add(circle);
    }

    public void ClearCircle() {
        circles.clear();
    }

    public void AddDoorButton(JButton button) { doorButtons.add(button); }

    public void ClearDoorButton() {
        doorButtons.clear();
    }

    public void AddItemButton(JButton button) { itemButtons.add(button); }

    public void ClearItemButton() {
        itemButtons.clear();
    }
    /*public JPanel GetButtonsPanel() {
        return buttonsPanel;
    }*/
    public void ClearAll() {
        ClearRect();
        ClearDoorRect();
        ClearCircle();
        ClearDoorButton();
        ClearItemButton();
        removeAll();
    }

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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(doorButtons.contains((JButton)e.getSource())) {
            JButton dButton = (JButton)e.getSource();
            int doorIndex = doorButtons.indexOf(dButton);
            IVDoorSide doorRef = doorRects.get(doorIndex).GetDoorRef();
            if(controlPanel.GetCurrentStudent().Move(doorRef)) {
                currentRoom = doorRef.GetIVPair().GetIVRoom().GetVRoom();
                controlPanel.GetCurrentStudent().EndTurn();
            }
        }
    }
}
