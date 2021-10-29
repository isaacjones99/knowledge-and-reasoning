package checkers.gui;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    private JFrame window;
    private GamePanel gamePanel;

    public GUI() {
        gamePanel = new GamePanel();

        createWindow();
    }

    private void createWindow() {
        Dimension windowSize = new Dimension(WIDTH, HEIGHT);

        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(windowSize);
        window.setResizable(false);
        window.setLocationRelativeTo(null);
        window.setTitle("Checkers");
        window.add(gamePanel);
        window.pack();
        window.setVisible(true);
    }
}
