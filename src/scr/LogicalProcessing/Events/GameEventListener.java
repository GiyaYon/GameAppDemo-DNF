package scr.LogicalProcessing.Events;

import java.util.EventListener;

public interface GameEventListener<T extends GameEvent> extends EventListener {
    void GameEventInvoke(T event);
}
