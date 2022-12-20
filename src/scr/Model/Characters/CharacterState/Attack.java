package scr.Model.Characters.CharacterState;

import scr.Entity.Characters.Swordman.SwordsManStatesTable;
import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.DetectsColliders.AttackDetectsCollider;
import scr.Model.Characters.Forces.AttackEffect;
import scr.Model.Characters.Forces.AttackType;

class Attack extends CharacterStates implements IState
{
    int start,size;

    public Attack(CharacterBaseModel c) {
        super(c);
    }

    @Override
    public void onStart() {

        if(c.netWorking)
        {
            c.playerNetWork.sedCommand.add("Attack_"+c.property.AtkNext+","+0);
        }
        c.property.attackType = new AttackType(new Vector2D(c.property.director,0),10, AttackEffect.Light,new Force(1,1,0));
        c.cAnimator.getAnimator().resetAnim(c.cAnimator.getAnimation("attack1"));
        c.cAnimator.getAnimator().setPlayRate(c.property.attackTimes);
        c.cAnimator.getAnimator().play(c.cAnimator.getAnimation("attack1"));
        start=  (int)System.currentTimeMillis();
        size = c.cAnimator.getAnimation("attack1").igArray.get(0).size;
    }

    @Override
    public void onUpdate() {

        if(c.property.director == -1)
        {
            if((int)(System.currentTimeMillis() - start) >57*size/2 && (int)(System.currentTimeMillis() - start) < 57*(size/2+0.5)) {
                new AttackDetectsCollider(c.property.initHorizontalLine.xPos - 100, c.property.initHorizontalLine.yPos - 50, 100, 100, new Vector2D(0, 0), c.property.iCollider).attackDetect(c.property.bdcs);
            }
        }
        else if (c.property.director == 1)
        {
            if((int)(System.currentTimeMillis() - start) >57*size/2 && (int)(System.currentTimeMillis() - start) < 57*(size/2+0.5))
            {
                new AttackDetectsCollider(c.property.initHorizontalLine.xPos,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);
            }

        }


        if(c.cAnimator.getAnimator().getFinish())
        {
            if(c.property.isReadyNextAttack)
            {
                c.cAnimator.getFsm().ChangeState(SwordsManStatesTable.Attack2);
            }
            else
            {
                c.cAnimator.getFsm().ChangeState(BaseStates.Idle);
            }

        }
    }

    @Override
    public void onExit() {
        System.out.println("finish" +((int) System.currentTimeMillis() - start));
        c.property.isReadyNextAttack = false;
        c.cAnimator.getAnimator().setPlayRate(120);
    }
}