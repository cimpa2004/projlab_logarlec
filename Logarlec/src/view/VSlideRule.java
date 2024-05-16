package view;

import viewmodel.IVSlideRule;

import javax.swing.*;
import java.awt.*;

public class VSlideRule extends VItem {
    //ezt a konstruktort használd Roomban megírtam onnan lehet példát venni
    public VSlideRule(IVSlideRule ivSlideRule){

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
        return new CirclePanel(new Color(0,255,0));
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

        CirclePanel circlePanel = new CirclePanel(new Color(0,255,0));
        JButton throwButton = new JButton();

        itemPanel.add(circlePanel);
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
