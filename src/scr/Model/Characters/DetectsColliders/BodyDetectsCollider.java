package scr.Model.Characters.DetectsColliders;

import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterEvents.HitManager;
import scr.Model.Map.IObject;

public class BodyDetectsCollider extends BoxCollider {

    public HitManager hitManager;
    IObject obj;
    public Transform transform;
    public BodyDetectsCollider(int x, int y, int w, int h, Vector2D vector2D, IObject name,Transform transform) {
        super(x, y, w, h, vector2D);
        obj = name;
        hitManager =  new HitManager();
        this.transform = transform;
    }

    public void receiveAttackCollider(CharacterBaseModel player)
    {
        hitManager.fireHit(player.cProperty.atk,player);
    }
    public void updatePosition(Transform transform,Transform transform1) {
        s1.x = transform.xPos;
        s1.y = transform.yPos;
        this.transform = transform1;
    }
    public void test(CharacterBaseModel player)
    {
        hitManager.fireHit(player.cProperty.atk,player);
    }
}
