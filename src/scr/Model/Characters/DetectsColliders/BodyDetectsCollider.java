package scr.Model.Characters.DetectsColliders;

import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Collide.Colliders.ICollider;
import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.Robot.IController;
import scr.Model.Characters.CharacterEvents.HitEvent;
import scr.Model.Characters.CharacterEvents.HitListener;

public class BodyDetectsCollider extends BoxCollider implements HitListener {


    public BodyDetectsCollider(int x, int y, int w, int h, Vector2D vector2D) {
        super(x, y, w, h, vector2D);
    }

    public void receiveAttackCollider(IController player)
    {
        System.out.println("i was damaged by "+ player.getClass());
    }

    @Override
    public void GameEventInvoke(HitEvent event) {

    }
}
