package scr.Controller.Physics;

public class Force {
    int v;
    int a;
    float t;

    public Force(int v, int a, float t) {
        this.v = v;
        this.a = a;
        this.t = t;
    }

    public int resultVy(float mt)
    {
        return v = (int)(v + a * mt);
    }
}
