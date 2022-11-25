package scr.Controller.Collide;


/**
 * 是否碰撞
 */
public interface Collider {

    boolean isIntersect(ShapeProperty s1, ShapeProperty s2);
}
