package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterEvents.HitEvent;
import scr.Model.Characters.CharacterEvents.HitListener;
import scr.Model.Characters.Forces.AttackType;

public class Injure extends CharacterStates implements IState, HitListener
{

    public Injure(CharacterBaseModel c) {
        super(c);
    }

    int injureInterval = 250;
    float dt;
    Force fallForce;
    boolean haveRepel;
    AttackType attackType;
    @Override
    public void onStart() {
        c.cAnimator.getAnimator().resetAnim(c.cAnimator.getAnimation("injure"));
        c.cAnimator.getAnimator().play(c.cAnimator.getAnimation("injure"));
        dt = (int)System.currentTimeMillis();
        c.property.flyView = new Vector2D(0,0);
    }

    @Override
    public void onUpdate() {

        if(haveRepel)
        {
            fallForce = attackType.force;
            c.property.flyView.x = attackType.attackVector.x * fallForce.resultVy(((int)System.currentTimeMillis() -dt)/1000);
            c.transform.xPos += c.property.flyView.x;
        }
        if((int)System.currentTimeMillis() - dt > injureInterval)
        {
            c.cAnimator.getFsm().ChangeState(BaseStates.Idle);
        }

    }

    @Override
    public void onExit() {
        dt = 0;
        haveRepel = false;
    }

    @Override
    public void GameEventInvoke(HitEvent event) {
        haveRepel = true;
        attackType = event.getPlayValue().property.attackType;
    }
}
