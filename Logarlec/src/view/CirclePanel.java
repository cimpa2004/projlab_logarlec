package view;

import util.Logger;

import javax.swing.*;
import java.awt.*;

public class CirclePanel extends JPanel {
    private Color color;
    public CirclePanel(Color c){
        color = c;
    }

    public void paintComponent(Graphics g){
        Logger.startedView(this, "paintComponent", g);
        g.drawOval(35, 10, 130,130);
        g.setColor(color);
        g.fillOval(35, 10, 130,130);
        Logger.finishedView(this, "paintComponent", g);
    }
}
