package scr.Model.Characters;

import scr.Controller.Collide.Colliders.BoxCollider;
import scr.Controller.Events.HitEvent;
import scr.Controller.Events.HitListener;
import scr.Controller.IController;

public class BodyDetectsCollider extends BoxCollider {

    public BodyDetectsCollider(int x, int y, int w, int h, Vector2D vector2D) {
        super(x, y, w, h, vector2D);
    }

    public void receiveAttackCollider()
    {
        System.out.println("i was damaged by ");
    }

}
