package scr.Controller.Collide.Colliders;

import scr.Controller.Collide.ColliderShape;
import scr.Controller.Collide.Detector;
import scr.Controller.Collide.ShapeProperty;
import scr.Model.Characters.Transform;
import scr.Model.Characters.Vector2D;

public class BoxCollider extends Detector implements Collider {

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
    public boolean colliderDetect(Collider s2) {
        assert false;
        return isIntersect(s1,s2.getShapeProperty());
    }

    @Override
    public void updatePosition(Transform transform) {
        s1.x = transform.xPos;
        s1.y = transform.yPos;
    }
}
