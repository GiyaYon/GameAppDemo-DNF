package scr.Model.Characters.CharacterEvents;

import scr.LogicalProcessing.Collide.Colliders.ICollider;
import scr.LogicalProcessing.Events.GameEvent;

public class HitEvent extends GameEvent<HitEvent> {
    public float getHitValue() {
        return hitValue;
    }
    private float hitValue;
    public ICollider player;
    public HitEvent(Object source,float hitValue,ICollider iCollider) {
        super(source);
        this.hitValue = hitValue;
        this.player = iCollider;
    }


}
