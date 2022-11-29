package scr.Controller.StateMachine;

public interface IState {
    void onStart();
    void onUpdate();
    void onExit();
}
