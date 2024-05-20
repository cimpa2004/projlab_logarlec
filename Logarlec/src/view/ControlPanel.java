package view;

import util.Logger;
import viewmodel.IControl;

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
    private JEditorPane infoPane = new JEditorPane();
    private JLabel nameLabel = new JLabel();
    private JLabel roomLabel = new JLabel();
    private VStudent currentStudent;
    private JPanel itemsPanel = new JPanel();
    private GamePanel gamePanel;
    private JPanel namepanel = new JPanel();
    private JLabel remainingTurnsLabel = new JLabel();

    private String text = "";

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

        if(currentStudent != null && currentStudent.GetItems() != null){
            for (VItem item: currentStudent.GetItems()){
                item.DrawInInventory(itemsPanel, currentStudent, false);
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
        buttonsPanel.add(Box.createVerticalStrut(10));

        // Create info pane
        infoPane.setPreferredSize(new Dimension(425,150));
        infoPane.setContentType("text/plain");
        infoPane.setText(text);
        infoPane.setEditable(false);
        JScrollPane jScrollPane = new JScrollPane(infoPane);
        buttonsPanel.add(jScrollPane);

        // Display student name and room name
        namepanel.setLayout(new BoxLayout(namepanel, BoxLayout.Y_AXIS));
        namepanel.add(nameLabel);
        namepanel.add(roomLabel);
        namepanel.add(remainingTurnsLabel);



        // Add panels to the main panel
        add(namepanel, BorderLayout.NORTH);
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
    public ArrayList<VStudent> GetStudents(){
        return students;
    }

    /**
     * Újra rajzolja az inventory-t
     */
    public void Update(){
        Logger.startedView(this, "Update");

        itemsPanel.removeAll();
        itemsPanel.revalidate();

        if (currentStudent != null){
            if(currentStudent.GetItems() != null){
                for(VItem item: currentStudent.GetItems()){
                    item.DrawInInventory(itemsPanel, currentStudent, false);
                }
            }

            nameLabel.setText("A körön lévő játékos:          " + currentStudent.toString());
            nameLabel.setBorder(new EmptyBorder(10,10,10,10));
            roomLabel.setText("A jelenlegi szoba:             " + currentStudent.GetRoom().GetID());
            roomLabel.setBorder(new EmptyBorder(10,10,10,10));
            remainingTurnsLabel.setText("A hátralévő körök száma: " + currentStudent.input.GetGameTimer());
            remainingTurnsLabel.setBorder(new EmptyBorder(10,10,10,10));
            infoPane.setText(text);
        }
        else nameLabel.setText("Senki köre");
        Logger.finishedView(this, "Update");
    }


    /**
     * Újra rajzolja az inventory-t és a gamePanelt
     */
    public void UpdateAll() {
        Logger.startedView(this, "UpdateAll");

        Update();

        if(currentStudent != null){
            gamePanel.ClearAll();
            gamePanel.Draw(currentStudent.GetRoom().GetVRoom());
        }

        Logger.finishedView(this, "UpdateAll");
    }
    public void SetCurrentStudent(VStudent st){
        currentStudent = st;
    }

    /**
     *Frissíti az éppen körön lévő játékost és kirajzolja az új inventory-t
     */
    @Override
    public void StudentStartedTurn() {
        Logger.startedView(this, "StudentStartedTurn");
        UpdateCurrentStudent();
        UpdateAll();
        Logger.finishedView(this, "StudentStartedTurn");
    }

    @Override
    public void InstructorWin() {
        Logger.startedView(this, "InstructorWin");
        infoLabel.setText("A játék véget ért, az oktatók nyertek!");
        remainingTurnsLabel.setText("A hátralévő körök száma: 0");
        EndTurnButton.setEnabled(false);
        PickUpButton.setEnabled(false);

        itemsPanel.removeAll();
        itemsPanel.revalidate();
        if(currentStudent != null && currentStudent.GetItems() != null){
            for (VItem item: currentStudent.GetItems()){
                item.DrawInInventory(itemsPanel, currentStudent, true);
            }
        }
        Logger.finishedView(this, "InstructorWin");
    }

    @Override
    public void StudentWin() {
        Logger.startedView(this, "StudentWin");
        infoLabel.setText("A játék véget ért, a hallgatók nyertek!");
        remainingTurnsLabel.setText("A hátralévő körök száma: 0");
        EndTurnButton.setEnabled(false);
        PickUpButton.setEnabled(false);

        itemsPanel.removeAll();
        itemsPanel.revalidate();
        if(currentStudent != null && currentStudent.GetItems() != null){
            for (VItem item: currentStudent.GetItems()){
                item.DrawInInventory(itemsPanel, currentStudent, true);
            }
        }
        Logger.finishedView(this, "StudentWin");
    }

    public void LogEvent(String event){
        text += event;
    }

    private class EndTurnButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Logger.startedView(this, "EndTurnButtonListener.actionPerformed", e);
            if (currentStudent != null){
                LogEvent(currentStudent.GetID() + " köre véget ért!\n");
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
            if(gamePanel.GetSelectedItem() != null){
                gamePanel.GetSelectedItem().GetIVItemUpdate().SetControlPanel(ControlPanel.this);
                currentStudent.input.PickupItem(currentStudent.GetID(), gamePanel.GetSelectedItem());
                gamePanel.SetSelectedItem(null);
                UpdateAll();
            }
            Logger.finishedView(this, "PickupButtonListener.actionPerformed", e);
        }
    }

}
