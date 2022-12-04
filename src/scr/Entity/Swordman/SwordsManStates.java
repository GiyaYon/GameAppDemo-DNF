package scr.Entity.Swordman;

import scr.Controller.Physics.Force;
import scr.Controller.StateMachine.IState;
import scr.Controller.StateMachine.States;
import scr.Model.Characters.*;

public class SwordsManStates {
    protected CharacterModel c;
    public SwordsManStates(CharacterModel c)
    {
        this.c = c;
    }
}

class Idle extends SwordsManStates implements IState
{

    public Idle(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.getAnimator().play(c.getAnimation("idle"));

    }

    @Override
    public void onUpdate() {

       if(!c.property.vector2D.compare(new Vector2D(0,0)))
        {
            c.getFsm().ChangeState(States.Walk);
        }
        if(!c.property.vector2D.compare(new Vector2D(0,0)) && c.property.states.equals(States.Run))
        {
            c.getFsm().ChangeState(States.Run);
        }
    }

    @Override
    public void onExit() {

    }
}

class Walk extends SwordsManStates implements IState
{

    public Walk(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.getAnimator().play(c.getAnimation("walk"));
    }

    @Override
    public void onUpdate() {

        if(c.property.vector2D.compare(new Vector2D(0,0)))
        {
            c.getFsm().ChangeState(States.Idle);
        }

    }

    @Override
    public void onExit() {

    }
}

class Run extends SwordsManStates implements IState
{

    public Run(CharacterModel c) {super(c);}

    @Override
    public void onStart() {
        c.getAnimator().play(c.getAnimation("run"));
        c.property.moveSpeed += 2;
    }

    @Override
    public void onUpdate() {

        if(c.property.vector2D.compare(new Vector2D(0,0)))
        {
            c.getFsm().ChangeState(States.Idle);
        }

    }

    @Override
    public void onExit() {
        c.property.moveSpeed -= 2;

    }
}

class Jump extends SwordsManStates implements IState
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
        jumpForce = new JumpForce(10,0);
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

class  Fall extends SwordsManStates implements IState
{
    Force fallForce;
    float dt;
    public Fall(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.property.flyView = new Vector2D(0,0);
        fallForce = new Force(14,-1,0);
        c.getAnimator().resetAnim(c.getAnimation("fall"));
        c.getAnimator().play(c.getAnimation("fall"));
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
            c.getAnimator().resetAnim(c.getAnimation("fallStand"));
            c.getAnimator().play(c.getAnimation("fallStand"));
            c.getFsm().ChangeState(States.Idle);
        }
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onExit() {

    }
}

class Attack extends SwordsManStates implements IState
{
    public Attack(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {

        c.getAnimator().resetAnim(c.getAnimation("attack1"));
        c.getAnimator().setPlayRate(c.property.attackTimes);
        c.getAnimator().play(c.getAnimation("attack1"));

    }

    @Override
    public void onUpdate() {
        if(c.property.director == -1)
        {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos-50,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0)).attackDetect(c.property.bdcs);
        } else if (c.property.director == 1) {
            new AttackDetectsCollider(c.property.initHorizontalLine.xPos-50+100,c.property.initHorizontalLine.yPos-50,100,100,new Vector2D(0,0)).attackDetect(c.property.bdcs);
        }


        if(c.getAnimator().getFinish())
        {
            c.getFsm().ChangeState(States.Idle);
        }
    }

    @Override
    public void onExit() {

        c.getAnimator().setPlayRate(120);
    }
}

class Attack2 extends SwordsManStates implements IState
{
    public Attack2(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {

        c.getAnimator().resetAnim(c.getAnimation("attack2"));
        c.getAnimator().setPlayRate(c.property.attackTimes);
        c.getAnimator().play(c.getAnimation("attack2"));
    }

    @Override
    public void onUpdate() {

        if(c.getAnimator().getFinish())
        {
            c.getFsm().ChangeState(States.Idle);
        }
    }

    @Override
    public void onExit() {

        c.getAnimator().setPlayRate(120);
    }
}

class Attack3 extends SwordsManStates implements IState
{
    public Attack3(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {

        c.getAnimator().resetAnim(c.getAnimation("attack3"));
        c.getAnimator().setPlayRate(c.property.attackTimes);
        c.getAnimator().play(c.getAnimation("attack3"));
    }

    @Override
    public void onUpdate() {

        if(c.getAnimator().getFinish())
        {
            c.getFsm().ChangeState(States.Idle);
        }
    }

    @Override
    public void onExit() {

        c.getAnimator().setPlayRate(120);
    }
}