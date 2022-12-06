package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.StateMachine.IState;
import scr.LogicalProcessing.StateMachine.States;
import scr.Model.Characters.Properties.CharacterModel;

public class Injure extends CharacterStates implements IState
{

    public Injure(CharacterModel c) {
        super(c);
    }

    int injureInterval = 750;
    int dt;
    @Override
    public void onStart() {
        c.getAnimator().resetAnim(c.getAnimation("injure"));
        c.getAnimator().play(c.getAnimation("injure"));
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onUpdate() {

        if((int)System.currentTimeMillis() - dt > injureInterval)
        {
            c.getFsm().ChangeState(States.Idle);
        }

    }

    @Override
    public void onExit() {
        dt = 0;
    }
}
