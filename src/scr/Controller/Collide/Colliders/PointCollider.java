package scr.Controller.Collide.Colliders;

import scr.Controller.Collide.ColliderShape;
import scr.Controller.Collide.Detector;
import scr.Controller.Collide.ShapeProperty;

public class PointCollider extends Detector {
    public ShapeProperty s1;

    public PointCollider(int x, int y) {
        s1 = new ShapeProperty(ColliderShape.Point,x,y);
    }

    @Override
    public boolean ColliderDetect(ShapeProperty s2) {
        return isIntersect(this.s1,s2);
    }

}
