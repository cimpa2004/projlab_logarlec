package view;

import model.Transistor;
import viewmodel.IVTransistor;

import javax.swing.*;
import java.awt.*;

public class VTransistor extends VItem{

    private Transistor ClickedT;
    IVTransistor ivTransistor;

    public VTransistor(IVTransistor ivTransistor){
        this.ivTransistor = ivTransistor;
        this.ivTransistor.SetIVItemUpdate(this);
    }

    @Override
    public void PickedUp() {

    }

    @Override
    public CirclePanel DrawOnMap() {
        return new CirclePanel(new Color(0,0,0));
    }

    @Override
    public void DrawInInventory(JPanel panel) {
        JPanel itemPanel = new JPanel();
        itemPanel.setPreferredSize(new Dimension(100, 50)); // Set preferred size
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for visualization

        BoxLayout boxlayout = new BoxLayout(itemPanel, BoxLayout.Y_AXIS);
        itemPanel.setLayout(boxlayout);

        CirclePanel invCirclePanel = new CirclePanel(new Color(0,0,0));
        JButton useButton = new JButton();
        JButton throwButton = new JButton();
        JButton connectButton = new JButton();

        itemPanel.add(invCirclePanel);
        itemPanel.add(useButton);
        itemPanel.add(throwButton);
        itemPanel.add(connectButton);

        panel.add(itemPanel);
    }

    @Override
    public boolean HasNullable() {
        return true;
    }

    @Override
    public void Throw() {

    }

    @Override
    public void UsedUpdate() {

    }
}
