package view;

import javax.swing.*;
import java.awt.*;

public class RectPanel extends JPanel {
    private Dimension coords;
    private Color color;
    private Dimension size;
    protected void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(coords.width - size.width / 2,coords.height - size.height / 2,size.width,size.height);
    }

    public RectPanel(Color color, Dimension coords, Dimension size) {
        this.coords = coords;
        this.color = color;
        this.size = size;
    }

    public RectPanel(Color color, Dimension coords, Dimension size, GamePanel p) {
        this.coords = coords;
        this.color = color;
        this.size = size;
        JButton addBtn = new JButton();
        addBtn.setBackground(color);
        addBtn.setBounds(coords.width - size.width / 2,coords.height - size.height / 2,size.width,size.height);
        p.add(addBtn);
        p.AddDoorButton(addBtn);
    }

    public Dimension getPreferredSize() {
        return new Dimension(size.width, size.height); // appropriate constants
    }
}
