package checkers.gui.board;

import checkers.gui.shapes.Counter;

import java.awt.*;

public class BoardPosition {

    private int x;
    private int y;
    private Counter counter = null;

    public BoardPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCounter(Counter counter) {
        this.counter = counter;
    }
    public void removeCounter() {
        counter = null;
    }
    public boolean containsCounter() { return counter != null; }
    public Counter getCounter() {
        return counter;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getPixelX() {
        return x * 75;
    }
    public int getPixelY() {
        return y * 75;
    }
    public static int getPixelX(int x) {
        return x * 75;
    }
    public static int getPixelY(int y) {
        return y * 75;
    }
    public static int[] getBoardPos(Point p) {
        int x = (int) p.getX() / 75;
        int y = (int) p.getY() / 75;
        return new int[] { x, y };
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
