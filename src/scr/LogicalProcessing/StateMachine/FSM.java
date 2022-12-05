package scr.LogicalProcessing.StateMachine;

import scr.Model.Characters.Properties.CharacterModel;

public class FSM {

    public IState currentState;
    private CharacterModel c;

    public FSM(CharacterModel c)
    {
        this.c = c;
    }
    public void ChangeState(States states)
    {
        currentState.onExit();
        currentState = c.getState(states);
        c.property.states = states;
        currentState.onStart();
    }
    public void UpdateFSM()
    {
        currentState.onUpdate();
    }
}
