package scr.Model.Characters.Commands;

import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Characters.Forces.AttackType;

public class ThrowFlyCommand implements ICommand
{
    GameObjectAction go;
    AttackType vector2D;
    public ThrowFlyCommand(GameObjectAction go, AttackType vector2D)
    {
        this.go = go;
        this.vector2D = vector2D;
    }
    @Override
    public void Execute() {
        go.throwFly(vector2D);
    }
}
