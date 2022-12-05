package scr.Entity.Swordman;

import scr.LogicalProcessing.Commander.ICommand;
import scr.Model.Characters.Position.Transform;

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
