package view;

import viewmodel.IVFFP2Mask;

import javax.swing.*;
import java.awt.*;

public class VFFP2Mask extends VItem{
    IVFFP2Mask ivFFP2Mask;
    
    public VFFP2Mask(IVFFP2Mask ivffp2Mask){
        this.ivFFP2Mask = ivffp2Mask;
        this.ivFFP2Mask.SetIVItemUpdate(this);
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
        return new CirclePanel(new Color(255,255,255));
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

        CirclePanel invCirclePanel = new CirclePanel(new Color(255,255,255));
        JButton useButton = new JButton();
        JButton throwButton = new JButton();

        itemPanel.add(invCirclePanel);
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
