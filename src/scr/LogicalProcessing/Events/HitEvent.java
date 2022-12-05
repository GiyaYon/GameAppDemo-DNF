package scr.LogicalProcessing.Events;

import java.util.EventObject;

public class HitEvent extends EventObject {
    public float getHitValue() {
        return hitValue;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */

    private float hitValue;
    public HitEvent(Object source,float hitValue) {
        super(source);
        this.hitValue = hitValue;
    }


}
