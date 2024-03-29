package scr.Model.Characters.Forces;

import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.Physics.Gravity;
import scr.LogicalProcessing.Physics.IForce;
import scr.LogicalProcessing.Position.Vector2D;

public class FloatAirForce implements IForce {
    Force xF;
    Gravity gF;

    public FloatAirForce(int a,float t,int vx,int vy) {
        xF = new Force(vx,a,t);
        gF = new Gravity(vy,t);
    }

    @Override
    public Vector2D ForceResult(float mt) {
        return new Vector2D(xF.resultVy(mt), gF.resultVy(mt));
    }
}
