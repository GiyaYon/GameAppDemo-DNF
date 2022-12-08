package scr.Model.Characters.Commands;

import scr.Model.Characters.Commands.ICommand;
import scr.Model.Characters.Commands.GameObjectAction;

public class NoneCommand implements ICommand {

    GameObjectAction go;
    public NoneCommand(GameObjectAction go)
    {
        this.go = go;
    }
    @Override
    public void Execute() {
        go.idle();
    }
}
