package scr.Model.Characters.Commands;

import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;

public interface GameObjectAction {
    void move(Vector2D vector2D, Transform transform);
    void idle();
    void injure();
    void throwFly();

}
