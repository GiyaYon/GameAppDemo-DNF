package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterEvents.HitEvent;
import scr.Model.Characters.CharacterEvents.HitListener;
import scr.Model.Characters.Forces.AttackType;

public class InAir extends CharacterStates implements IState, HitListener {
    public InAir(CharacterBaseModel c) {
        super(c);
    }
    Force fallForce;
    float dt;

    boolean haveRepel;
    AttackType attackType;
    Force Force;
    @Override
    public void onStart() {

        c.property.flyView = new Vector2D(0,0);
        fallForce = new Force(12,1,0);
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onUpdate() {


        if(haveRepel)
        {
            Force = attackType.force;
            c.property.flyView.x = attackType.attackVector.x * Force.resultVy(((int)System.currentTimeMillis() -dt)/1000);
            c.transform.xPos += c.property.flyView.x;
        }
        if(c.property.horizontal.y < c.property.initHorizontalLine.yPos)
        {
            c.property.flyView.y = fallForce.resultVy(((int)System.currentTimeMillis() -dt)/1000);
            c.property.horizontal.y += c.property.flyView.y;
        }
        else {
            if(c.property.fallTimes < c.property.defaultFallTime)
            {
                c.property.fallTimes++;
                c.property.isRebound = true;
                c.cAnimator.getFsm().ChangeState(BaseStates.Throw);
            }else
            {
                c.property.isRebound = false;
                c.property.fallTimes = 1;
                c.property.throwTimes = 1;
                c.cAnimator.getFsm().ChangeState(BaseStates.Idle);
            }

        }
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onExit() {
        haveRepel = false;
    }

    @Override
    public void GameEventInvoke(HitEvent event) {
        haveRepel = true;
        attackType = event.getPlayValue().property.attackType;
    }
}
