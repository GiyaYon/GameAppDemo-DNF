package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.StateMachine.IState;
import scr.LogicalProcessing.StateMachine.States;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Characters.Properties.CharacterModel;

public class Idle extends CharacterStates implements IState
{

    public Idle(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.getAnimator().play(c.getAnimation("idle"));

    }

    @Override
    public void onUpdate() {

        if(!c.property.vector2D.compare(new Vector2D(0,0)))
        {
            c.getFsm().ChangeState(States.Walk);
        }
        if(!c.property.vector2D.compare(new Vector2D(0,0)) && c.property.states.equals(States.Run))
        {
            c.getFsm().ChangeState(States.Run);
        }
    }

    @Override
    public void onExit() {

    }
}