package scr.Controller.Collide.Colliders;


import scr.Controller.Collide.ShapeProperty;
import scr.Model.Characters.Transform;

/**
 * 是否碰撞
 */
public interface ICollider {

    ShapeProperty getShapeProperty();
    boolean colliderDetect(ICollider s2);

    void updatePosition(Transform transform);
}