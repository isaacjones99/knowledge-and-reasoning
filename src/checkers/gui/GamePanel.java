package checkers.gui;

import checkers.gui.board.GameBoard;
import checkers.gui.shapes.Counter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

    private GameBoard gameBoard;

    private int selectedCounter = -1;

    public GamePanel() {
        setPreferredSize(new Dimension(GUI.WIDTH, GUI.HEIGHT));

        createGameBoard();

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    private void createGameBoard() {
        gameBoard = new GameBoard();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Render the game board
        gameBoard.render(g2d);

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Point p = e.getPoint();
        Counter[] counters = gameBoard.getCounters();

        if (selectedCounter == -1) {
            // Select a counter
            for (int i = 0; i < counters.length; i++) {
                if (counters[i].contains(p)) {
                    selectedCounter = i;
                    counters[i].isSelected = true;
                }
            }
        } else {
            // Unselect a counter
            counters[selectedCounter].isSelected = false;
            selectedCounter = -1;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (selectedCounter >= 0) {
            Point p = e.getPoint();
            int mouseX = (int) p.getX() - (75 / 2);
            int mouseY = (int) p.getY() - (75 / 2);

            Counter[] counters = gameBoard.getCounters();
            counters[selectedCounter].x = mouseX;
            counters[selectedCounter].y = mouseY;
        }
    }
}
