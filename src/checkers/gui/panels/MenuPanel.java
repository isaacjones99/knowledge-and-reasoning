package checkers.gui.panels;

import checkers.gui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener {

    // Buttons
    private JButton[] menuButtons;
    private String[] menuButtonText = { "Play", "Help", "Quit" };

    public MenuPanel() {
        setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
        setLayout(null);

        // Create buttons
        menuButtons = new JButton[menuButtonText.length];
        int y = 100;
        for (int i = 0; i < menuButtons.length; i++) {
            menuButtons[i] = new JButton(menuButtonText[i]);
            menuButtons[i].setBounds(250, y, 100, 32);
            menuButtons[i].addActionListener(this);
            add(menuButtons[i]);
            y += 50;
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButtons[0]) {
            GUI.changePanel(new GamePanel());
        }
        if (e.getSource() == menuButtons[1]) {
            GUI.changePanel(new HelpPanel());
        }
        if (e.getSource() == menuButtons[2]) {
            System.exit(0);
        }
    }
}
