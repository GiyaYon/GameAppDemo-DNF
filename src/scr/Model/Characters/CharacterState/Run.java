package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.StateMachine.IState;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.BasePlayer.CharacterBaseModel;

public class Run extends CharacterStates implements IState
{

    public Run(CharacterBaseModel c) {super(c);}

    @Override
    public void onStart() {
        c.swordsManAnimator.getAnimator().play(c.swordsManAnimator.getAnimation("run"));
        c.property.moveSpeed += 2;
    }

    @Override
    public void onUpdate() {

        if(c.property.vector2D.compare(new Vector2D(0,0)))
        {
            c.swordsManAnimator.getFsm().ChangeState(BaseStates.Idle);
        }

    }

    @Override
    public void onExit() {
        c.property.moveSpeed -= 2;

    }
}