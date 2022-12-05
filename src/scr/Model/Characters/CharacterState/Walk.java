package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.StateMachine.IState;
import scr.LogicalProcessing.StateMachine.States;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Characters.Properties.CharacterModel;

public class Walk extends CharacterStates implements IState
{

    public Walk(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.getAnimator().play(c.getAnimation("walk"));
    }

    @Override
    public void onUpdate() {

        if(c.property.vector2D.compare(new Vector2D(0,0)))
        {
            c.getFsm().ChangeState(States.Idle);
        }

    }

    @Override
    public void onExit() {

    }
}