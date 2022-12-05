package scr.Model.Characters.DetectsColliders;

import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Vector2D;

import java.util.ArrayList;

public class AttackDetectsCollider extends BoxCollider{

    int x,y,w,h;
    public AttackDetectsCollider(int x, int y, int w, int h, Vector2D vector2D) {
        super(x, y, w, h, vector2D);
        this.x =x;
        this.y =y;
        this.w = w;
        this.h = h;
    }

    public void attackDetect(ArrayList<BodyDetectsCollider> bodyDetectsColliders)
    {
        for(var b : bodyDetectsColliders)
        {
            if(colliderDetect(b))
            {
                b.receiveAttackCollider();
            }
        }

    }

}