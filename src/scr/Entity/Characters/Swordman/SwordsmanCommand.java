package scr.Entity.Characters.Swordman;

import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.Commands.GameObject;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;

public class SwordsmanCommand implements GameObject {

    public CharacterBaseModel cb;
    public SwordsmanCommand(CharacterBaseModel cb)
    {
        this.cb = cb;
    }
    @Override
    public void move(Vector2D vector2D, Transform transform) {

        cb.property.states = BaseStates.Walk;
        transform.xPos += vector2D.x * cb.property.moveSpeed;
        transform.yPos += vector2D.y * cb.property.moveSpeed;
        if(vector2D.x != 0)
        {
            transform.flipX = vector2D.x;
        }

        cb.property.vector2D = vector2D;

    }

    @Override
    public void idle() {
        cb.property.states = BaseStates.Idle;
        cb.property.vector2D = new Vector2D(0,0);
    }

    public void run(Vector2D vector2D, Transform transform)
    {
        cb.property.states = BaseStates.Run;
        cb.swordsManAnimator.getFsm().ChangeState(cb.property.states);
        transform.xPos += vector2D.x * cb.property.moveSpeed;
        transform.yPos += vector2D.y * cb.property.moveSpeed;
        if(vector2D.x != 0)
        {
            transform.flipX = vector2D.x;
        }

        cb.property.vector2D = vector2D;
    }

    public void attack()
    {
        cb.property.states = SwordsManStatesTable.Attack;
        cb.swordsManAnimator.getFsm().ChangeState(cb.property.states);
    }
    public void attack2()
    {
        cb.property.states = SwordsManStatesTable.Attack2;
        cb.swordsManAnimator.getFsm().ChangeState(cb.property.states);
    }
    public void attack3()
    {
        cb.property.states = SwordsManStatesTable.Attack3;
        cb.swordsManAnimator.getFsm().ChangeState(cb.property.states);
    }

    public void jump(Transform transform)
    {
        cb.property.states = SwordsManStatesTable.Jump;
        cb.swordsManAnimator.getFsm().ChangeState(cb.property.states);
    }

}
