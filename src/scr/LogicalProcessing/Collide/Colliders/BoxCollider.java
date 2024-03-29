package scr.LogicalProcessing.Collide.Colliders;

import scr.LogicalProcessing.Collide.ColliderShape;
import scr.LogicalProcessing.Collide.Detector;
import scr.LogicalProcessing.Collide.ShapeProperty;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;

public class BoxCollider extends Detector implements ICollider {

    public ShapeProperty s1;

    public BoxCollider(int x, int y,int w,int h,Vector2D vector2D) {
        s1 = new ShapeProperty(ColliderShape.Rect,x,y,w,h);
        this.vector2D = vector2D;
    }

    public Vector2D vector2D;
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
    public void updatePosition(Transform transform,Vector2D wh) {
        s1.x = transform.xPos;
        s1.y = transform.yPos;
        s1.w = wh.x;
        s1.h = wh.y;
    }

}
