package scr.Model.Characters.Commands;

import scr.Model.Characters.Forces.AttackType;

public class DeathCommand implements ICommand {

    GameObjectAction go;
    public DeathCommand(GameObjectAction go)
    {
        this.go = go;
    }
    @Override
    public void Execute() {
        go.death();
    }
}
