package scr.Entity.Characters.giselle;

import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.CharacterState.CharacterStates;
import scr.Model.Characters.DetectsColliders.AttackDetectsCollider;
import scr.Model.Characters.Forces.AttackEffect;
import scr.Model.Characters.Forces.AttackType;


class Attack extends CharacterStates implements IState
{
    int start,size;
    float dt;
    Force fallForce;
    public Attack(CharacterBaseModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.property.attackTimes = 150;
        c.property.attackType = new AttackType(new Vector2D(c.property.director,0),10, AttackEffect.Heavy,new Force( 15,1,0));
        c.cAnimator.getAnimator().resetAnim(c.cAnimator.getAnimation("attack1"));
        c.cAnimator.getAnimator().setPlayRate(c.property.attackTimes);
        c.cAnimator.getAnimator().play(c.cAnimator.getAnimation("attack1"));
        start=  (int)System.currentTimeMillis();
        size = c.cAnimator.getAnimation("attack1").igArray.get(0).size;
        dt = (int)System.currentTimeMillis();
        c.property.flyView = new Vector2D(0,0);
    }

    @Override
    public void onUpdate() {

        fallForce = c.property.attackType.force;
        c.property.flyView.x = c.property.attackType.attackVector.x * fallForce.resultVy(((int)System.currentTimeMillis() -dt)/1000)/4;
        c.transform.xPos += c.property.flyView.x;
        if(c.property.director == -1)
        {
            if((int)(System.currentTimeMillis() - start) >600 && (int)(System.currentTimeMillis() - start) < 700)
            {
                new AttackDetectsCollider(c.property.initHorizontalLine.xPos-100, c.property.initHorizontalLine.yPos - 50, 200, 100, new Vector2D(0, 0), c.property.iCollider).attackDetect(c.property.bdcs);
            }

        }
        else if (c.property.director == 1)
        {
            if((int)(System.currentTimeMillis() - start) >600 && (int)(System.currentTimeMillis() - start) < 700) {
                new AttackDetectsCollider(c.property.initHorizontalLine.xPos, c.property.initHorizontalLine.yPos - 50, 200, 100, new Vector2D(0, 0), c.property.iCollider).attackDetect(c.property.bdcs);
            }
        }


        if(c.cAnimator.getAnimator().getFinish())
        {
            c.cAnimator.getFsm().ChangeState(BaseStates.Idle);
        }
    }

    @Override
    public void onExit() {
        System.out.println("finish" +((int) System.currentTimeMillis() - start));
        c.property.isReadyNextAttack = false;
        c.cAnimator.getAnimator().setPlayRate(120);
    }
}
class Attack2 extends CharacterStates implements IState
{
    int start,size;
    float dt;
    Force fallForce;
    public Attack2(CharacterBaseModel c) {
        super(c);

    }

    @Override
    public void onStart() {
        c.property.attackType = new AttackType(new Vector2D(c.property.director,0),10, AttackEffect.Light,new Force(1,1,0));
        c.cAnimator.getAnimator().resetAnim(c.cAnimator.getAnimation("attack2"));
        c.cAnimator.getAnimator().setPlayRate(c.property.attackTimes);
        c.cAnimator.getAnimator().play(c.cAnimator.getAnimation("attack2"));
        start=  (int)System.currentTimeMillis();
        size = c.cAnimator.getAnimation("attack1").igArray.get(0).size;
        dt = (int)System.currentTimeMillis();
        c.property.flyView = new Vector2D(0,0);
    }

    @Override
    public void onUpdate() {

        fallForce = c.property.attackType.force;
        c.property.flyView.x = c.property.attackType.attackVector.x * fallForce.resultVy(((int)System.currentTimeMillis() -dt)/1000);
        c.transform.xPos += c.property.flyView.x;
        if(c.property.director == -1)
        {
            if((int)(System.currentTimeMillis() - start) >57*size/2 && (int)(System.currentTimeMillis() - start) < 57*(size/2+0.5)) {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos-100,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);}
        } else if (c.property.director == 1) {
            if((int)(System.currentTimeMillis() - start) >57*size/2 && (int)(System.currentTimeMillis() - start) < 57*(size/2+0.5)) {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);}
        }
        if(c.cAnimator.getAnimator().getFinish())
        {
            if(c.property.isReadyNextAttack)
            {
                c.cAnimator.getFsm().ChangeState(GiselleStatesTable.Attack3);
            }
            else
            {
                c.cAnimator.getFsm().ChangeState(BaseStates.Idle);
            }
        }
    }

    @Override
    public void onExit() {
        c.property.isReadyNextAttack = false;
        c.cAnimator.getAnimator().setPlayRate(120);
    }
}

class Attack3 extends CharacterStates implements IState
{
    public Attack3(CharacterBaseModel c) {
        super(c);
    }
    int start,size;
    float dt;
    Force fallForce;
    @Override
    public void onStart() {
        c.property.attackType = new AttackType(new Vector2D(c.property.director,0),10,AttackEffect.Heavy,new Force(1,1,0));
        c.cAnimator.getAnimator().resetAnim(c.cAnimator.getAnimation("attack3"));
        c.cAnimator.getAnimator().setPlayRate(c.property.attackTimes);
        c.cAnimator.getAnimator().play(c.cAnimator.getAnimation("attack3"));
        start=  (int)System.currentTimeMillis();
        size = c.cAnimator.getAnimation("attack1").igArray.get(0).size;
        dt = (int)System.currentTimeMillis();
        c.property.flyView = new Vector2D(0,0);
    }

    @Override
    public void onUpdate() {

        fallForce = c.property.attackType.force;
        c.property.flyView.x = c.property.attackType.attackVector.x * fallForce.resultVy(((int)System.currentTimeMillis() -dt)/1000);
        c.transform.xPos += c.property.flyView.x;
        if(c.property.director == -1)
        {
            if((int)(System.currentTimeMillis() - start) >57*size/2 && (int)(System.currentTimeMillis() - start) < 57*(size/2+0.5)) {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos-100,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);}
        } else if (c.property.director == 1) {
            if((int)(System.currentTimeMillis() - start) >57*size/2 && (int)(System.currentTimeMillis() - start) < 57*(size/2+0.5)) {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);}
        }
        if(c.cAnimator.getAnimator().getFinish())
        {
            if(c.property.isReadyNextAttack)
            {
                c.cAnimator.getFsm().ChangeState(GiselleStatesTable.Attack);
            }
            else
            {
                c.cAnimator.getFsm().ChangeState(BaseStates.Idle);
            }
        }
    }

    @Override
    public void onExit() {
        c.property.isReadyNextAttack = false;
        c.cAnimator.getAnimator().setPlayRate(120);
    }
}
