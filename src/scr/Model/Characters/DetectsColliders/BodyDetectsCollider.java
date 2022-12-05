package scr.Model.Characters.DetectsColliders;

import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Vector2D;

public class BodyDetectsCollider extends BoxCollider {

    public BodyDetectsCollider(int x, int y, int w, int h, Vector2D vector2D) {
        super(x, y, w, h, vector2D);
    }

    public void receiveAttackCollider()
    {
        System.out.println("i was damaged by ");
    }

}
