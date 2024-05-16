package view;

import viewmodel.IControl;

import javax.swing.*;
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
        //TODO: ha minden kész ezt komentteleníteni az alatta lévőt meg törölni
        if(currentStudent != null && currentStudent.getItems() != null){
            for (VItem item: currentStudent.getItems()){
                item.DrawInInventory(itemsPanel, currentStudent);
            }
        }

        // Panel for buttons on the right
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS)); // Vertical alignment
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Create buttons
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
        for (VStudent student : students){
            if (student.getIsActiveTurn()){
                currentStudent = student;
                break;
            }
        }
    }

    /**
     * beállítja a kiválasztott tárgyat
     * @param item a kiválasztott tárgy
     */
    public void SetSelectedItem(VItem item){
        this.selectedItem = item;
    }

    /**
     * Újra rajzolja az inventory-t és a gamePanelt
     */
    private void Update() {
        //TODO: uncomment this
        if (currentStudent != null){
            if(currentStudent.getItems() != null){
                for(VItem item: currentStudent.getItems()){
                    item.DrawInInventory(itemsPanel, currentStudent);
                }
            }
            nameLabel.setText(currentStudent.toString());
            gamePanel.Draw(currentStudent.GetRoom().GetVRoom());
        }
        else nameLabel.setText("Senki köre");
    }

    /**
     *Frissíti az éppen körön lévő játékost és kirajzolja az új inventory-t
     */
    @Override
    public void StudentStartedTurn() {
        UpdateCurrentStudent();
        Update();
    }

    @Override
    public void InstructorWin() {
        infoLabel.setText("A játék véget ért, az oktatók nyertek!");
        EndTurnButton.setEnabled(false);
        PickUpButton.setEnabled(false);
    }

    @Override
    public void StudentWin() {
        infoLabel.setText("A játék véget ért, a hallgatók nyertek!");
        EndTurnButton.setEnabled(false);
        PickUpButton.setEnabled(false);
    }

    private class EndTurnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (currentStudent != null){
                currentStudent.EndTurn();
                UpdateCurrentStudent();
            }
        }
    }
    private class PickupButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(selectedItem != null){
                currentStudent.Pickup(selectedItem);
                selectedItem = null;
                gamePanel.Redraw();//eltűnjön a felvett tárgy
            }
        }
    }

}
