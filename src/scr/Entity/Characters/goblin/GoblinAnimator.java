package scr.Entity.Characters.goblin;

import scr.Entity.Characters.Swordman.SwordsManStatesTable;
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

public class GoblinAnimator extends CharacterAnimator {

    public GoblinAnimator(CharacterBaseModel cb) {
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
        animResources.res.put("body",new ResSprites("goblin\\body"));
        animResources.res.put("back",new ResSprites("goblin\\back"));
        animResources.res.put("front",new ResSprites("goblin\\front"));
        animResources.res.put("pants",new ResSprites("goblin\\pants"));
        animResources.res.put("weapon",new ResSprites("goblin\\weapon"));
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
        assembleToAnimations("idle",0,1,true);
        assembleToAnimations("walk",11,15,true);
        assembleToAnimations("attack1",1,4,false);
        assembleToAnimations("injure",5,6,false);
        assembleToAnimations("inair",7,8,false);
        assembleToAnimations("death",8,9,false);
    }

    @Override
    protected void setStates() {

        fsm = new FSM(cb);
        //添加列表
        statesList.put(BaseStates.Idle,new Idle(cb));
        statesList.put(BaseStates.Walk,new Walk(cb));
        statesList.put(SwordsManStatesTable.Attack,new scr.Entity.Characters.goblin.Attack(cb));
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
        if(cb.property.states.equals(SwordsManStatesTable.Jump) || cb.property.states.equals(SwordsManStatesTable.Fall) || cb.property.states.equals(BaseStates.InAir) || cb.property.states.equals(BaseStates.Throw))
        {
            Transform transform1 = new Transform(transform.xPos, cb.property.horizontal.y);
            transform1.flipX = transform.flipX;
            animator.Flash(g,jPanel,transform1,200,200);

            //animator.Flash(g,jPanel, transform);
        }
        else
        {
            animator.Flash(g,jPanel, transform,200,200);
        }

    }

    @Override
    public void update() {
        fsm.UpdateFSM();
    }
    private Animation assembleReady(String name, int first, int last, boolean ir)
    {
        //提取资源
        ResSprites res = animResources.res.get(name);
        //临时缓冲区
        ImageMerge im = new ImageMerge(name,res.getImageArray(first,last),res.getPositionsArray(first,last));
        //某个部位的动画
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

    public String[] list = {"body","back","front","pants","weapon"
    };

}
