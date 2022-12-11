package scr.LogicalProcessing.Robot;

public class Node {
    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Node(int x,int y,int stepType)
    {
        this.x = x;
        this.y = y;
        this.stepType = stepType;
    }
    public int x;
    public int y;

    public int F;
    public int G;
    public int H;

    public int stepType;

    public void calcF() {
        this.F = this.G + this.H;
    }

    public Node parent;
}