package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.StateMachine.IState;
import scr.LogicalProcessing.StateMachine.States;
import scr.Model.Characters.Position.Vector2D;
import scr.Model.Characters.Properties.CharacterModel;

public class Fall extends CharacterStates implements IState
{
    Force fallForce;
    float dt;
    public Fall(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.property.flyView = new Vector2D(0,0);
        fallForce = new Force(17,-1,0);
        c.getAnimator().resetAnim(c.getAnimation("fall"));
        c.getAnimator().play(c.getAnimation("fall"));
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onUpdate() {


        if(c.property.horizontal.y < c.property.initHorizontalLine.yPos)
        {
            c.property.flyView.y = fallForce.resultVy(((int)System.currentTimeMillis() -dt)/1000);
            System.out.println(c.property.flyView.y);
            c.property.horizontal.y += c.property.flyView.y;
        }
        else {
            c.getAnimator().resetAnim(c.getAnimation("fallStand"));
            c.getAnimator().play(c.getAnimation("fallStand"));
            c.getFsm().ChangeState(States.Idle);
        }
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onExit() {

    }
}