package scr.Entity.Swordman;

import scr.LogicalProcessing.StateMachine.States;
import scr.LogicalProcessing.Commander.GameObject;
import scr.Model.Characters.Position.Transform;
import scr.Model.Characters.Position.Vector2D;

public class SwordsmanCommand implements GameObject {

    SwordsMan sm;

    public SwordsmanCommand(SwordsMan sm)
    {
        this.sm = sm;

    }
    @Override
    public void move(Vector2D vector2D, Transform transform) {

        sm.property.states = States.Walk;
        transform.xPos += vector2D.x * sm.property.moveSpeed;
        transform.yPos += vector2D.y * sm.property.moveSpeed;
        if(vector2D.x != 0)
        {
            transform.flipX = vector2D.x;
        }

        sm.property.vector2D = vector2D;

    }

    @Override
    public void idle() {
        sm.property.states = States.Idle;
        sm.property.vector2D = new Vector2D(0,0);
    }

    public void run(Vector2D vector2D, Transform transform)
    {
        sm.property.states = States.Run;
        sm.getFsm().ChangeState(sm.property.states);
        transform.xPos += vector2D.x * sm.property.moveSpeed;
        transform.yPos += vector2D.y * sm.property.moveSpeed;
        if(vector2D.x != 0)
        {
            transform.flipX = vector2D.x;
        }

        sm.property.vector2D = vector2D;
    }

    public void attack()
    {
        sm.property.states = States.Attack;
        sm.getFsm().ChangeState(sm.property.states);
    }
    public void attack2()
    {
        sm.property.states = States.Attack2;
        sm.getFsm().ChangeState(sm.property.states);
    }
    public void attack3()
    {
        sm.property.states = States.Attack3;
        sm.getFsm().ChangeState(sm.property.states);
    }

    public void jump(Transform transform)
    {
        sm.property.states = States.Jump;
        sm.getFsm().ChangeState(sm.property.states);
    }

}
