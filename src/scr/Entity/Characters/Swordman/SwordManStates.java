package scr.Entity.Characters.Swordman;

import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.DetectsColliders.AttackDetectsCollider;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Characters.Properties.CharacterModel;
import scr.Model.Characters.CharacterState.CharacterStates;




class Attack extends CharacterStates implements IState
{
    public Attack(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {

        c.getAnimator().resetAnim(c.getAnimation("attack1"));
        c.getAnimator().setPlayRate(c.property.attackTimes);
        c.getAnimator().play(c.getAnimation("attack1"));

    }

    @Override
    public void onUpdate() {
        if(c.property.director == -1)
        {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos-100,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0)).attackDetect(c.property.bdcs);
        } else if (c.property.director == 1) {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0)).attackDetect(c.property.bdcs);
        }


        if(c.getAnimator().getFinish())
        {
            if(c.property.isReadyNextAttack)
            {
                c.getFsm().ChangeState(SwordsManStatesTable.Attack2);
            }
            else
            {
                c.getFsm().ChangeState(BaseStates.Idle);
            }

        }
    }

    @Override
    public void onExit() {
        c.property.isReadyNextAttack = false;
        c.getAnimator().setPlayRate(120);
    }
}

class Attack2 extends CharacterStates implements IState
{
    public Attack2(CharacterModel c) {
        super(c);

    }

    @Override
    public void onStart() {
        c.getAnimator().resetAnim(c.getAnimation("attack2"));
        c.getAnimator().setPlayRate(c.property.attackTimes);
        c.getAnimator().play(c.getAnimation("attack2"));
    }

    @Override
    public void onUpdate() {

        if(c.property.director == -1)
        {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos-100,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0)).attackDetect(c.property.bdcs);
        } else if (c.property.director == 1) {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0)).attackDetect(c.property.bdcs);
        }
        if(c.getAnimator().getFinish())
        {
            if(c.property.isReadyNextAttack)
            {
                c.getFsm().ChangeState(SwordsManStatesTable.Attack3);
            }
            else
            {
                c.getFsm().ChangeState(BaseStates.Idle);
            }
        }
    }

    @Override
    public void onExit() {
        c.property.isReadyNextAttack = false;
        c.getAnimator().setPlayRate(120);
    }
}

class Attack3 extends CharacterStates implements IState
{
    public Attack3(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {

        c.getAnimator().resetAnim(c.getAnimation("attack3"));
        c.getAnimator().setPlayRate(c.property.attackTimes);
        c.getAnimator().play(c.getAnimation("attack3"));
    }

    @Override
    public void onUpdate() {

        if(c.property.director == -1)
        {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos-100,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0)).attackDetect(c.property.bdcs);
        } else if (c.property.director == 1) {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0)).attackDetect(c.property.bdcs);
        }
        if(c.getAnimator().getFinish())
        {
            if(c.property.isReadyNextAttack)
            {
                c.getFsm().ChangeState(SwordsManStatesTable.Attack);
            }
            else
            {
                c.getFsm().ChangeState(BaseStates.Idle);
            }
        }
    }

    @Override
    public void onExit() {
        c.property.isReadyNextAttack = false;
        c.getAnimator().setPlayRate(120);
    }
}
