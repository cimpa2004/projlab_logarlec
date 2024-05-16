package view;

import viewmodel.IVTVSZ;

import javax.swing.*;
import java.awt.*;

public class VTVSZ extends VItem {
    IVTVSZ ivTVSZ;

    public VTVSZ(IVTVSZ ivTVSZ) {
        this.ivTVSZ = ivTVSZ;
        this.ivTVSZ.SetIVItemUpdate(this);
    }

    @Override
    public void PickedUp() {

    }

    @Override
    public CirclePanel DrawOnMap() {
        return new CirclePanel(new Color(255,0,0));
    }

    @Override
    public void DrawInInventory(JPanel panel) {
        JPanel itemPanel = new JPanel();
        itemPanel.setPreferredSize(new Dimension(100, 50)); // Set preferred size
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for visualization

        BoxLayout boxlayout = new BoxLayout(itemPanel, BoxLayout.Y_AXIS);
        itemPanel.setLayout(boxlayout);

        CirclePanel circlePanel = new CirclePanel(new Color(255,0,0));
        JButton throwButton = new JButton();

        itemPanel.add(circlePanel);
        itemPanel.add(throwButton);

        panel.add(itemPanel);
    }

    @Override
    public boolean HasNullable() {
        return false;
    }

    @Override
    public void Throw() {

    }
}
