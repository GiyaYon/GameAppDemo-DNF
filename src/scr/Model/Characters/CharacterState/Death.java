package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.BasePlayer.CharacterBaseModel;

public class Death extends CharacterStates implements IState
{

    public Death(CharacterBaseModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.cAnimator.getAnimator().play(c.cAnimator.getAnimation("death"));
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onExit() {

    }
}