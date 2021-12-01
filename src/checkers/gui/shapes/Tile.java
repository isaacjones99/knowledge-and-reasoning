package checkers.gui.shapes;

import checkers.gui.board.BoardPosition;

import java.awt.*;

public class Tile extends Rectangle {

    public boolean isBlack;
    public Color color;

    BoardPosition boardPosition;

    public Tile(BoardPosition boardPosition, int x, int y) {
        this.x = BoardPosition.getPixelX(x);
        this.y = BoardPosition.getPixelY(y);
        this.width = 75;
        this.height = 75;
    }

    public void setColor(Color color) {
        this.color = color;
        if (color == Color.black)
            isBlack = true;
        else
            isBlack = false;
    }
}
