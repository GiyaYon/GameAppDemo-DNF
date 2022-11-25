package scr.Controller.Collide;

public class ShapeProperty {
    public ColliderShape shape;
    public int x,y;
    public int w,h;
    public int r;

    public ShapeProperty(ColliderShape shape, int x, int y, int r) {
        this.shape = shape;
        this.x = x;
        this.y = y;
        this.r = r;
    }

    public ShapeProperty(ColliderShape shape, int x, int y, int w, int h) {
        this.shape = shape;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public ShapeProperty(ColliderShape shape, int x, int y) {
        this.shape = shape;
        this.x = x;
        this.y = y;
    }

}
