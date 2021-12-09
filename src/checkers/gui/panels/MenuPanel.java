package checkers.gui.panels;

import checkers.gui.Difficulty;
import checkers.gui.GUI;
import checkers.gui.Options;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel implements ActionListener {

    // Buttons
    private JButton[] menuButtons;
    private String[] menuButtonText = { "Play", "Help", "Quit" };

    // Who goes first option
    private JLabel lblFirstPlayer;
    private ButtonGroup btnPlayerGroup;
    private JRadioButton chkPlayer;
    private JRadioButton chkAI;

    // Difficulty option
    private JLabel lblDifficulty;
    private JComboBox<Difficulty> cmbDifficulty;

    // Force capture option
    private JLabel lblForceCapture;
    private JCheckBox chkForceCapture;

    // Display available moves option
    private JCheckBox chkShowMoves;

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

        // Create who goes first radio buttons
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

        // Create difficult combobox
        lblDifficulty = new JLabel("Select difficulty");
        lblDifficulty.setFont(new Font("Font", Font.BOLD, 12));
        lblDifficulty.setBounds(200, 460, 100, 32);
        add(lblDifficulty);

        cmbDifficulty = new JComboBox<>();
        cmbDifficulty.setBounds(200, 490, 100, 24);
        cmbDifficulty.addItem(Difficulty.Random);
        cmbDifficulty.addItem(Difficulty.minimax);
        cmbDifficulty.setSelectedItem(Difficulty.minimax);
        add(cmbDifficulty);

        // Force capture checkbox
        lblForceCapture = new JLabel("Other options");
        lblForceCapture.setFont(new Font("Font", Font.BOLD, 12));
        lblForceCapture.setBounds(350, 460, 100, 32);
        add(lblForceCapture);

        chkForceCapture = new JCheckBox("Force captures");
        chkForceCapture.setBounds(350, 490, 150, 24);
        chkForceCapture.setSelected(true);
        add(chkForceCapture);

        // Show available moves
        chkShowMoves = new JCheckBox("Show available moves");
        chkShowMoves.setBounds(350, 510, 150, 24);
        chkShowMoves.setSelected(true);
        add(chkShowMoves);
    }

    private void setGameOptions() {
        Options.forceCapture = chkForceCapture.isSelected();
        Options.showMoves = chkShowMoves.isSelected();
        Options.difficulty = (Difficulty) cmbDifficulty.getSelectedItem();
        if (chkPlayer.isSelected())
            Options.firstPlayer = 0;
        else if (chkAI.isSelected())
            Options.firstPlayer = 1;

        System.out.println("Game options set - force capture: " + Options.forceCapture + ", difficulty: " + Options.difficulty);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuButtons[0]) {
            setGameOptions();
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
