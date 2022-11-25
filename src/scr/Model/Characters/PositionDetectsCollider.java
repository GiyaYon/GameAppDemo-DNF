package scr.Model.Characters;

import scr.Controller.Collide.Colliders.BoxCollider;
import scr.Controller.Collide.Colliders.PointCollider;
import scr.Entity.Players.Player;

public class PositionDetectsCollider extends PointCollider {

    Transform lastPosition;

    public PositionDetectsCollider(int x, int y) {
        super(x, y);
    }

    public boolean obstacle(BoxCollider boxCollider,Player p)
    {
        //更新点的位置
        lastPosition = p.transform;
        updatePosition(p.transform);
        if (colliderDetect(boxCollider))
        {
            System.out.println("crash");
            //不罚站
            lastPosition.yPos += 1;
            p.transform = lastPosition;
            return true;
        }
        return false;
    }

}
