package scr.Entity.Swordman;



import scr.Controller.StateMachine.FSM;
import scr.Controller.StateMachine.States;
import scr.Model.Characters.CharacterModel;
import scr.Model.Characters.Transform;
import scr.Viewer.Anim.Animation;
import scr.Viewer.Anim.AnimationMergeGroup;
import scr.Viewer.Anim.Animator;
import scr.Viewer.LoadImage.ImageMerge;
import scr.Viewer.LoadImage.ResSprites;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SwordsMan extends CharacterModel
{

    public SwordsmanCommand sc;
    public SwordsMan() throws IOException {
        setAnimResources();
        setAnimations();
        setAnimator();
        setStates();


        sc = new SwordsmanCommand(this);
    }

    @Override
    protected void setAnimResources() throws IOException {
        animResources.res.put("body",new ResSprites("swordsman\\body"));
        animResources.res.put("coat",new ResSprites("swordsman\\coat"));
        animResources.res.put("sworda",new ResSprites("swordsman\\weapona"));
        animResources.res.put("swordb",new ResSprites("swordsman\\weaponb"));
        animResources.res.put("pantsa",new ResSprites("swordsman\\pantsa"));
        animResources.res.put("pantsb",new ResSprites("swordsman\\pantsb"));
        animResources.res.put("shoesa",new ResSprites("swordsman\\shoesa"));
        animResources.res.put("shoesb",new ResSprites("swordsman\\shoesb"));
        animResources.res.put("hair",new ResSprites("swordsman\\hair"));
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
        assembleToAnimations("idle",90,95,true);
        assembleToAnimations("walk",180,187,true);
        assembleToAnimations("run",105,112,true);
        assembleToAnimations("attack1",0,9,false);
        assembleToAnimations("attack2",10,20,false);
        assembleToAnimations("attack3",33,41,false);
        assembleToAnimations("jump",125,128,false);
        assembleToAnimations("fall",129,132,false);

    }

    @Override
    protected void setStates() {

        fsm = new FSM(this);
        //添加列表
        statesList.put(States.Idle,new Idle(this));
        statesList.put(States.Walk,new Walk(this));
        statesList.put(States.Run,new Run(this));
        statesList.put(States.Attack,new Attack(this));
        statesList.put(States.Attack2,new Attack2(this));
        statesList.put(States.Attack3,new Attack3(this));
        statesList.put(States.Jump,new Jump(this));
        statesList.put(States.Fall,new Fall(this));

        //初始化
        fsm.currentState = getState(States.Idle);
        fsm.currentState.onStart();
        property.states = States.Idle;
    }


    @Override
    public void render(Graphics g, JPanel jPanel, Transform transform) {

        animator.Flash(g,jPanel,transform);

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

    public String[] list = {"body","coat","hair","pantsa","pantsb","shoesa","shoesb","sworda","swordb"
    };


}
