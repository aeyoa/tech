package t06.src;

/**
 * Created by arsenykogan on 27/05/14.
 */
public class Quad {

    private String x;
    private String y;
    private String size;
    private String color;

    public Quad(final String x, final String y, final String size, final String color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }

    public void setX(final String x) {
        this.x = x;
    }

    public void setY(final String y) {
        this.y = y;
    }

    public void setSize(final String size) {
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