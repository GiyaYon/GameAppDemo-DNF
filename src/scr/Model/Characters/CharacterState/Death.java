package scr.Model.Characters.CharacterState;

import scr.LogicalProcessing.StateMachine.IState;
import scr.Model.Characters.Properties.CharacterModel;

public class Death extends CharacterStates implements IState
{

    public Death(CharacterModel c) {
        super(c);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onExit() {

    }
}