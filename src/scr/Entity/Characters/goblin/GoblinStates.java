package scr.Entity.Characters.goblin;

import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.CharacterState.CharacterStates;
import scr.Model.Characters.DetectsColliders.AttackDetectsCollider;


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
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos-100,c.property.initHorizontalLine.yPos-25,50,50,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);
        } else if (c.property.director == 1) {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos,c.property.initHorizontalLine.yPos-25,50,50,new Vector2D(0,0),c.property.iCollider).attackDetect(c.property.bdcs);
        }


        if(c.swordsManAnimator.getAnimator().getFinish())
        {
            c.swordsManAnimator.getFsm().ChangeState(BaseStates.Idle);
        }
    }

    @Override
    public void onExit() {
        c.swordsManAnimator.getAnimator().setPlayRate(120);
    }
}
