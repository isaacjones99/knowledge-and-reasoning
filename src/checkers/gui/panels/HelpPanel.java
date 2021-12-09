package checkers.gui.panels;

import checkers.gui.GUI;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;

public class HelpPanel extends JPanel implements ActionListener {

    private JButton btnBack;
    private JEditorPane helpPane;

    private String htmlText = "" +
            "<h1>Help</h1>" +
            "<h2>Game Rules</h2>" +
            "Checkers is played on an 8x8 board with black and white squares. Counters are placed and played on the black squares. Each player will begin with 12 counters and the objective is to capture all of the other players counters." +
            "<br><br>" +
            "Counters are able to move forward diagonally only playing on the black squares. A capturing move can be made by jumping over the other player's counter into an empty square.<br><br>" +
            "If a counter reaches the other player's edge of the board the counter will become a king which allows it to move in either direction. A counter can also become a king by capturing any of the other player's kings. In the game a king has a gold border around the counter." +
            "Game rules taken from <a href=\"https://a4games.company/checkers-rules-and-layout/\">a4 games</a>." +
            "<h2>Game Instructions</h2>" +
            "To move a counter, select the counter you would like to move. Then click on the square you would like to place the counter." +
            "<h2>Game Options</h2>" +
            "You are able to choose the following options:<br><br>" +
            "<b>First player</b>" +
            "<ul>" +
            "<li>Human (you)</li>" +
            "<li>AI</li>" +
            "</ul>" +
            "<b>Force capture</b><br>" +
            "If force capture is selected then when a player is able to make a capture they will not be able to make any other move.<br><br>" +
            "<b>Difficulty</b>" +
            "<ul>" +
            "<li>Random - The opponent will make random moves</li>" +
            "</ul>";

    public HelpPanel() {
        setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));
        setLayout(null);

        btnBack = new JButton("Back to menu");
        btnBack.setBounds((GUI.WIDTH / 2) - (100 / 2), GUI.HEIGHT - 40, 100, 32);
        btnBack.addActionListener(this);
        add(btnBack);

        // Create editor pane
        helpPane = new JEditorPane();
        helpPane.setEditable(false);

        JScrollPane helpScrollPane = new JScrollPane(helpPane);
        helpScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        helpScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        helpScrollPane.setBounds(0, 0, GUI.WIDTH, GUI.HEIGHT - 50);

        helpPane.setContentType("text/html");
        helpPane.setText(htmlText);
        helpPane.addHyperlinkListener(new HyperlinkListener() {
            @Override
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    if (Desktop.isDesktopSupported()) {
                        try {
                            Desktop.getDesktop().browse(e.getURL().toURI());
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        add(helpScrollPane);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnBack) {
            GUI.changePanel(new MenuPanel());
        }
    }
}
