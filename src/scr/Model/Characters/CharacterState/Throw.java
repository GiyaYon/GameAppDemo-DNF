package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterEvents.HitEvent;
import scr.Model.Characters.CharacterEvents.HitListener;
import scr.Model.Characters.Forces.AttackType;
import scr.Model.Characters.Forces.JumpForce;

public class Throw extends CharacterStates implements IState , HitListener {
    public Throw(CharacterBaseModel c) {
        super(c);
    }
    float dt;
    JumpForce jumpForce;
    boolean haveRepel;
    AttackType attackType;
    Force fallForce;
    @Override
    public void onStart() {
        c.cAnimator.getAnimator().resetAnim(c.cAnimator.getAnimation("inair"));
        c.cAnimator.getAnimator().play(c.cAnimator.getAnimation("inair"));
        c.property.flyView = new Vector2D(0,0);
        dt = (int)System.currentTimeMillis();

        if(c.property.throwTimes>2)
        {
            //如果是在空中被击飞，进来

            if(c.property.isRebound)
            {
                //被连续击飞后，作为反弹进来
                jumpForce = new JumpForce(17/c.property.fallTimes,0);
            }else
            {
                //还是在空中
                jumpForce = new JumpForce(17/c.property.throwTimes,0);
            }
        }else
        {
            //如果是在空中没有被击飞，这是作为反弹进来
            jumpForce = new JumpForce(17/c.property.fallTimes,0);
        }
        c.property.throwTimes++;
    }

    @Override
    public void onUpdate() {
        c.property.flyView.y = jumpForce.ForceResult(((int)System.currentTimeMillis() -dt)/1000).y;
        c.property.horizontal.y -= c.property.flyView.y;
        if(haveRepel)
        {
            fallForce = attackType.force;
            c.property.flyView.x = attackType.attackVector.x * fallForce.resultVy(((int)System.currentTimeMillis() -dt)/1000);
            c.transform.xPos += c.property.flyView.x;
        }
        if(c.property.flyView.y <= 0)
        {
            c.cAnimator.getFsm().ChangeState(BaseStates.InAir);
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
