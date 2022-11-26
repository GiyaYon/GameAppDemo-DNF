package scr.Model.Characters;

import scr.Controller.Physics.Force;
import scr.Controller.Physics.Gravity;
import scr.Controller.Physics.IForce;

import java.util.TreeSet;

public class FloatAirForce implements IForce {
    Force xF;
    Gravity gF;

    public FloatAirForce(int a,float t,int vx,int vy) {
        xF = new Force(vx,a,t);
        gF = new Gravity(vy,t);
    }

    @Override
    public Transform ForceResult(float mt) {
        return new Transform(xF.resultVy(mt), gF.resultVy(mt));
    }
}
