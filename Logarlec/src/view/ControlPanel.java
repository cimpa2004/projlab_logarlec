package view;

import util.Logger;
import viewmodel.IControl;

import javax.naming.ldap.Control;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Az irányításhoz szükséges gombokat és az inventory-t jeleníti meg
 */
public class ControlPanel extends JPanel implements IControl {
    private ArrayList<VStudent> students = new ArrayList<>();
    private JButton EndTurnButton = new JButton("Kör vége");
    private JButton PickUpButton = new JButton("Kijelölt tárgy felvétele");
    private JLabel infoLabel = new JLabel();
    private JLabel nameLabel = new JLabel();
    private VItem selectedItem;
    private VStudent currentStudent;
    private JPanel itemsPanel = new JPanel();
    private GamePanel gamePanel;


    public void AddVStudent(VStudent new_){
        students.add(new_);
    }
    public ControlPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        setLayout(new BorderLayout());

        // Panel for items on the left
        itemsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); // Left-align items with gap
        itemsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        UpdateCurrentStudent();

        //Rendes függvény
        //TODO: maybe it will work
        if(currentStudent != null && currentStudent.GetItems() != null){
            for (VItem item: currentStudent.GetItems()){
                item.DrawInInventory(itemsPanel, currentStudent);
            }
        }

        // Panel for buttons on the right
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS)); // Vertical alignment
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Create button listeners
        EndTurnButton.addActionListener(new EndTurnButtonListener());
        PickUpButton.addActionListener(new PickupButtonListener());


        // Add buttons to the panel
        buttonsPanel.add(EndTurnButton);
        buttonsPanel.add(Box.createVerticalStrut(10)); // Add vertical gap
        buttonsPanel.add(PickUpButton);
        buttonsPanel.add(Box.createVerticalStrut(10));
        buttonsPanel.add(infoLabel);

        // Add panels to the main panel
        add(nameLabel, BorderLayout.NORTH);
        add(itemsPanel, BorderLayout.WEST);
        add(buttonsPanel, BorderLayout.EAST);
    }

    /**
     * frissiti az aktiv körön lévő hallgatót (privát)
     */
    private void UpdateCurrentStudent(){
        Logger.startedView(this, "UpdateCurrentStudent");
        for (VStudent student : students){
            if (student.GetIsActiveTurn()){
                currentStudent = student;
                break;
            }
        }
        Logger.finishedView(this, "UpdateCurrentStudent");
    }

    public VStudent GetCurrentStudent() {
        return currentStudent;
    }

    /**
     * beállítja a kiválasztott tárgyat
     * @param item a kiválasztott tárgy
     */
    public void SetSelectedItem(VItem item){
        Logger.startedView(this, "SetSelectedItem", item);
        this.selectedItem = item;
        Logger.finishedView(this, "SetSelectedItem", item);
    }

    /**
     * Újra rajzolja az inventory-t és a gamePanelt
     */
    public void Update() {
        Logger.startedView(this, "Update");
        //TODO: maybe it will work
        if (currentStudent != null){
            if(currentStudent.GetItems() != null){
                for(VItem item: currentStudent.GetItems()){
                    item.DrawInInventory(itemsPanel, currentStudent);
                }
            }
            nameLabel.setText("A körön lévő játékos:          " + currentStudent.toString());
            nameLabel.setBorder(new EmptyBorder(10,10,10,10));
            gamePanel.ClearAll();
            gamePanel.Draw(currentStudent.GetRoom().GetVRoom());
        }
        else nameLabel.setText("Senki köre");
        Logger.finishedView(this, "Update");
    }

    /**
     *Frissíti az éppen körön lévő játékost és kirajzolja az új inventory-t
     */
    @Override
    public void StudentStartedTurn() {
        Logger.startedView(this, "StudentStartedTurn");
        UpdateCurrentStudent();
        Update();
        Logger.finishedView(this, "StudentStartedTurn");
    }

    @Override
    public void InstructorWin() {
        Logger.startedView(this, "InstructorWin");
        infoLabel.setText("A játék véget ért, az oktatók nyertek!");
        EndTurnButton.setEnabled(false);
        PickUpButton.setEnabled(false);
        Logger.finishedView(this, "InstructorWin");
    }

    @Override
    public void StudentWin() {
        Logger.startedView(this, "StudentWin");
        infoLabel.setText("A játék véget ért, a hallgatók nyertek!");
        EndTurnButton.setEnabled(false);
        PickUpButton.setEnabled(false);
        Logger.finishedView(this, "StudentWin");
    }

    private class EndTurnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Logger.startedView(this, "EndTurnButtonListener.actionPerformed", e);
            if (currentStudent != null){
                currentStudent.EndTurn();
                gamePanel.Redraw();
                UpdateCurrentStudent();
            }
            Logger.finishedView(this, "EndTurnButtonListener.actionPerformed", e);
        }
    }
    private class PickupButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Logger.startedView(this, "PickupButtonListener.actionPerformed", e);
            if(selectedItem != null){
                currentStudent.Pickup(selectedItem);
                selectedItem = null;
                gamePanel.Redraw();//eltűnjön a felvett tárgy
            }
            Logger.finishedView(this, "PickupButtonListener.actionPerformed", e);
        }
    }

}
