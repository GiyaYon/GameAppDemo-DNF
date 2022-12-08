package scr.Model.Characters.CharacterEvents;

import scr.LogicalProcessing.Collide.Colliders.ICollider;
import scr.LogicalProcessing.Events.GameEvent;
import scr.Model.BasePlayer.CharacterBaseModel;

public class HitEvent extends GameEvent<HitEvent> {
    public float getHitValue() {
        return hitValue;
    }
    public CharacterBaseModel getPlayValue() {
        return player;
    }
    private float hitValue;
    private CharacterBaseModel player;
    public HitEvent(Object source, float hitValue,CharacterBaseModel player) {
        super(source);
        this.hitValue = hitValue;
        this.player = player;
    }


}
