package scr.Model.Characters.CharacterEvents;

import scr.LogicalProcessing.Collide.Colliders.ICollider;
import scr.LogicalProcessing.Events.GameEventManager;

public class HitManager extends GameEventManager<HitListener,HitEvent> {

    public void fireHit(float value, ICollider player)
    {
        if(listeners == null)
        {
            return;
        }
        HitEvent event = new HitEvent(this,value,player);
        notifyListeners(event);
    }

}
