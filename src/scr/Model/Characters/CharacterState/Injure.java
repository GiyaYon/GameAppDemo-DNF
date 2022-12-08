package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.BasePlayer.CharacterBaseModel;

public class Injure extends CharacterStates implements IState
{

    public Injure(CharacterBaseModel c) {
        super(c);
    }

    int injureInterval = 750;
    int dt;
    @Override
    public void onStart() {
        c.cAnimator.getAnimator().resetAnim(c.cAnimator.getAnimation("injure"));
        c.cAnimator.getAnimator().play(c.cAnimator.getAnimation("injure"));
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onUpdate() {

        if((int)System.currentTimeMillis() - dt > injureInterval)
        {
            c.cAnimator.getFsm().ChangeState(BaseStates.Idle);
        }

    }

    @Override
    public void onExit() {
        dt = 0;
    }
}
