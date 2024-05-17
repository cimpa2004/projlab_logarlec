package view;

import viewmodel.IVWetTableCloth;

import javax.swing.*;
import java.awt.*;

public class VWetTableCloth extends VItem {
    IVWetTableCloth ivWetTableCloth;

    public VWetTableCloth(IVWetTableCloth ivWetTableCloth) {
        this.ivWetTableCloth = ivWetTableCloth;
        ivWetTableCloth.SetIVItemUpdate(this);
    }

    /**
     *
     */
    @Override
    public void PickedUp() {

    }

    /**
     *
     */
    @Override
    public CirclePanel DrawOnMap() {
        return new CirclePanel(new Color(153,0,153));
    }

    /**
     * @param panel
     */
    @Override
    public void DrawInInventory(JPanel panel) {
        JPanel itemPanel = new JPanel();
        itemPanel.setPreferredSize(new Dimension(100, 50)); // Set preferred size
        itemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for visualization

        BoxLayout boxlayout = new BoxLayout(itemPanel, BoxLayout.Y_AXIS);
        itemPanel.setLayout(boxlayout);

        CirclePanel circlePanel = new CirclePanel(new Color(153,0,153));
        JButton useButton = new JButton();
        JButton throwButton = new JButton();

        itemPanel.add(circlePanel);
        itemPanel.add(useButton);
        itemPanel.add(throwButton);

        panel.add(itemPanel);
    }

    /**
     * @return
     */
    @Override
    public boolean HasNullable() {
        return false;
    }

    /**
     *
     */
    @Override
    public void Throw() {

    }

    @Override
    public void UsedUpdate() {

    }
}
