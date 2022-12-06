package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.Characters.Properties.CharacterModel;

public class InAir extends CharacterStates implements IState {
    public InAir(CharacterModel c) {
        super(c);
    }
    Force fallForce;
    float dt;
    @Override
    public void onStart() {

        c.property.flyView = new Vector2D(0,0);
        fallForce = new Force(12,1,0);
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onUpdate() {
        if(c.property.horizontal.y < c.property.initHorizontalLine.yPos)
        {
            c.property.flyView.y = fallForce.resultVy(((int)System.currentTimeMillis() -dt)/1000);
            c.property.horizontal.y += c.property.flyView.y;
        }
        else {
            if(c.property.fallTimes < c.property.defaultFallTime)
            {
                c.property.fallTimes++;
                c.property.isRebound = true;
                c.getFsm().ChangeState(BaseStates.Throw);
            }else
            {
                c.property.isRebound = false;
                c.property.fallTimes = 1;
                c.property.throwTimes = 1;
                c.getFsm().ChangeState(BaseStates.Idle);
            }

        }
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onExit() {

    }
}
