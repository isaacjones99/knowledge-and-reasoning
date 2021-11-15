package checkers.gui.panels;

import checkers.gui.GUI;
import checkers.gui.board.GameBoard;
import checkers.gui.shapes.Counter;
import checkers.gui.shapes.Square;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

    private GameBoard gameBoard;

    private int selectedCounter = -1;
    private int selectedCounterX;
    private int selectedCounterY;

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

                    selectedCounterX = (int) counters[i].x;
                    selectedCounterY = (int) counters[i].y;
                }
            }
        } else {
            // Unselect a counter
            counters[selectedCounter].isSelected = false;
            System.out.println("Is move valid: " + checkMove(p, counters, selectedCounter));
            selectedCounter = -1;
        }
    }

    private boolean checkMove(Point p, Counter[] counters, int selectedCounter) {
        Square[][] tiles = gameBoard.getBoardTiles();
        int[] selectedTile = { -1, -1 };

        boolean isValid = false;
        for (int y0 = 0; y0 < 8; y0++) {
            for (int x0 = 0; x0 < 8; x0++) {
                if (tiles[y0][x0].contains(p) && tiles[y0][x0].isBlack) {
                    selectedTile[0] = y0;
                    selectedTile[1] = x0;
                    isValid = true;
                    break;
                }
            }
        }

        // Snap counter to centre of tile.
        if (isValid) {
            counters[selectedCounter].x = tiles[selectedTile[0]][selectedTile[1]].x;
            counters[selectedCounter].y = tiles[selectedTile[0]][selectedTile[1]].y;
        } else {
            // Move counter back to original location.
            counters[selectedCounter].x = selectedCounterX;
            counters[selectedCounter].y = selectedCounterY;
        }

        return isValid;
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
