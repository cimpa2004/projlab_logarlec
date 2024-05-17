package view;

import util.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * A játék súgója
 */
class HelperFrame extends JFrame {
    private Window window;
    private JLabel imageLabel;
    private JButton backButton;


    /**
     * Alap konstuctor
     * @param w A tartalmazó window
     */
    public HelperFrame(Window w) {
        if (w == null){
            throw new NullPointerException("Received null Window in constructor");
        }
        window = w;
        setTitle("Súgó");
        setSize(500, 500);
        //kinagyítva lesz egyből:
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());

        //Kép
        imageLabel = new JLabel();
        add(imageLabel, BorderLayout.CENTER);


        //Back gomb
        backButton = new JButton("Vissza");
        backButton.setPreferredSize(new Dimension(50,50));
        backButton.addActionListener(new BackButtonListener());
        add(backButton, BorderLayout.SOUTH);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateImageSize();
            }
        });

        setLocationRelativeTo(w);
        setVisible(false);
    }

    /**
     * Új dispose művelet
     */
    @Override
    public void dispose() {
        Logger.startedView(this, "dispose");
        Back();
        Logger.finishedView(this, "dispose");
    }

    /**
     * Vissza a főmenübe
     */
    private void Back(){
        Logger.startedView(this, "Back");
        this.setVisible(false);
        window.ShowMainWindow();
        Logger.finishedView(this, "Back");
    }

    /**
     * Kép skálázása
     */
    private void updateImageSize() {
        Logger.startedView(this, "updateImageSize");
        if (imageLabel != null) {
            // Get the size of the frame
            int width = getWidth();
            int height = getHeight();

            // Load the original image
            ImageIcon originalIcon = new ImageIcon("HelpImage.png");

            // Scale the image to fit the current size of the frame
            Image scaledImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);

            // Update the image label with the scaled image
            imageLabel.setIcon(scaledIcon);
        }
        Logger.finishedView(this, "updateImageSize");
    }

    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Logger.startedView(this, "BackButtonListener.actionPerformed", e);
            Back();
            Logger.finishedView(this, "BackButtonListener.actionPerformed", e);
        }
    }
}