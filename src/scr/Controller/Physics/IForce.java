package scr.Controller.Physics;

import scr.Model.Characters.Transform;
import scr.Model.Characters.Vector2D;

public interface IForce {
    Vector2D ForceResult(float mt);
}
