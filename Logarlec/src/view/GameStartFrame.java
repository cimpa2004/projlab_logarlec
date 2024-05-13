package view;

import controller.Game;
import util.Logger;
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

public class GameStartFrame extends JFrame {
    private IVInit initer = new Game(false, Logger.LogLevel.INPUT_HANDLER);
    private Window window;
    private JButton startGameButton = new JButton("Játék indítása");
    private JButton addStudentButton = new JButton("Játékos felvétele");
    private JTextField nameTextBox = new JTextField();
    private ArrayList<String> names = new ArrayList<>();
    private ICInit icInit;
    private IControl iControl;
    private ICRoom icRoom;

    public GameStartFrame(Window w, ICRoom icRoom, IControl iControl, ICInit icInit) {
        if (w == null) {
            throw new NullPointerException("Received null Window in constructor");
        }
        this.icInit = icInit;
        this.iControl = iControl;
        this.icRoom = icRoom;
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

    @Override
    public void dispose() {
        this.setVisible(false);
        window.ShowMainWindow();
    }

    public void SetClickableIfNeededStartButton() {
        boolean containsNonEmpty = false;
        for (String name : names) {
            if (name != null && !name.trim().isEmpty()) {
                containsNonEmpty = true;
                break;
            }
        }
        startGameButton.setEnabled(containsNonEmpty);
    }
    public void SetClickableIfNeededAddButton() {
        boolean containsNonEmpty = false;
        if (!nameTextBox.getText().trim().isEmpty())
            containsNonEmpty = true;
        addStudentButton.setEnabled(containsNonEmpty);
    }

    private class StartButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //if button is clickable there are valid values
            //TODO: use real map
            initer.CreateGame("Tests/Test16/Map.json",icInit,iControl,icRoom);
            for (String name: names) {
                initer.AddStudent(name);
            }
            initer.StartGame();
            setVisible(false);
            window.ShowMainFrame();

        }
    }
    private class AddPlayerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            //When it can be clicked the value is valid
            String temp = nameTextBox.getText().trim();
            names.add(temp);
            SetClickableIfNeededStartButton();
            nameTextBox.setText("");
            addStudentButton.setEnabled(false);
        }
    }

    private class NameTextBoxKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {}

        @Override
        public void keyReleased(KeyEvent e) {
            SetClickableIfNeededAddButton();
        }
    }


}
