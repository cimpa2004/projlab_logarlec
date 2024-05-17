package view;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.Border;

public class MapCirclePanel extends JPanel {

    private Dimension coords;
    private Color color;
    private int rad;
    protected void draw(Graphics g) {
        g.setColor(color);
        g.fillOval(coords.width,coords.height,rad,rad);
    }

    public MapCirclePanel(Color color, Dimension coords, int rad, GamePanel p) {
        this.coords = coords;
        this.color = color;
        this.rad = rad;
        JButton addBtn = new JButton();
        addBtn.setBorder(new RoundedBorder(rad));
        addBtn.setBounds(coords.width, coords.height, rad, rad);
        addBtn.setForeground(color);
        addBtn.setBackground(new Color(128, 128, 128));
        p.add(addBtn);
        p.AddItemButton(addBtn);
    }

    //Forrás: https://stackoverflow.com/questions/423950/rounded-swing-jbutton-using-java
    private static class RoundedBorder implements Border {

        private int radius;

        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        }

        public boolean isBorderOpaque() {
            return true;
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.fillRoundRect(x, y, width-1, height-1, radius, radius);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(rad, rad); // appropriate constants
    }
}
