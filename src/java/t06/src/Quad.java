package t06.src;

/**
 * Created by arsenykogan on 27/05/14.
 */
public class Quad {

    private int x;
    private int y;
    private int size;
    private String color;

    public Quad(final int x, final int y, final int size, final String color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public void setColor(final String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Quad{" +
                "x=" + x +
                ", y=" + y +
                ", size=" + size +
                ", color=" + color +
                '}';
    }
}