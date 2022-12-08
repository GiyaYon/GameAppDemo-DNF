package scr.Model.Characters.CharacterState;

import scr.Entity.Characters.Swordman.SwordsManStatesTable;
import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.Forces.JumpForce;
import scr.LogicalProcessing.Position.Vector2D;

public class Jump extends CharacterStates implements IState
{

    public Jump(CharacterBaseModel c) {
        super(c);
    }
    float dt;
    JumpForce jumpForce;

    @Override
    public void onStart() {

        c.property.flyView = new Vector2D(0,0);
        c.swordsManAnimator.getAnimator().resetAnim(c.swordsManAnimator.getAnimation("jump"));
        c.swordsManAnimator.getAnimator().play(c.swordsManAnimator.getAnimation("jump"));
        dt = (int)System.currentTimeMillis();
        jumpForce = new JumpForce(15,0);
    }

    @Override
    public void onUpdate() {

        c.property.flyView.y = jumpForce.ForceResult(((int)System.currentTimeMillis() -dt)/1000).y;
        c.property.horizontal.y -= c.property.flyView.y;
        if(c.property.flyView.y <= 0)
        {
            c.swordsManAnimator.getFsm().ChangeState(SwordsManStatesTable.Fall);
        }
        dt = (int)System.currentTimeMillis();
    }
    @Override
    public void onExit() {
    }
}