package scr.Entity.Swordman;

import scr.Controller.Commander.ICommand;
import scr.Model.Characters.Transform;
import scr.Model.Characters.Vector2D;

public class JumpCommand implements ICommand {
    SwordsmanCommand sc;
    Transform transform;
    public JumpCommand(SwordsmanCommand sc,Transform transform)
    {
        this.sc = sc;
        this.transform = transform;
    }
    @Override
    public void Execute() {
        sc.jump(transform);
    }
}
