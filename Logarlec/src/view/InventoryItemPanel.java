package view;

import util.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InventoryItemPanel extends JPanel {
    VItem item;
    JLabel nameLabel;
    JButton throwButton;
    JButton useButton;
    JButton connectButton;
    CirclePanel circlePanel;
    public InventoryItemPanel(Color circleColor, boolean use, boolean connect, VItem i, String name, boolean buttons){
        item = i;
        setPreferredSize(new Dimension(200, 250)); // Set preferred size
        setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for visualization

        BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
        setLayout(boxlayout);

        nameLabel = new JLabel(name);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        circlePanel = new CirclePanel(circleColor);

        throwButton = new JButton("Throw");
        useButton = new JButton("Use");
        connectButton = new JButton("Connect");

        throwButton.setPreferredSize(new Dimension(200, 25));
        useButton.setPreferredSize(new Dimension(200, 25));
        connectButton.setPreferredSize(new Dimension(200, 25));

        throwButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        useButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        throwButton.setMargin(new Insets(0,60,0,60));
        useButton.setMargin(new Insets(0,67,0,67));
        connectButton.setMargin(new Insets(0,54,0,54));

        add(Box.createVerticalStrut(2));
        add(nameLabel);
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
        add(Box.createVerticalStrut(7));

        if(buttons){
            DisableButtons();
        }
    }

    public void DisableButtons(){
        throwButton.setEnabled(false);
        useButton.setEnabled(false);
        connectButton.setEnabled(false);
    }

    private class ThrowButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            Logger.startedView(this, "ThrowButtonListener.actionPerformed", e);
            item.Thrown();
            item.owner.GetControlPanel().UpdateAll();
            Logger.finishedView(this, "ThrowButtonListener.actionPerformed", e);
        }
    }

    private class UseButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            Logger.startedView(this, "UseButtonListener.actionPerformed", e);
            item.Used();
            item.owner.GetControlPanel().UpdateAll();
            Logger.finishedView(this, "UseButtonListener.actionPerformed", e);
        }
    }

    private class ConnectButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            Logger.startedView(this, "ConnectButtonListener.actionPerformed", e);
            if(item.GetClickedT() == null){
                item.SetClickedT((VTransistor)item);
                item.owner.GetControlPanel().LogEvent("A " + item.GetID() + " párosításra kész.\n");
                item.owner.GetControlPanel().UpdateAll();
                connectButton.setEnabled(false);
            }else{
                item.Connected();
                item.owner.GetControlPanel().LogEvent((item.GetClickedT().GetID() + " párosítva a " + item.GetID() + " tranzisztorral.\n"));
                item.owner.GetControlPanel().UpdateAll();
            }
            Logger.finishedView(this, "ConnectButtonListener.actionPerformed", e);
        }
    }
}
