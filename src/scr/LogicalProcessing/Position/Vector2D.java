package scr.LogicalProcessing.Position;

public class Vector2D {
    public int x,y;
    public Vector2D()
    {

    }
    public Vector2D(int x,int y)
    {
        this.x = x;
        this.y = y;
    }

    public boolean compare(Vector2D vector2D)
    {
        return this.x == vector2D.x && this.y == vector2D.y;
    }
}
