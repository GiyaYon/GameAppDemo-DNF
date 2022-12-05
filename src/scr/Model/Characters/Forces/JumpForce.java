package scr.Model.Characters.Forces;

import scr.LogicalProcessing.Physics.Gravity;
import scr.LogicalProcessing.Physics.IForce;
import scr.Model.Characters.Position.Vector2D;

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
