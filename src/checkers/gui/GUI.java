package checkers.gui;

import checkers.gui.panels.GamePanel;
import checkers.gui.panels.MenuPanel;

import javax.swing.*;
import java.awt.*;

public class GUI {

    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    private static JFrame window;

    private MenuPanel menuPanel;

    public GUI() {
        menuPanel = new MenuPanel();
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
        window.setContentPane(menuPanel);
        window.setVisible(true);
    }

    public static void changePanel(JPanel panel) {
        window.setContentPane(panel);
        window.pack();
    }
}
