package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.StateMachine.IState;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.BasePlayer.CharacterBaseModel;

public class Fall extends CharacterStates implements IState
{
    Force fallForce;
    float dt;
    public Fall(CharacterBaseModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.property.flyView = new Vector2D(0,0);
        fallForce = new Force(11,1,0);
        c.swordsManAnimator.getAnimator().resetAnim(c.swordsManAnimator.getAnimation("fall"));
        c.swordsManAnimator.getAnimator().play(c.swordsManAnimator.getAnimation("fall"));
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
            c.swordsManAnimator.getAnimator().resetAnim(c.swordsManAnimator.getAnimation("fallStand"));
            c.swordsManAnimator.getAnimator().play(c.swordsManAnimator.getAnimation("fallStand"));
            c.swordsManAnimator.getFsm().ChangeState(BaseStates.Idle);
        }
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onExit() {

    }
}