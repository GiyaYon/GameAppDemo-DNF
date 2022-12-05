package scr.Model.Characters.Commands.GameObjectCommands;

import scr.Model.Characters.Commands.ICommand;
import scr.Model.Characters.Commands.GameObject;

public class NoneCommand implements ICommand {

    GameObject go;
    public NoneCommand(GameObject go)
    {
        this.go = go;
    }
    @Override
    public void Execute() {
        go.idle();
    }
}
