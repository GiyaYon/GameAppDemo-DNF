package scr.Model.UI;

import scr.LogicalProcessing.Events.GameEventManager;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterEvents.HitEvent;

public class JumpManager extends GameEventManager<JumpListener,JumpEvent> {

    public void jump(int code)
    {
        if(listeners == null)
        {
            return;
        }
        JumpEvent event = new JumpEvent(this,code);
        notifyListeners(event);
    }
}
