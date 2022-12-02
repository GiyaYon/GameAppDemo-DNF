package scr.Model.Characters;

import scr.Controller.Physics.Gravity;
import scr.Controller.Physics.IForce;

public class JumpForce implements IForce {

    public Gravity gravity;

    public JumpForce(int v,float t) {
        gravity = new Gravity(v,t);
    }

    public Vector2D ForceResult(float mt)
    {
        return new Vector2D(0,gravity.resultVy(mt));
    }

}
