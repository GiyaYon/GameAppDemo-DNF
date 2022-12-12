package scr.Entity.Characters.Swordman;

import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.Commands.GameObjectAction;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Characters.Forces.AttackType;

public class SwordsmanCommand implements GameObjectAction {
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
    @Override
    public void injure(AttackType type) {
        cb.property.horizontal = new Vector2D(cb.transform.xPos,cb.transform.yPos);
        cb.property.initHorizontalLine = cb.transform;
        cb.cAnimator.getFsm().ChangeState(BaseStates.Injure);
    }
    @Override
    public void throwFly(AttackType type) {
        if(cb.property.states.equals(BaseStates.InAir) ||cb.property.states.equals(BaseStates.Throw))
        {
            cb.property.horizontal = new Vector2D(cb.transform.xPos,cb.property.horizontal.y);
            cb.cAnimator.getFsm().ChangeState(BaseStates.Throw);
        }
        else {
            cb.property.throwTimes = 1;
            cb.property.fallTimes = 1;
            cb.property.horizontal = new Vector2D(cb.transform.xPos,cb.transform.yPos);
            cb.property.initHorizontalLine = cb.transform;
            cb.cAnimator.getFsm().ChangeState(BaseStates.Throw);
        }
    }
    public void run(Vector2D vector2D, Transform transform)
    {
        cb.property.states = BaseStates.Run;
        cb.cAnimator.getFsm().ChangeState(cb.property.states);
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
        cb.cAnimator.getFsm().ChangeState(cb.property.states);
    }
    public void attack2()
    {
        cb.property.states = SwordsManStatesTable.Attack2;
        cb.cAnimator.getFsm().ChangeState(cb.property.states);
    }
    public void attack3()
    {
        cb.property.states = SwordsManStatesTable.Attack3;
        cb.cAnimator.getFsm().ChangeState(cb.property.states);
    }
    public void jump(Transform transform)
    {
        cb.property.states = SwordsManStatesTable.Jump;
        cb.cAnimator.getFsm().ChangeState(cb.property.states);
    }
    public void death()
    {
        cb.property.states = BaseStates.Death;
        cb.cAnimator.getFsm().ChangeState(cb.property.states);
    }

}
