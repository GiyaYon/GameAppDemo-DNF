package scr.LogicalProcessing.Commander.GameObjectCommands;

import scr.LogicalProcessing.Commander.ICommand;
import scr.LogicalProcessing.Commander.GameObject;

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
