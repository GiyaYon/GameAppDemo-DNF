package scr.Model.Characters.Commands;

import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Characters.Forces.AttackType;

public interface GameObjectAction {
    void move(Vector2D vector2D, Transform transform);
    void idle();
    void injure(AttackType vector2D);
    void throwFly(AttackType vector2D);
    void death();
}
