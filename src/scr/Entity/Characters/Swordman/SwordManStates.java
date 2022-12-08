package scr.Entity.Characters.Swordman;

import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.DetectsColliders.AttackDetectsCollider;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Characters.CharacterState.CharacterStates;




class Attack extends CharacterStates implements IState
{
    public Attack(CharacterBaseModel c) {
        super(c);
    }

    @Override
    public void onStart() {

        c.swordsManAnimator.getAnimator().resetAnim(c.swordsManAnimator.getAnimation("attack1"));
        c.swordsManAnimator.getAnimator().setPlayRate(c.property.attackTimes);
        c.swordsManAnimator.getAnimator().play(c.swordsManAnimator.getAnimation("attack1"));

    }

    @Override
    public void onUpdate() {
        if(c.property.director == -1)
        {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos-100,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);
        } else if (c.property.director == 1) {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);
        }


        if(c.swordsManAnimator.getAnimator().getFinish())
        {
            if(c.property.isReadyNextAttack)
            {
                c.swordsManAnimator.getFsm().ChangeState(SwordsManStatesTable.Attack2);
            }
            else
            {
                c.swordsManAnimator.getFsm().ChangeState(BaseStates.Idle);
            }

        }
    }

    @Override
    public void onExit() {
        c.property.isReadyNextAttack = false;
        c.swordsManAnimator.getAnimator().setPlayRate(120);
    }
}

class Attack2 extends CharacterStates implements IState
{
    public Attack2(CharacterBaseModel c) {
        super(c);

    }

    @Override
    public void onStart() {
        c.swordsManAnimator.getAnimator().resetAnim(c.swordsManAnimator.getAnimation("attack2"));
        c.swordsManAnimator.getAnimator().setPlayRate(c.property.attackTimes);
        c.swordsManAnimator.getAnimator().play(c.swordsManAnimator.getAnimation("attack2"));
    }

    @Override
    public void onUpdate() {

        if(c.property.director == -1)
        {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos-100,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);
        } else if (c.property.director == 1) {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);
        }
        if(c.swordsManAnimator.getAnimator().getFinish())
        {
            if(c.property.isReadyNextAttack)
            {
                c.swordsManAnimator.getFsm().ChangeState(SwordsManStatesTable.Attack3);
            }
            else
            {
                c.swordsManAnimator.getFsm().ChangeState(BaseStates.Idle);
            }
        }
    }

    @Override
    public void onExit() {
        c.property.isReadyNextAttack = false;
        c.swordsManAnimator.getAnimator().setPlayRate(120);
    }
}

class Attack3 extends CharacterStates implements IState
{
    public Attack3(CharacterBaseModel c) {
        super(c);
    }

    @Override
    public void onStart() {

        c.swordsManAnimator.getAnimator().resetAnim(c.swordsManAnimator.getAnimation("attack3"));
        c.swordsManAnimator.getAnimator().setPlayRate(c.property.attackTimes);
        c.swordsManAnimator.getAnimator().play(c.swordsManAnimator.getAnimation("attack3"));
    }

    @Override
    public void onUpdate() {

        if(c.property.director == -1)
        {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos-100,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);
        } else if (c.property.director == 1) {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);
        }
        if(c.swordsManAnimator.getAnimator().getFinish())
        {
            if(c.property.isReadyNextAttack)
            {
                c.swordsManAnimator.getFsm().ChangeState(SwordsManStatesTable.Attack);
            }
            else
            {
                c.swordsManAnimator.getFsm().ChangeState(BaseStates.Idle);
            }
        }
    }

    @Override
    public void onExit() {
        c.property.isReadyNextAttack = false;
        c.swordsManAnimator.getAnimator().setPlayRate(120);
    }
}
