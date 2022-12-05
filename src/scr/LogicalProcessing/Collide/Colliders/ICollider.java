package scr.LogicalProcessing.Collide.Colliders;


import scr.LogicalProcessing.Collide.ShapeProperty;
import scr.Model.Characters.Position.Transform;

/**
 * 是否碰撞
 */
public interface ICollider {

    ShapeProperty getShapeProperty();
    boolean colliderDetect(ICollider s2);

    void updatePosition(Transform transform);
}
