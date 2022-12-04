package scr.Controller.Events;

import java.util.EventListener;

public interface HitListener extends EventListener {
     void hitEvent(HitEvent event);
}
