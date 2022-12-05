package scr.LogicalProcessing.Commander;

import scr.Model.Characters.Position.Transform;
import scr.Model.Characters.Position.Vector2D;

public interface GameObject {
    void move(Vector2D vector2D, Transform transform);
    void idle();
}
