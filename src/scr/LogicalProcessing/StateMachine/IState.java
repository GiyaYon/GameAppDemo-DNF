package scr.LogicalProcessing.StateMachine;

public interface IState {
    void onStart();
    void onUpdate();
    void onExit();
}
