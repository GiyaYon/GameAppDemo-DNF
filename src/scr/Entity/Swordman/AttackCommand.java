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
            case 1:
                 sc.attack();
            break;
            case 2 :
                sc.attack2();
            break;
            case 3 :
                sc.attack3();
            break;
        }

    }
}
