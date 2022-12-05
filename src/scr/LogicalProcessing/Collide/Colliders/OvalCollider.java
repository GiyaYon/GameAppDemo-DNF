package scr.LogicalProcessing.Collide.Colliders;

import scr.LogicalProcessing.Collide.ColliderShape;
import scr.LogicalProcessing.Collide.Detector;
import scr.LogicalProcessing.Collide.ShapeProperty;
import scr.Model.Characters.Position.Transform;

public class OvalCollider extends Detector implements ICollider {
    public ShapeProperty s1;

    public OvalCollider(int x, int y,int r) {
        s1 = new ShapeProperty(ColliderShape.Oval,x,y,r);
    }

    @Override
    public ShapeProperty getShapeProperty() {
        return s1;
    }

    @Override
    public boolean colliderDetect(ICollider s2) {
        assert false;
        return isIntersect(this.s1,s2.getShapeProperty());
    }

    @Override
    public void updatePosition(Transform transform) {
        s1.x = transform.xPos;
        s1.y = transform.yPos;
    }
}
