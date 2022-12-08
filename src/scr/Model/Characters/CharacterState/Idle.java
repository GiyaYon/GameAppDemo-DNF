package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.StateMachine.IState;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.BasePlayer.CharacterBaseModel;

public class Idle extends CharacterStates implements IState
{

    public Idle(CharacterBaseModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.cAnimator.getAnimator().play(c.cAnimator.getAnimation("idle"));

    }

    @Override
    public void onUpdate() {

        if(!c.property.vector2D.compare(new Vector2D(0,0)))
        {
            c.cAnimator.getFsm().ChangeState(BaseStates.Walk);
        }
        if(!c.property.vector2D.compare(new Vector2D(0,0)) && c.property.states.equals(BaseStates.Run))
        {
            c.cAnimator.getFsm().ChangeState(BaseStates.Run);
        }
    }

    @Override
    public void onExit() {

    }
}