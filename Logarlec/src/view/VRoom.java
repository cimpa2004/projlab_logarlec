package view;

import model.DoorSide;
import viewmodel.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class VRoom extends JPanel implements IVRoomUpdate {
    private ArrayList<VItem> items = new ArrayList<>();
    private IVRoom ivRoom;
    private ArrayList<Dimension> doorPositions;


    /**
     * Inithez kell
     * @param ivRoom
     */
    public VRoom(IVRoom ivRoom){
        this.ivRoom = ivRoom;
        ivRoom.SetIVRoomUpdate(this);
    }
    public void AddDoorSide(VDoorSide vDoorSide){

    }

    /**
     * Kirajzolató függvény
     * @param panelToDrawOn a panel amire rajzolja magát
     */
    public void Draw(GamePanel panelToDrawOn){
        panelToDrawOn.setPreferredSize(new Dimension(1000, 400));
        panelToDrawOn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5, true)); // Add border for visualization

        //set room color
        if(ivRoom.GetPoisonDuration() != 0)
            if(ivRoom.GetIsCursed())
                panelToDrawOn.setBackground(new Color(16, 167, 137));
            else
                panelToDrawOn.setBackground(new Color(206, 231, 64));
        else
            if(ivRoom.GetIsCursed())
                panelToDrawOn.setBackground(new Color(250, 199, 16));
            else
                panelToDrawOn.setBackground(new Color(128, 128, 128));

        for(IVStudent student : ivRoom.GetIVStudents()) {
            Dimension drawPos = GetRandomRoomPos(panelToDrawOn,10);
            panelToDrawOn.AddRect(new RectPanel(new Color(144, 209, 79),
                    drawPos, new Dimension(50, 50)));
            if(student.GetIsActiveTurn())
                panelToDrawOn.AddRect(new RectPanel(new Color(242, 70, 38),
                        drawPos, new Dimension(20, 20)));
            else if(!student.GetIsAlive())
                panelToDrawOn.AddRect(new RectPanel(new Color(26, 26, 26),
                        drawPos, new Dimension(20, 20)));
            else if(student.GetIsFainted())
                panelToDrawOn.AddRect(new RectPanel(new Color(16, 167, 137),
                        drawPos, new Dimension(20, 20)));
        }

        for(IVInstructor instructor : ivRoom.GetIVInstructors()) {
            Dimension drawPos = GetRandomRoomPos(panelToDrawOn,10);
            panelToDrawOn.AddRect(new RectPanel(new Color(242, 70, 38),
                    drawPos, new Dimension(50, 50)));
            if(instructor.GetIsStunned())
                panelToDrawOn.AddRect(new RectPanel(new Color(218, 0, 99),
                        drawPos, new Dimension(20, 20)));
            else if(instructor.GetIsFainted())
                panelToDrawOn.AddRect(new RectPanel(new Color(16, 167, 137),
                        drawPos, new Dimension(20, 20)));
        }

        for(IVJanitor janitor : ivRoom.GetIVJanitors()) {
            Dimension drawPos = GetRandomRoomPos(panelToDrawOn,10);
            panelToDrawOn.AddRect(new RectPanel(new Color(45, 156, 240),
                    drawPos, new Dimension(50, 50)));
        }

        for(IVItem item : ivRoom.GetIVItems()) {
            Dimension drawPos = GetRandomRoomPos(panelToDrawOn,50);
            Color color = item.GetColor();
            panelToDrawOn.AddCircle(new MapCirclePanel(color,
                    drawPos, 50, panelToDrawOn));
        }

        int doorsCount = ivRoom.GetIVDoors().size();
        System.out.println("DOORS COUNT: " + doorsCount + " -------------------------------------------------------------------------");
        ArrayList<Boolean> isHorizontalDoors = GetDoorPositions(panelToDrawOn, doorsCount);
        int counter = 0;
        for(IVDoorSide doorSide : ivRoom.GetIVDoors()) {
            Dimension drawPos = doorPositions.get(counter);
            Dimension doorDim;
            if(isHorizontalDoors.get(counter)) {
                doorDim = new Dimension(80, 20);
            }
            else {
                doorDim = new Dimension(20, 80);
            }
            if(!doorSide.GetCanBeOpened())
                if(!doorSide.GetIsVisible())
                    panelToDrawOn.AddRect(new RectPanel(new Color(218, 0, 99), drawPos, doorDim, panelToDrawOn));
                else
                    panelToDrawOn.AddRect(new RectPanel(new Color(242, 70, 38), drawPos, doorDim, panelToDrawOn));
            else
                if(!doorSide.GetIsVisible())
                    panelToDrawOn.AddRect(new RectPanel(new Color(255, 255, 255), drawPos, doorDim, panelToDrawOn));
                else
                    panelToDrawOn.AddRect(new RectPanel(new Color(26, 26, 26), drawPos, doorDim, panelToDrawOn));
            counter++;
        }
    }

    private Dimension GetRandomRoomPos(JPanel p, int border) {
        return new Dimension(ThreadLocalRandom.current().nextInt(border, p.getPreferredSize().width - border),
                ThreadLocalRandom.current().nextInt(border, p.getPreferredSize().height - border));
    }

    private ArrayList<Boolean> GetDoorPositions(JPanel p, int count) {
        doorPositions = new ArrayList<>();
        int height = p.getPreferredSize().height;
        int width = p.getPreferredSize().width;
        System.out.println("SIZE: WIDTH: " + width + " HEIGHT: " + height + " -------------------------------------------------------------------------");
        ArrayList<Boolean> isHorizontalDoors = new ArrayList<>();
        int perimeter = 2 * width + 2 * height;
        int startPoint = (int) (0.5 * height);
        int currentPoint = startPoint;
        for(int i = 0; i < count; i++) {
            if(currentPoint < height) {
                doorPositions.add(new Dimension(0, currentPoint));
                isHorizontalDoors.add(false);
            }
            else if(currentPoint < height + width) {
                doorPositions.add(new Dimension(currentPoint - height, height));
                isHorizontalDoors.add(true);
            }
            else if(currentPoint < 2 * height + width) {
                doorPositions.add(new Dimension(width, height - (currentPoint - width - height)));
                isHorizontalDoors.add(false);
            }
            else {
                doorPositions.add(new Dimension(width - (currentPoint - 2 * height - width), 0));
                isHorizontalDoors.add(true);
            }
            currentPoint += (perimeter - startPoint) / count;
        }

        return isHorizontalDoors;
    }

    public ArrayList<VItem> GetItems(){
        return items;
    }
    public void AddVItem(VItem item){
        items.add(item);
    }

    public IVRoom GetIVRoom(){
        return ivRoom;
    }

    @Override
    public VRoom GetVRoom() {
        return this;
    }
}
