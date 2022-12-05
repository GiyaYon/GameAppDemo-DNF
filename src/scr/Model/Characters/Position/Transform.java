package scr.Model.Characters.Position;

public class Transform {
    public int xPos,yPos;
    public int flipX;

    public final int FLIP_RIGHT = 1;
    public final int FLIP_LEFT = -1;

    public Transform() {

    }

    public Transform(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }
}
