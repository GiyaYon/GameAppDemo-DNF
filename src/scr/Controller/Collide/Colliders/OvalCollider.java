package scr.Controller.Collide.Colliders;

import scr.Controller.Collide.ColliderShape;
import scr.Controller.Collide.Detector;
import scr.Controller.Collide.ShapeProperty;
import scr.Model.Characters.Transform;

public class OvalCollider extends Detector implements Collider {
    public ShapeProperty s1;

    public OvalCollider(int x, int y,int r) {
        s1 = new ShapeProperty(ColliderShape.Oval,x,y,r);
    }

    @Override
    public ShapeProperty getShapeProperty() {
        return s1;
    }

    @Override
    public boolean colliderDetect(Collider s2) {
        assert false;
        return isIntersect(this.s1,s2.getShapeProperty());
    }

    @Override
    public void updatePosition(Transform transform) {
        s1.x = transform.xPos;
        s1.y = transform.yPos;
    }
}
