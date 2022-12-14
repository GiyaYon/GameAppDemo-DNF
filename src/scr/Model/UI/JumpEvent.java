package scr.Model.UI;

import scr.LogicalProcessing.Events.GameEvent;

public class JumpEvent extends GameEvent<JumpEvent> {
    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    int jumpCode;
    public JumpEvent(Object source,int code) {
        super(source);
        jumpCode = code;
    }
}
