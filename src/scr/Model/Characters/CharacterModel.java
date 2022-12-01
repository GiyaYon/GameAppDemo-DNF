package scr.Model.Characters;

import scr.Controller.StateMachine.FSM;
import scr.Controller.StateMachine.IState;
import scr.Controller.StateMachine.States;
import scr.Viewer.Anim.Animator;
import scr.Viewer.Anim.AnimationMergeGroup;
import scr.Viewer.LoadImage.ReloadAnimResources;

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
    protected HashMap<States,IState> statesList = new HashMap<>();

    public Property property = new Property();

    protected abstract void setAnimResources() throws IOException;
    protected abstract void setAnimator();
    protected abstract void setAnimations();
    protected abstract void setStates();

    public IState getState(States states) {return statesList.get(states);}
    public FSM getFsm() {return fsm;}
    public Animator getAnimator(){return animator;}
    public AnimationMergeGroup getAnimation(String s){return animations.get(s);}

    public abstract void render(Graphics g, JPanel jPanel,Transform transform);
    public abstract void update();
}
