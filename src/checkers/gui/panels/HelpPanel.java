package checkers.gui.panels;

import checkers.gui.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelpPanel extends JPanel implements ActionListener {

    private JLabel lblTitle;
    private JButton btnBack;

    public HelpPanel() {
        setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
        setLayout(null);

        lblTitle = new JLabel("Help");
        lblTitle.setBounds(250, 100, 100, 32);
        lblTitle.setFont(new Font("myFont", 1, 32));
        add(lblTitle);

        btnBack = new JButton("Back to menu");
        btnBack.setBounds(250, 200, 100, 32);
        btnBack.addActionListener(this);
        add(btnBack);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            GUI.changePanel(new MenuPanel());
        }
    }
}
