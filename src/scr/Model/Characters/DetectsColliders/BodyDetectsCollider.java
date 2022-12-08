package scr.Model.Characters.DetectsColliders;

import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Map.IObject;

public class BodyDetectsCollider extends BoxCollider {

    IObject obj;
    public BodyDetectsCollider(int x, int y, int w, int h, Vector2D vector2D, IObject name) {
        super(x, y, w, h, vector2D);
        obj = name;
    }

    public void receiveAttackCollider(CharacterBaseModel player)
    {
        System.out.println("i am "+ obj.getName() +" was damaged by "+ player.getName());
    }

}
