package scr.Entity.Swordman;

import scr.Controller.StateMachine.IState;
import scr.Controller.StateMachine.States;
import scr.Model.Characters.CharacterModel;
import scr.Model.Characters.JumpForce;
import scr.Model.Characters.Vector2D;

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
        c.property.MoveSpeed += 2;
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
        c.property.MoveSpeed -= 2;

    }
}

class Jump extends SwordsManStates implements IState
{
    int dt;
    JumpForce jumpForce;
    public Jump(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.getAnimator().resetAnim(c.getAnimation("jump"));
        c.getAnimator().play(c.getAnimation("jump"));
        dt = (int)System.currentTimeMillis();
        jumpForce = new JumpForce(5,dt);
    }

    @Override
    public void onUpdate() {


        System.out.print("时间已经过了"+ ((int)System.currentTimeMillis() - dt) +"毫秒\n");
        //c.property.player.transform =  jumpForce.ForceResult(((int)System.currentTimeMillis() - dt)/1000);

        if(c.getAnimator().getFinish())
        {
            c.getFsm().ChangeState(States.Fall);
        }
        //System.out.print("时间已经过了"+ ((int)System.currentTimeMillis() - dt)/1000 +"秒\n");
        dt = (int)System.currentTimeMillis();
    }

    @Override
    public void onExit() {

    }
}
class Fall extends SwordsManStates implements IState
{

    public Fall(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {
        c.getAnimator().resetAnim(c.getAnimation("fall"));
        c.getAnimator().play(c.getAnimation("fall"));
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