package view;

import viewmodel.IControl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControlPanel extends JPanel implements IControl {
    private ArrayList<VStudent> students = new ArrayList<>();
    private VStudent currentStudent;
    public ControlPanel() {
        setLayout(new BorderLayout());

        // Panel for items on the left
        JPanel itemsPanel = new JPanel();
        itemsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Left-align items with gap
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        UpdateCurrentStudent();



        //Rendes függvény
        //TODO: ha minden kész ezt komentteleníteni az alatta lévőt meg törölni
        //for (VItem item: currentStudent.getItems()){
        //    item.DrawInInventory(itemsPanel);
        //}

        // Add 5 items (placeholders)
        for (int i = 0; i < 5; i++) {
            JPanel box = new JPanel();
            box.setPreferredSize(new Dimension(100, 50)); // Set preferred size
            box.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add border for visualization
            itemsPanel.add(box);
        }

        // Panel for buttons on the right
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS)); // Vertical alignment
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Create buttons
        JButton EndTurnButton = new JButton("Kör vége");
        EndTurnButton.addActionListener(new EndTurnButtonListener());
        JButton PickUpButton = new JButton("Kijelölt tárgy felvétele");

        // Add buttons to the panel
        buttonsPanel.add(EndTurnButton);
        buttonsPanel.add(Box.createVerticalStrut(10)); // Add vertical gap
        buttonsPanel.add(PickUpButton);

        // Add panels to the main panel
        add(itemsPanel, BorderLayout.WEST); // Place items on the left
        add(buttonsPanel, BorderLayout.EAST); // Place buttons on the right
    }
    private void UpdateCurrentStudent(){
        for (VStudent student : students){
            if (student.getIsActiveTurn()){
                currentStudent = student;
                break;
            }
        }
    }

    private class EndTurnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            currentStudent.EndTurn();
            UpdateCurrentStudent();
        }
    }
    //TODO: PickupButtonListener

}
