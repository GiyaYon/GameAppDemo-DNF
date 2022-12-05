package scr.LogicalProcessing.Events;

import java.util.Collection;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;

public class GameEventManager<T1 extends GameEventListener<T2>,T2 extends GameEvent<T2>> {
    protected Collection listeners;

    public void addHitListener(T1 listener)
    {
        if(listeners == null)
        {
            listeners = new HashSet();
        }
        listeners.add(listener);
    }
    public void removeHitListener(T1 listener)
    {
        if(listener == null)
        {
            return;
        }
        listeners.remove(listener);
    }

    protected void notifyListeners(T2 event) {
        Iterator iter = listeners.iterator();
        while (iter.hasNext()) {
            T1 listener = (T1) iter.next();
            listener.GameEventInvoke(event);
        }
    }
}
