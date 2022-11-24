package scr.Entity.Swordman;

import scr.Controller.Commander.ICommand;

public class JumpCommand implements ICommand {
    SwordsmanCommand sc;

    public JumpCommand(SwordsmanCommand sc)
    {
        this.sc = sc;
    }
    @Override
    public void Execute() {
        sc.jump();
    }
}
