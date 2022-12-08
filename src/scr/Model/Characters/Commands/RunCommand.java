package scr.Model.Characters.Commands;

import scr.Entity.Characters.Swordman.SwordsmanCommand;
import scr.Model.Characters.Commands.ICommand;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;

public class RunCommand implements ICommand {

    SwordsmanCommand sc;
    Vector2D vector2D;
    Transform transform;
    public RunCommand(SwordsmanCommand sc,Vector2D vector2D, Transform transform)
    {
        this.sc = sc;
        this.vector2D = vector2D;
        this.transform = transform;
    }
    @Override
    public void Execute() {
        sc.run(vector2D,transform);
    }
}
