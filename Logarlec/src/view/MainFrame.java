package view;

import viewmodel.ICInit;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MainFrame extends JFrame implements ICInit {
    private GamePanel gamePanel = new GamePanel();
    private ControlPanel controlPanel = new ControlPanel();


    public MainFrame(){
        setTitle("Logarléc");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());
        add(gamePanel);
        add(controlPanel);

    }
}
