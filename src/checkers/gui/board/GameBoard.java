package checkers.gui.board;

import checkers.gui.shapes.Counter;
import checkers.gui.shapes.Tile;

import java.awt.*;
import java.util.Vector;

public class GameBoard {

    private BoardPosition[][] boardPositions;

    // Board tiles array
    private Tile[][] boardTiles;

    // Counter arrays
    private Vector<Counter> playerCounters;
    private Vector<Counter> AICounters;

    public GameBoard() {
        boardPositions = new BoardPosition[8][8];
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++)
                boardPositions[y][x] = new BoardPosition(x, y);
        }
        createGameBoard();
        createPlayerCounters();
        createAICounters();
    }

    private void createGameBoard() {
        boardTiles = new Tile[8][8];

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                boardTiles[y][x] = new Tile(boardPositions[y][x], x, y);

                // Set the tiles colour
                Color color = Color.black;
                if ((x + y) % 2 == 0) color = Color.white;
                boardTiles[y][x].setColor(color);
            }
        }
    }

    private void createPlayerCounters() {
        playerCounters = new Vector<>();

        int x = 0;
        int y = 7;
        for (int i = 0; i < 12; i++) {
            BoardPosition bPos = boardPositions[y][x];
            Counter counter = new Counter(bPos, false);
            counter.setColor(Color.red);
            playerCounters.add(counter);

            if (i == 3) {
                y--;
                x = 1;
            } else if (i == 7) {
                y--;
                x = 0;
            } else x += 2;
        }
    }

    private void createAICounters() {
        AICounters = new Vector<>();

        int x = 1;
        int y = 0;
        for (int i = 0; i < 12; i++) {
            BoardPosition bPos = boardPositions[y][x];
            Counter counter = new Counter(bPos, true);
            counter.setColor(Color.blue);
            AICounters.add(counter);

            if (i == 3) {
                y++;
                x = 0;
            } else if (i == 7) {
                y++;
                x = 1;
            } else x += 2;
        }
    }

    public void removeCounter(Counter counter) {
        // TODO - Delete this.
        System.out.println("Removing counter: " + counter);

        int counterX = counter.getBoardPosition().getX();
        int counterY = counter.getBoardPosition().getY();
        boardPositions[counterY][counterX].removeCounter();

        if (counter.isAI)
            AICounters.remove(counter);
        else
            playerCounters.remove(counter);
    }

    public void render(Graphics2D g2d) {
        // Render tiles
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                Tile tile = boardTiles[y][x];
                g2d.setColor(tile.color);
                g2d.fill(tile);
            }
        }

        // Render counters
        Vector<Counter> allCounters = new Vector<>();
        allCounters.addAll(AICounters);
        allCounters.addAll(playerCounters);

        for (Counter counter : allCounters) {
            g2d.setColor(counter.color);
            g2d.fill(counter);

            if (counter.getIsKing()) {
                g2d.setColor(new Color(217, 174, 48));
                g2d.draw(counter);
            }
        }
    }

    public Vector<Counter> getPlayerCounters() { return playerCounters; }
    public Vector<Counter> getAICounters() { return AICounters; }
    public Tile[][] getBoardTiles() {
        return boardTiles;
    }

    public BoardPosition[][] getBoardPositions() {
        return boardPositions;
    }
}
