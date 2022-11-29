package scr.Controller.Commander.GameObjectCommands;

import scr.Controller.Commander.ICommand;
import scr.Controller.Commander.GameObject;

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
