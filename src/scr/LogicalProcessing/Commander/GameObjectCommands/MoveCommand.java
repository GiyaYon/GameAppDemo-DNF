package scr.LogicalProcessing.Commander.GameObjectCommands;

import scr.LogicalProcessing.Commander.ICommand;
import scr.LogicalProcessing.Commander.GameObject;
import scr.Model.Characters.Position.Transform;
import scr.Model.Characters.Position.Vector2D;


public class MoveCommand implements ICommand

{
    GameObject go;
    Vector2D vector2D;
    Transform transform;
    public MoveCommand(GameObject go, Vector2D vector2D, Transform transform)
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

