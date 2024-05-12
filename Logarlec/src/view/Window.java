package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Az alkalmazás fő ablaka
 */
public class Window extends JFrame {
    private HelperFrame helperFrame = new HelperFrame(this);
    private GameStartFrame gameStartFrame = new GameStartFrame(this);
    private MainFrame mainFrame = new MainFrame();
    private JButton gameButton = new JButton("Játék");
    private JButton helpButton = new JButton("Súgó");
    private JButton exitButton = new JButton("Kilépés");
    public Window() {
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
        helperFrame.setVisible(true);
        this.setVisible(false);
    }

    public void Exit(){
        System.exit(1);
    }
    public void ShowGameStartFrame(){
        this.setVisible(false);
        gameStartFrame.setVisible(true);
    }

    public void ShowMainWindow(){
        this.setVisible(true);
    }

    public void ShowMainFrame(){
        mainFrame.setVisible(true);
    }

    //Action Listener-ek:
    private class HelpButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ShowHelperFrame();
        }
    }
    private class ExitButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Exit();
        }
    }
    private class PlayButtonListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            ShowGameStartFrame();
        }
    }

}
