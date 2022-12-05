package scr.Model.Characters.CharacterEvents;

import scr.LogicalProcessing.Events.GameEvent;

public class HitEvent extends GameEvent<HitEvent> {
    public float getHitValue() {
        return hitValue;
    }
    private float hitValue;
    public HitEvent(Object source,float hitValue) {
        super(source);
        this.hitValue = hitValue;
    }


}
