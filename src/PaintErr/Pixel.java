package PaintErr;

import javafx.scene.paint.Color;

public class Pixel {

    int x, y;
    Color color;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Pixel(int x, int y) {

        this.x = x;
        this.y = y;

    }
}
