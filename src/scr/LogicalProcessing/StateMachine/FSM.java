package scr.LogicalProcessing.StateMachine;

import scr.Model.BasePlayer.CharacterBaseModel;

public class FSM {

    public IState currentState;
    private CharacterBaseModel c;

    public FSM(CharacterBaseModel c)
    {
        this.c = c;
    }
    public void ChangeState(String states)
    {
        currentState.onExit();
        currentState = c.swordsManAnimator.getState(states);
        c.property.states = states;
        currentState.onStart();
    }
    public void UpdateFSM()
    {
        currentState.onUpdate();
    }
}
