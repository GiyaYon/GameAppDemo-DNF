package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.StateMachine.IState;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Characters.Properties.CharacterModel;

public class Run extends CharacterStates implements IState
{

    public Run(CharacterModel c) {super(c);}

    @Override
    public void onStart() {
        c.getAnimator().play(c.getAnimation("run"));
        c.property.moveSpeed += 2;
    }

    @Override
    public void onUpdate() {

        if(c.property.vector2D.compare(new Vector2D(0,0)))
        {
            c.getFsm().ChangeState(BaseStates.Idle);
        }

    }

    @Override
    public void onExit() {
        c.property.moveSpeed -= 2;

    }
}