package checkers.gui.shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Counter extends Ellipse2D.Double {

    public Color color;
    public boolean isSelected;
    public boolean isKing;

    public Counter(int x, int y, int width, int height) {
        super(x, y, width, height);

        isSelected = false;
        isKing = false;
    }
}
