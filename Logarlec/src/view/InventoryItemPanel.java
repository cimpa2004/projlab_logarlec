package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryItemPanel extends JPanel {
    VItem item;

    JButton connectButton;
    public InventoryItemPanel(Color circleColor, boolean use, boolean connect, VItem i){
        item = i;
        setPreferredSize(new Dimension(100, 50)); // Set preferred size
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for visualization

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);

        CirclePanel circlePanel = new CirclePanel(circleColor);

        JButton throwButton = new JButton();
        JButton useButton = new JButton();
        connectButton = new JButton();

        add(circlePanel);
        throwButton.addActionListener(new ThrowButtonListener());
        add(throwButton);
        if(use){
            useButton.addActionListener(new UseButtonListener());
            add(useButton);
        }
        if(connect){
            connectButton.addActionListener(new ConnectButtonListener());
            add(connectButton);
        }

    }

    private class ThrowButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            item.Thrown();
        }
    }

    private class UseButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            item.Used();
        }
    }

    private class ConnectButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            if(item.GetClickedT() == null){
                item.SetClickedT((VTransistor)item);
                connectButton.setEnabled(false);
            }else{
                item.Connected();
            }

            item.Connected();
        }
    }
}
