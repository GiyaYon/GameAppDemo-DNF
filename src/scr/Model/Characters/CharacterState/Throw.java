package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.Forces.JumpForce;

public class Throw extends CharacterStates implements IState {
    public Throw(CharacterBaseModel c) {
        super(c);
    }
    float dt;
    JumpForce jumpForce;
    @Override
    public void onStart() {
        c.swordsManAnimator.getAnimator().resetAnim(c.swordsManAnimator.getAnimation("inair"));
        c.swordsManAnimator.getAnimator().play(c.swordsManAnimator.getAnimation("inair"));
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
        if(c.property.flyView.y <= 0)
        {
            c.swordsManAnimator.getFsm().ChangeState(BaseStates.InAir);
        }
        dt = (int)System.currentTimeMillis();

    }

    @Override
    public void onExit() {

    }
}
