package checkers.gui.shapes;

import checkers.gui.board.BoardPosition;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class MovePointer extends Ellipse2D.Double {

    private BoardPosition boardPosition;
    private Color color;

    public MovePointer(BoardPosition boardPosition) {
        this.boardPosition = boardPosition;
        this.x = boardPosition.getPixelX() + 20;
        this.y = boardPosition.getPixelY() + 20;
        this.width = 35;
        this.height = 35;
        this.color = Color.yellow;
    }

    public Color getColor() {
        return color;
    }

    public BoardPosition getBoardPosition() {
        return boardPosition;
    }
}
