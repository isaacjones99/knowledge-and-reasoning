package checkers.gui.shapes;

import checkers.gui.board.BoardPosition;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Counter extends Ellipse2D.Double {

    private BoardPosition boardPosition;
    public Color color;
    public boolean isAI;
    private boolean isKing;
    public int yDir;

    public Counter(BoardPosition boardPosition, boolean isAI) {
        this.boardPosition = boardPosition;
        boardPosition.setCounter(this);
        this.isAI = isAI;
        this.x = boardPosition.getPixelX();
        this.y = boardPosition.getPixelY();
        this.width = 75;
        this.height = 75;
        isKing = false;

        // TODO - This probably needs to be based on who goes first?
        if (isAI)
            yDir = 1;
        else
            yDir = -1;

        System.out.println("Counter created - " + this);
    }

    public void setColor(Color color) {
        this.color = color;
    }
    public BoardPosition getBoardPosition() {
        return boardPosition;
    }
    public void setBoardPosition(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;

        if (isAI && boardPosition.getY() == 7)
            isKing = true;
        else if (!isAI && boardPosition.getY() == 0)
            isKing = true;
    }

    public boolean getIsKing() {
        return isKing;
    }

    public void setIsKing(boolean isKing) {
        this.isKing = isKing;
        yDir = 0;
    }

    public int getyDir() {
        return yDir;
    }

    public String toString() {
        return "Board position: " + boardPosition + ", isAI: " + isAI + ", isKing: " + isKing + ", yDir: " + yDir;
    }
}
