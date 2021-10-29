package checkers.gui.shapes;

import java.awt.*;

public class Square extends Rectangle {

    public boolean isBlack;
    public Color color;

    public Square(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void setColor(Color color) {
        this.color = color;
        if (color == Color.black)
            isBlack = true;
        else
            isBlack = false;
    }
}
