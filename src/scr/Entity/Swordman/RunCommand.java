package scr.Entity.Swordman;

import scr.Controller.Commander.ICommand;
import scr.Model.Characters.Transform;
import scr.Model.Characters.Vector2D;

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
