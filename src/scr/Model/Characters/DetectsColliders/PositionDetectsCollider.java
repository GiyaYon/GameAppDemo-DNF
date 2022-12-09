package scr.Model.Characters.DetectsColliders;

import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Collide.Colliders.PointCollider;
import scr.Entity.Players.Player;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Map.TransportEvent;
import scr.Model.Map.TransportManager;

import java.util.ArrayList;

public class PositionDetectsCollider extends PointCollider {

    public TransportManager transportEvent= new TransportManager();
    Transform lastPosition;

    public PositionDetectsCollider(int x, int y) {
        super(x, y);
    }

    public boolean obstacle(ArrayList<BoxCollider> boxCollider, CharacterBaseModel p)
    {
        //更新点的位置
        lastPosition = p.transform;
        updatePosition(p.transform);
        for (BoxCollider collider : boxCollider) {

            if (colliderDetect(collider))
            {
                if(collider.vector2D.compare(new Vector2D(0,0))&&p.getName().equals("Miren13"))
                {
                    System.out.println("transport");
                    lastPosition.yPos = 420;
                    lastPosition.xPos = 30;
                    p.transform = lastPosition;
                    transportEvent.startTransport();
                }
                //不罚站
                lastPosition.yPos += collider.vector2D.y;
                lastPosition.xPos += collider.vector2D.x;
                p.transform = lastPosition;
                return true;
            }
        }
        return false;
    }
    public boolean obstacle(ArrayList<BoxCollider> boxCollider, Transform p)
    {
        //更新点的位置
        lastPosition = p;
        updatePosition(p);
        for (BoxCollider collider : boxCollider) {

            if (colliderDetect(collider))
            {
                if(collider.vector2D.compare(new Vector2D(0,0)))
                {
                    System.out.println("transport");
                    lastPosition.yPos = 420;
                    lastPosition.xPos = 30;
                    p = lastPosition;
                    transportEvent.startTransport();
                }
                //不罚站
                lastPosition.yPos += collider.vector2D.y;
                lastPosition.xPos += collider.vector2D.x;
                p = lastPosition;
                return true;
            }
        }
        return false;
    }

}
