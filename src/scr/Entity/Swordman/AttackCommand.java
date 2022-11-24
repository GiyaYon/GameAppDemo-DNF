package scr.Entity.Swordman;

import scr.Controller.Commander.ICommand;

public class AttackCommand implements ICommand {

    SwordsmanCommand sc;
    int atk;
    public AttackCommand(SwordsmanCommand sc,int atk)
    {
        this.sc = sc;
        this.atk = atk;
    }
    @Override
    public void Execute() {
        switch (atk) {
            case 1 -> sc.attack();
            case 2 -> sc.attack2();
            case 3 -> sc.attack3();
        }

    }
}
