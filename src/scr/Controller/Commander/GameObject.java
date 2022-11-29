package scr.Controller.Commander;

import scr.Model.Characters.Transform;
import scr.Model.Characters.Vector2D;

public interface GameObject {
    void move(Vector2D vector2D, Transform transform);
    void idle();
}
