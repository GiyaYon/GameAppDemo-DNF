package scr.Model.Characters.DetectsColliders;

import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterEvents.HitManager;
import scr.Model.Map.IObject;

public class BodyDetectsCollider extends BoxCollider {

    public HitManager hitManager;
    IObject obj;
    public BodyDetectsCollider(int x, int y, int w, int h, Vector2D vector2D, IObject name) {
        super(x, y, w, h, vector2D);
        obj = name;
        hitManager =  new HitManager();
    }

    public void receiveAttackCollider(CharacterBaseModel player)
    {
        hitManager.fireHit(player.cProperty.atk,player);
    }

    public void test(CharacterBaseModel player)
    {
        hitManager.fireHit(player.cProperty.atk,player);
    }
}
