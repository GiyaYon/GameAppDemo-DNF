package scr.Controller.StateMachine;

import scr.Model.Characters.CharacterModel;

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
        currentState.onStart();
    }
    public void UpdateFSM()
    {
        currentState.onUpdate();
    }
}
