package view;

import viewmodel.IVCamembert;

import javax.swing.*;
import java.awt.*;

public class VCamembert extends VItem{
    IVCamembert ivCamembert;

    public VCamembert(IVCamembert ivCamembert){
        this.ivCamembert = ivCamembert;
        this.ivCamembert.SetIVItemUpdate(this);
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
        return new CirclePanel(new Color(255,255,0));
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

        CirclePanel circlePanel = new CirclePanel(new Color(255,255,0));
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
}
