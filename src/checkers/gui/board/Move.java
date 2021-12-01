package checkers.gui.board;

import checkers.gui.shapes.Counter;

public class Move {

    int boardX;
    int boardY;
    boolean isCaptureMove;
    Counter capturedCounter;
    Counter counter;

    int dirX;
    int dirY;

    public Move(int boardX, int boardY, Counter counter) {
        this.boardX = boardX;
        this.boardY = boardY;
        this.counter = counter;
    }

    public Counter getCounter() {
        return counter;
    }

    @Override
    public boolean equals(Object obj) {
        Move move = (Move) obj;
        if (move.counter == counter && move.boardX == boardX && move.boardY == boardY)
            return true;
        return false;
    }

    public int getBoardX() {
        return boardX;
    }
    public int getBoardY() {
        return boardY;
    }

    public boolean isCaptureMove() {
        return isCaptureMove;
    }

    public Counter getCapturedCounter() {
        return capturedCounter;
    }

    public void setCaptureMove(boolean isCaptureMove, Counter capturedCounter) {
        this.capturedCounter = capturedCounter;
        this.isCaptureMove = isCaptureMove;
    }

    public void setDir(int dirX, int dirY) {
        this.dirX = dirX;
        this.dirY = dirY;
    }

    public int getDirX() {
        return dirX;
    }

    public int getDirY() {
        return dirY;
    }

    @Override
    public String toString() {
        return "boardX: " + boardX + ", boardY: " + boardY + ", isCaptureMove: " + isCaptureMove;
    }
}
