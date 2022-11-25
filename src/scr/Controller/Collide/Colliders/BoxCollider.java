package scr.Controller.Collide.Colliders;

import scr.Controller.Collide.ColliderShape;
import scr.Controller.Collide.Detector;
import scr.Controller.Collide.ShapeProperty;

public class BoxCollider extends Detector {

    public ShapeProperty s1;

    public BoxCollider(int x, int y,int w,int h) {
        s1 = new ShapeProperty(ColliderShape.Rect,x,y,w,h);
    }

    @Override
    public boolean ColliderDetect(ShapeProperty s2) {
        return isIntersect(this.s1,s2);
    }
}
