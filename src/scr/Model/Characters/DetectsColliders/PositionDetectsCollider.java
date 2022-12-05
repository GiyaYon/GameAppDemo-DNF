package scr.Model.Characters.DetectsColliders;

import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Collide.Colliders.PointCollider;
import scr.Entity.Players.Player;
import scr.Model.Characters.Position.Transform;

import java.util.ArrayList;

public class PositionDetectsCollider extends PointCollider {

    Transform lastPosition;

    public PositionDetectsCollider(int x, int y) {
        super(x, y);
    }

    public boolean obstacle(ArrayList<BoxCollider> boxCollider, Player p)
    {
        //更新点的位置
        lastPosition = p.transform;
        updatePosition(p.transform);
        for (BoxCollider collider : boxCollider) {

            if (colliderDetect(collider))
            {
                System.out.println("crash");
                //不罚站
                lastPosition.yPos += collider.vector2D.y;
                lastPosition.xPos += collider.vector2D.x;
                p.transform = lastPosition;
                return true;
            }
        }

        return false;
    }


}
