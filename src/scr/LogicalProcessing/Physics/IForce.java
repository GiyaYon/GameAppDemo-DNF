package scr.LogicalProcessing.Physics;

import scr.Model.Characters.Position.Vector2D;

public interface IForce {
    Vector2D ForceResult(float mt);
}
