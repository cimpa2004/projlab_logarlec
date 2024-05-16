package view;

import javax.swing.*;
import java.awt.*;

public class CirclePanel extends JPanel {
    private Color color;
    public CirclePanel(Color c){
        color = c;
    }

    public void paintComponent(Graphics g){
        g.drawOval(0, 0, 25,25);
        g.setColor(color);
        g.fillOval(0, 0, 25,25);
    }
}
