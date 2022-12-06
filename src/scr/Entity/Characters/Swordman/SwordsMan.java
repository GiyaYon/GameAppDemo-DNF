package scr.Entity.Characters.Swordman;



import scr.LogicalProcessing.Robot.IController;
import scr.LogicalProcessing.StateMachine.FSM;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.CharacterState.*;
import scr.Model.Characters.Properties.CharacterModel;
import scr.LogicalProcessing.Position.Transform;

import scr.IOProcessing.Anim.Animation;
import scr.IOProcessing.Anim.AnimationMergeGroup;
import scr.IOProcessing.Anim.Animator;
import scr.IOProcessing.LoadImage.ImageMerge;
import scr.IOProcessing.LoadImage.ResSprites;
import scr.Model.Characters.Properties.Property;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class SwordsMan extends CharacterModel
{
    public SwordsmanCommand sc;
    public SwordsMan(IController iController) throws IOException {
        property = new Property(iController);

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
        assembleToAnimations("fall",129,131,false);
        assembleToAnimations("fallStand",132,132,false);
        assembleToAnimations("injure",96,99,false);
        assembleToAnimations("inair",100,101,false);
        assembleToAnimations("death",102,103,false);
    }

    @Override
    protected void setStates() {


        fsm = new FSM(this);
        //添加列表
        statesList.put(BaseStates.Idle,new Idle(this));
        statesList.put(BaseStates.Walk,new Walk(this));
        statesList.put(BaseStates.Run,new Run(this));
        statesList.put(SwordsManStatesTable.Attack,new Attack(this));
        statesList.put(SwordsManStatesTable.Attack2,new Attack2(this));
        statesList.put(SwordsManStatesTable.Attack3,new Attack3(this));
        statesList.put(SwordsManStatesTable.Jump,new Jump(this));
        statesList.put(SwordsManStatesTable.Fall,new Fall(this));
        statesList.put(BaseStates.Injure,new Injure(this));
        statesList.put(BaseStates.InAir,new InAir(this));
        statesList.put(BaseStates.Throw,new Throw(this));

        //初始化
        fsm.currentState = getState(BaseStates.Idle);
        fsm.currentState.onStart();
        property.states = BaseStates.Idle;
    }


    @Override
    public void render(Graphics g, JPanel jPanel, Transform transform) {

        if(property.states.equals(SwordsManStatesTable.Jump) || property.states.equals(SwordsManStatesTable.Fall) || property.states.equals(BaseStates.InAir) || property.states.equals(BaseStates.Throw))
        {
            Transform transform1 = new Transform(transform.xPos, property.horizontal.y);
            transform1.flipX = transform.flipX;
            animator.Flash(g,jPanel,transform1);

            //animator.Flash(g,jPanel, transform);
        }
        else
        {
            animator.Flash(g,jPanel, transform);
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

    public String[] list = {"body","coat","hair","pantsa","pantsb","shoesa","shoesb","sworda","swordb"
    };


}
