package view;
import javax.swing.*;

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
    public Window(LogLevel logLevel) {
        this.gameStartFrame = new GameStartFrame(this, mainFrame.GetGamePanel(),mainFrame.GetControlPanel(),mainFrame,logLevel);
        setTitle("Logarléc");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1, 2,10));
        setLocationRelativeTo(null);
        add(gameButton);
        add(helpButton);
        add(exitButton);

        helpButton.addActionListener(new HelpButtonListener());
        exitButton.addActionListener(new ExitButtonListener());
        gameButton.addActionListener(new PlayButtonListener());



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
