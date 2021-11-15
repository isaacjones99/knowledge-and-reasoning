package checkers.gui.board;

import checkers.gui.shapes.Counter;
import checkers.gui.shapes.Square;

import java.awt.*;

public class GameBoard {

    // Create the board tiles
    private Square[][] boardTiles;

    // Create the board counters
    private Counter[] playerCounters;
    private Counter[] AICounters;

    private boolean boardCreated = false;

    public GameBoard() {
        createBoard();
        createPlayerCounters();
    }

    private void createPlayerCounters() {
        playerCounters = new Counter[12];

        int x = 0;
        int y = 600 - 75;
        for (int i = 0; i < playerCounters.length; i++) {
            playerCounters[i] = new Counter(x, y);
            x += 150;

            if (i == 3) {
                y -= 75;
                x = 75;
            } else if (i == 7) {
                y -= 75;
                x = 0;
            }
        }
    }

    private void createAICounters() {
        AICounters = new Counter[12];

        int x = 0;
        int y = 0;
        for (int i = 0; i < playerCounters.length; i++) {
            AICounters[i] = new Counter(x, y);
            x += 150;

            if (i == 3) {
                y -= 75;
                x = 75;
            } else if (i == 7) {
                y -= 75;
                x = 0;
            }
        }
    }

    /**
     * Create the game board.
     */
    private void createBoard() {
        boardTiles = new Square[8][8];

        int x = 0;
        int y = 0;

        int toggle = 0;
        for (int y0 = 0; y0 < 8; y0++) {
            for (int x0 = 0; x0 < 8; x0++) {
                boardTiles[y0][x0] = new Square(x, y, 75, 75);

                // Set the tiles colour
                if ((x0 + y0) % 2 == 0) boardTiles[y0][x0].setColor(Color.white);
                else boardTiles[y0][x0].setColor(Color.black);
                x += 75;
            }
            x = 0;
            y += 75;
        }
        boardCreated = true;
    }

    public void render(Graphics2D g2d) {
        if (!boardCreated) return;
        // Render tiles
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Square tile = boardTiles[y][x];
                g2d.setColor(tile.color);
                g2d.fill(tile);
            }
        }

        // Render counters
        for (int i = 0; i < playerCounters.length; i++) {
            Counter counter = playerCounters[i];
            g2d.setColor(Color.blue);
            g2d.fill(counter);

            if (counter.isSelected) {
                g2d.setColor(Color.red);
                g2d.draw(counter);
            }
        }
    }

    public Counter[] getCounters() {
        return playerCounters;
    }
    public Square[][] getBoardTiles() { return boardTiles; }
}
