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

    private JLabel lblFirstPlayer;
    private ButtonGroup btnPlayerGroup;
    private JRadioButton chkPlayer;
    private JRadioButton chkAI;

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

        btnPlayerGroup = new ButtonGroup();

        lblFirstPlayer = new JLabel("Who goes first?");
        lblFirstPlayer.setFont(new Font("font", Font.BOLD, 12));
        lblFirstPlayer.setBounds(16, 460, 150, 32);
        add(lblFirstPlayer);

        chkPlayer = new JRadioButton("Player");
        chkPlayer.setSelected(true);
        chkPlayer.setBounds(16, 490, 100, 32);
        btnPlayerGroup.add(chkPlayer);
        add(chkPlayer);

        chkAI = new JRadioButton("AI");
        chkAI.setBounds(16, 522, 100, 32);
        btnPlayerGroup.add(chkAI);
        add(chkAI);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButtons[0]) {
            if (chkPlayer.isSelected())
                GUI.changePanel(new GamePanel(0));
            else if (chkAI.isSelected())
                GUI.changePanel(new GamePanel(1));
            else
                JOptionPane.showMessageDialog(this, "Select who goes first.");
        }
        if (e.getSource() == menuButtons[1]) {
            GUI.changePanel(new HelpPanel());
        }
        if (e.getSource() == menuButtons[2]) {
            System.exit(0);
        }
    }
}
