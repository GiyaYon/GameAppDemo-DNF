package scr.Model.Characters.Commands;

import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Characters.Forces.AttackEffect;
import scr.Model.Characters.Forces.AttackType;

public class InjureCommand implements ICommand{
    GameObjectAction go;
    AttackType vector2D;
    public InjureCommand(GameObjectAction go, AttackType hitVector2D)
    {
        this.go = go;
        this.vector2D = hitVector2D;
    }
    @Override
    public void Execute() {
        go.injure(vector2D);
    }
}
