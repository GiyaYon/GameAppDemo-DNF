package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.Characters.Forces.JumpForce;
import scr.Model.Characters.Properties.CharacterModel;

public class Throw extends CharacterStates implements IState {
    public Throw(CharacterModel c) {
        super(c);
    }
    float dt;
    JumpForce jumpForce;
    @Override
    public void onStart() {
        c.getAnimator().resetAnim(c.getAnimation("inair"));
        c.getAnimator().play(c.getAnimation("inair"));
        c.property.flyView = new Vector2D(0,0);
        dt = (int)System.currentTimeMillis();
        jumpForce = new JumpForce(17/c.property.fallTimes,0);

    }

    @Override
    public void onUpdate() {
        c.property.flyView.y = jumpForce.ForceResult(((int)System.currentTimeMillis() -dt)/1000).y;
        c.property.horizontal.y -= c.property.flyView.y;
        if(c.property.flyView.y <= 0)
        {
            c.getFsm().ChangeState(BaseStates.InAir);
        }
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onExit() {

    }
}
