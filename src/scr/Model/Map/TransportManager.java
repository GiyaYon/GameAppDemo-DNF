package scr.Model.Map;

import scr.LogicalProcessing.Collide.Colliders.ICollider;
import scr.LogicalProcessing.Events.GameEventManager;

public class TransportManager extends GameEventManager<TransportListener, TransportEvent> {

    public void startTransport()
    {
        if(listeners == null)
        {
            return;
        }
        TransportEvent event = new TransportEvent(this);
        notifyListeners(event);
    }

}
