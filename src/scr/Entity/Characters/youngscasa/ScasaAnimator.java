package scr.Entity.Characters.youngscasa;


import scr.Entity.Characters.giselle.GiselleStatesTable;
import scr.IOProcessing.Anim.Animation;
import scr.IOProcessing.Anim.AnimationMergeGroup;
import scr.IOProcessing.Anim.Animator;
import scr.IOProcessing.LoadImage.ImageMerge;
import scr.IOProcessing.LoadImage.ResSprites;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.StateMachine.FSM;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterState.*;
import scr.Model.Characters.Properties.CharacterAnimator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class ScasaAnimator extends CharacterAnimator
{
    public ScasaAnimator(CharacterBaseModel cb) {
        this.cb = cb;

    }

    @Override
    public void init() throws IOException {
        setAnimResources();
        setAnimations();
        setAnimator();
        setStates();
    }

    @Override
    protected void setAnimResources() throws IOException {
        animResources.res.put("body",new ResSprites("youngscasa\\body"));
        animResources.res.put("angry",new ResSprites("youngscasa\\angry"));
    }

    @Override
    protected void setAnimator() {

        //初始化
        animator = new Animator();
        animator.currentAnim = animations.get("idle");
    }

    @Override
    protected void setAnimations() {

        //先装配
        assembleToAnimations("idle",0,5,true);
        assembleToAnimations("walk",17,23,true);
        assembleToAnimations("attack1",5,10,false);
        assembleToAnimations("injure",0,1,false);
        assembleToAnimations("death",32,33,false);
        //先装配

        assembleToAnimations("attack2",0,1,false);
        assembleToAnimations("injure",0,1,false);
        assembleToAnimations("inair",0,1,false);

    }

    @Override
    protected void setStates() {


        fsm = new FSM(cb);
        //添加列表
        statesList.put(BaseStates.Idle,new Idle(cb));
        statesList.put(BaseStates.Walk,new Walk(cb));
        statesList.put(GiselleStatesTable.Attack,new scr.Entity.Characters.youngscasa.Attack(cb));
        statesList.put(GiselleStatesTable.Attack2,new scr.Entity.Characters.youngscasa.Attack2(cb));
        statesList.put(BaseStates.Injure,new Injure(cb));
        statesList.put(BaseStates.InAir,new InAir(cb));
        statesList.put(BaseStates.Throw,new Throw(cb));
        statesList.put(BaseStates.Death,new Death(cb));

        //初始化
        fsm.currentState = getState(BaseStates.Idle);
        fsm.currentState.onStart();
        cb.property.states = BaseStates.Idle;
    }


    @Override
    public void render(Graphics g, JPanel jPanel, Transform transform) {

        if(cb.property.states.equals(ScasaStatesTable.Jump) || cb.property.states.equals(ScasaStatesTable.Fall) || cb.property.states.equals(BaseStates.InAir) || cb.property.states.equals(BaseStates.Throw))
        {
            Transform transform1 = new Transform(transform.xPos, cb.property.horizontal.y);
            transform1.flipX = transform.flipX;
            animator.Flash(g,jPanel,transform1,800,800);

            //animator.Flash(g,jPanel, transform);
        }
        else
        {
            animator.Flash(g,jPanel, transform,800,800);
        }

    }

    @Override
    public void update() {

        fsm.UpdateFSM();
    }

    private Animation assembleReady(String name, int first, int last, boolean ir)
    {
        ResSprites res = animResources.res.get(name);
        ImageMerge im = new ImageMerge();
        try {

            //临时缓冲区
            im = new ImageMerge(name,res.getImageArray(first,last),res.getPositionsArray(first,last));
            //某个部位的动画
        }catch (NullPointerException e)
        {

        }
        //提取资源
        return new Animation(im,ir);
    }

    private void assembleToAnimations(String name,int first,int last,boolean ir)
    {
        AnimationMergeGroup ig = new AnimationMergeGroup();
        ig.igArray = new ArrayList<>();
        for(var v : list)
        {
            ig.igArray.add(assembleReady(v,first,last,ir));
        }
        animations.put(name,ig);
    }

    public String[] list = {"body","angry"
    };


}
