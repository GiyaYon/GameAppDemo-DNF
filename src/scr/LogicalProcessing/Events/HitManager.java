package scr.LogicalProcessing.Events;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

public class HitManager {
    private Collection listeners;

    public void addHitListener(HitListener listener)
    {
        if(listeners == null)
        {
            listeners = new HashSet();
        }
        listeners.add(listener);
    }
    public void removeHitListener(HitListener listener)
    {
        if(listener == null)
        {
            return;
        }
        listeners.remove(listener);
    }

    public void fireHit(float value)
    {
        if(listeners == null)
        {
            return;
        }
        HitEvent event = new HitEvent(this,value);
        notifyListeners(event);
    }

    private void notifyListeners(HitEvent event) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            HitListener listener = (HitListener) iter.next();
            listener.hitEvent(event);
        }
    }
}
