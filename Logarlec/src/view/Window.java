package view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import util.Logger;
import util.Logger.LogLevel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Az alkalmazás fő ablaka
 */
public class Window extends JFrame {
    private HelperFrame helperFrame = new HelperFrame(this);
    private MainFrame mainFrame = new MainFrame();
    private GameStartFrame gameStartFrame;
    private JButton gameButton = new JButton("Játék");
    private JButton helpButton = new JButton("Súgó");
    private JButton exitButton = new JButton("Kilépés");
    private JPanel panel;
    public Window(LogLevel logLevel) {
        this.gameStartFrame = new GameStartFrame(this, mainFrame.GetGamePanel(),mainFrame.GetControlPanel(),mainFrame,logLevel);
        setTitle("Logarléc");
        setPreferredSize(new Dimension(600, 300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);


        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        JLabel welcomeText = new JLabel("Üdvözlünk a Logarléc című játékban!");
        JLabel emptySpace1 = new JLabel(" ");
        JLabel descriptionText = new JLabel("Kattints a Játék gombra új játék indításához vagy a Súgó gombra a súgó megnyitásához!");
        JLabel emptySpace2 = new JLabel(" ");

        welcomeText.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptySpace1.setAlignmentX(Component.CENTER_ALIGNMENT);
        descriptionText.setAlignmentX(Component.CENTER_ALIGNMENT);
        emptySpace2.setAlignmentX(Component.CENTER_ALIGNMENT);

        gameButton.addActionListener(new PlayButtonListener());
        helpButton.addActionListener(new HelpButtonListener());
        exitButton.addActionListener(new ExitButtonListener());

        gameButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        gameButton.setPreferredSize(new Dimension(100, 100));
        gameButton.setMargin(new Insets(10,55,10,55));

        helpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        helpButton.setPreferredSize(new Dimension(100, 100));
        helpButton.setMargin(new Insets(10,55,10,55));

        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setPreferredSize(new Dimension(100, 100));
        exitButton.setMargin(new Insets(10,49,10,48));

        panel.add(Box.createRigidArea(new Dimension(10,10)));

        panel.add(welcomeText);
        panel.add(emptySpace1);
        panel.add(descriptionText);
        panel.add(emptySpace2);
        panel.add(gameButton);
        panel.add(helpButton);
        panel.add(exitButton);

        panel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        add(panel);

        pack();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Megjeleníti a súgót
     */
    public void ShowHelperFrame(){
        Logger.startedView(this, "ShowHelperFrame");
        helperFrame.setVisible(true);
        this.setVisible(false);
        Logger.finishedView(this, "ShowHelperFrame");
    }

    public void Exit(){
        Logger.startedView(this, "Exit");
        Logger.finishedView(this, "Exit");
        System.exit(1);
    }
    public void ShowGameStartFrame(){
        Logger.startedView(this, "ShowGameStartFrame");
        this.setVisible(false);
        gameStartFrame.setVisible(true);
        Logger.finishedView(this, "ShowGameStartFrame");
    }

    public void ShowMainWindow(){
        Logger.startedView(this, "ShowMainWindow");
        this.setVisible(true);
        Logger.finishedView(this, "ShowMainWindow");
    }

    public void ShowMainFrame(){
        Logger.startedView(this, "ShowMainFrame");
        mainFrame.setVisible(true);
        Logger.finishedView(this, "ShowMainFrame");
    }

    //Action Listener-ek:
    private class HelpButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Logger.startedView(this, "HelpButtonListener.actionPerformed", e);
            ShowHelperFrame();
            Logger.finishedView(this, "HelpButtonListener.actionPerformed", e);
        }
    }
    private class ExitButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Logger.startedView(this, "ExitButtonListener.actionPerformed", e);
            Exit();
            Logger.finishedView(this, "ExitButtonListener.actionPerformed", e);
        }
    }
    private class PlayButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Logger.startedView(this, "PlayButtonListener.actionPerformed", e);
            ShowGameStartFrame();
            Logger.finishedView(this, "PlayButtonListener.actionPerformed", e);
        }
    }

}
