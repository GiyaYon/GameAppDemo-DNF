package scr.Model.Characters.Properties;

import scr.LogicalProcessing.StateMachine.FSM;
import scr.LogicalProcessing.StateMachine.IState;
import scr.LogicalProcessing.Position.Transform;
import scr.IOProcessing.Anim.Animator;
import scr.IOProcessing.Anim.AnimationMergeGroup;
import scr.IOProcessing.LoadImage.ReloadAnimResources;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public abstract class CharacterModel
{
    protected ReloadAnimResources animResources = new ReloadAnimResources();
    protected Animator animator;
    protected FSM fsm;
    protected HashMap<String, AnimationMergeGroup> animations = new HashMap<>();
    protected HashMap<String,IState> statesList = new HashMap<>();

    public Property property;

    protected abstract void setAnimResources() throws IOException;
    protected abstract void setAnimator();
    protected abstract void setAnimations();
    protected abstract void setStates();

    public IState getState(String states) {return statesList.get(states);}
    public FSM getFsm() {return fsm;}
    public Animator getAnimator(){return animator;}
    public AnimationMergeGroup getAnimation(String s){return animations.get(s);}

    public abstract void render(Graphics g, JPanel jPanel, Transform transform);
    public abstract void update();
}
