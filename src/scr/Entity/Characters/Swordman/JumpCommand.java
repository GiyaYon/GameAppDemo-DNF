package scr.Entity.Characters.Swordman;

import scr.Model.Characters.Commands.ICommand;
import scr.LogicalProcessing.Position.Transform;

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
