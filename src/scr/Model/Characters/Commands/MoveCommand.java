package scr.Model.Characters.Commands;

import scr.Model.Characters.Commands.ICommand;
import scr.Model.Characters.Commands.GameObjectAction;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;


public class MoveCommand implements ICommand

{
    GameObjectAction go;
    Vector2D vector2D;
    Transform transform;
    public MoveCommand(GameObjectAction go, Vector2D vector2D, Transform transform)
    {
        this.go = go;
        this.vector2D = vector2D;
        this.transform = transform;
    }
    @Override
    public void Execute() {

        go.move(vector2D,transform);
    }
}

