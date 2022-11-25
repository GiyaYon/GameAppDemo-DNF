package scr.Controller.Collide.Colliders;

import scr.Controller.Collide.ColliderShape;
import scr.Controller.Collide.Detector;
import scr.Controller.Collide.ShapeProperty;

public class OvalCollider extends Detector {
    public ShapeProperty s1;

    public OvalCollider(int x, int y,int r) {
        s1 = new ShapeProperty(ColliderShape.Oval,x,y,r);
    }

    @Override
    public boolean ColliderDetect(ShapeProperty s2) {
        return isIntersect(this.s1,s2);
    }
}
