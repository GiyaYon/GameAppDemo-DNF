package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.StateMachine.IState;
import scr.LogicalProcessing.StateMachine.States;
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
            System.out.println(c.property.flyView.y);
            c.property.horizontal.y += c.property.flyView.y;
        }
        else {
            if(c.property.fallTimes < c.property.defaultFallTime)
            {
                c.property.fallTimes++;
                c.getFsm().ChangeState(States.Throw);
            }else
            {
                c.property.fallTimes = 1;
                c.getFsm().ChangeState(States.Idle);
            }

        }
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onExit() {

    }
}
