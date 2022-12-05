package scr.LogicalProcessing.Collide.Colliders;

import scr.LogicalProcessing.Collide.ColliderShape;
import scr.LogicalProcessing.Collide.Detector;
import scr.LogicalProcessing.Collide.ShapeProperty;
import scr.Model.Characters.Position.Transform;

public class PointCollider extends Detector implements ICollider {
    public ShapeProperty s1;

    public PointCollider(int x, int y) {
        s1 = new ShapeProperty(ColliderShape.Point,x,y);
    }

    @Override
    public ShapeProperty getShapeProperty() {
        return s1;
    }

    @Override
    public boolean colliderDetect(ICollider s2) {
        assert false;
        return isIntersect(s1,s2.getShapeProperty());
    }


    @Override
    public void updatePosition(Transform transform) {
        s1.x = transform.xPos;
        s1.y = transform.yPos;
    }


}
