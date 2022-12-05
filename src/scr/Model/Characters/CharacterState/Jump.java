package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.StateMachine.IState;
import scr.LogicalProcessing.StateMachine.States;
import scr.Model.Characters.Forces.JumpForce;
import scr.Model.Characters.Position.Vector2D;
import scr.Model.Characters.Properties.CharacterModel;

public class Jump extends CharacterStates implements IState
{

    public Jump(CharacterModel c) {
        super(c);
    }
    float dt;
    JumpForce jumpForce;

    @Override
    public void onStart() {

        c.property.flyView = new Vector2D(0,0);
        c.getAnimator().resetAnim(c.getAnimation("jump"));
        c.getAnimator().play(c.getAnimation("jump"));
        dt = (int)System.currentTimeMillis();
        jumpForce = new JumpForce(15,0);
    }

    @Override
    public void onUpdate() {

        c.property.flyView.y = jumpForce.ForceResult(((int)System.currentTimeMillis() -dt)/1000).y;
        c.property.horizontal.y -= c.property.flyView.y;
        if(c.property.flyView.y <= 0)
        {
            c.getFsm().ChangeState(States.Fall);
        }
        dt = (int)System.currentTimeMillis();
    }
    @Override
    public void onExit() {
    }
}