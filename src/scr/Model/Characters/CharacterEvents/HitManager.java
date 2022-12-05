package scr.Model.Characters.CharacterEvents;

import scr.LogicalProcessing.Events.GameEventManager;

public class HitManager extends GameEventManager<HitListener,HitEvent> {

    public void fireHit(float value)
    {
        if(listeners == null)
        {
            return;
        }
        HitEvent event = new HitEvent(this,value);
        notifyListeners(event);
    }

}
