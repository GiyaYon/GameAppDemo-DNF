package scr.LogicalProcessing.Events;

import java.util.EventObject;

public class GameEvent<T extends EventObject> extends EventObject {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public GameEvent(Object source) {
        super(source);
    }
}
