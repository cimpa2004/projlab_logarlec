package view;

import controller.Game;
import util.Logger;
import util.Logger.LogLevel;
import viewmodel.ICInit;
import viewmodel.ICRoom;
import viewmodel.IControl;
import viewmodel.IVInit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * A játékos felvevő és játék elindító ablak, fool proof!!!
 */
public class GameStartFrame extends JFrame {
    private IVInit initer;
    private Window window;
    private JButton startGameButton = new JButton("Játék indítása");
    private JButton addStudentButton = new JButton("Játékos felvétele");
    private JTextField nameTextBox = new JTextField();
    private ArrayList<String> names = new ArrayList<>();
    private ICInit icInit;
    private IControl iControl;
    private ICRoom icRoom;

    public GameStartFrame(Window w, ICRoom icRoom, IControl iControl, ICInit icInit, LogLevel logLevel) {
        if (w == null) {
            throw new NullPointerException("Received null Window in constructor");
        }
        this.icInit = icInit;
        this.iControl = iControl;
        this.icRoom = icRoom;
        this.initer = new Game(false, logLevel);
        window = w;
        setTitle("JátékosFelvevő");
        setSize(500, 200);
        setLayout(new BorderLayout()); // Use BorderLayout for better organization
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        startGameButton.setEnabled(false);
        addStudentButton.setEnabled(false);
        nameTextBox.addKeyListener(new NameTextBoxKeyListener());
        startGameButton.addActionListener(new StartButtonListener());
        addStudentButton.addActionListener(new AddPlayerButtonListener());

        // Create panel for buttons and text field
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 0, 10)); // Use grid layout with spacing
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
        buttonPanel.add(new JLabel("Név:"));
        buttonPanel.add(nameTextBox);

        JPanel buttonContainerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonContainerPanel.add(addStudentButton);
        buttonContainerPanel.add(startGameButton);

        buttonPanel.add(buttonContainerPanel);

        nameTextBox.setHorizontalAlignment(JTextField.CENTER);
        add(buttonPanel, BorderLayout.CENTER);
        setLocationRelativeTo(null);

        setVisible(false);
    }

    /**
     * JFrame művelet felülírása
     */
    @Override
    public void dispose() {
        Logger.startedView(this, "dispose");
        this.setVisible(false);
        window.ShowMainWindow();
        Logger.finishedView(this, "dispose");
    }

    /**
     * Ha nincs felvéve játékos nem lehet játszani
     */
    public void SetClickableIfNeededStartButton() {
        Logger.startedView(this, "SetClickableIfNeededStartButton");
        boolean containsNonEmpty = false;
        for (String name : names) {
            if (name != null && !name.trim().isEmpty()) {
                containsNonEmpty = true;
                break;
            }
        }
        startGameButton.setEnabled(containsNonEmpty);
        Logger.finishedView(this, "dispose");
    }

    /**
     * Ha nincs megadva rendes név nem lehet felvenni a játékost
     */
    public void SetClickableIfNeededAddButton() {
        Logger.startedView(this, "SetClickableIfNeededAddButton");
        boolean containsNonEmpty = false;
        if (!nameTextBox.getText().trim().isEmpty())
            containsNonEmpty = true;
        addStudentButton.setEnabled(containsNonEmpty);
        Logger.finishedView(this, "SetClickableIfNeededAddButton");
    }

    /**
     * Action listener
     */
    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Logger.startedView(this, "StartButtonListener.actionPerformed", e);
            //if button is clickable there are valid values
            //TODO: use real map
            initer.CreateGame("Map.json",icInit,iControl,icRoom);
            for (String name: names) {
                initer.AddStudent(name);
            }
            initer.StartGame();
            setVisible(false);
            window.ShowMainFrame();
            Logger.finishedView(this, "StartButtonListener.actionPerformed", e);
        }
    }
    /**
     * Action listener
     */
    private class AddPlayerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Logger.startedView(this, "AddPlayerButtonListener.actionPerformed", e);
            //When it can be clicked the value is valid
            String temp = nameTextBox.getText().trim();
            names.add(temp);
            SetClickableIfNeededStartButton();
            nameTextBox.setText("");
            addStudentButton.setEnabled(false);
            Logger.finishedView(this, "AddPlayerButtonListener.actionPerformed", e);
        }
    }

    /**
     * Key lisener, kell az add gomb lezárásához és feloldásához
     */
    private class NameTextBoxKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {
            Logger.startedView(this, "NameTextBoxKeyListener.keyTyped", e);
            Logger.finishedView(this, "NameTextBoxKeyListener.keyTyped", e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            Logger.startedView(this, "NameTextBoxKeyListener.keyPressed", e);
            Logger.finishedView(this, "NameTextBoxKeyListener.keyPressed", e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            Logger.startedView(this, "NameTextBoxKeyListener.keyPressed", e);
            SetClickableIfNeededAddButton();
            Logger.finishedView(this, "NameTextBoxKeyListener.keyPressed", e);
        }
    }


}
