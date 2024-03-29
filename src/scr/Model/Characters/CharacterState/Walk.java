package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.StateMachine.IState;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.BasePlayer.CharacterBaseModel;

public class Walk extends CharacterStates implements IState
{

    public Walk(CharacterBaseModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.cAnimator.getAnimator().play(c.cAnimator.getAnimation("walk"));
    }

    @Override
    public void onUpdate() {

        if(c.property.vector2D.compare(new Vector2D(0,0)))
        {
            c.cAnimator.getFsm().ChangeState(BaseStates.Idle);
        }

    }

    @Override
    public void onExit() {

    }
}