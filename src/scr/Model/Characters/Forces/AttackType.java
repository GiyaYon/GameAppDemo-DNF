package scr.Model.Characters.Forces;

import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.Position.Vector2D;

public class AttackType {
    public Vector2D attackVector;
    public float attackValue;
    public AttackEffect effect;
    public Force force;

    public AttackType(Vector2D attackVector, float attackValue, AttackEffect effect,Force force) {
        this.attackVector = attackVector;
        this.attackValue = attackValue;
        this.effect = effect;
        this.force = force;
    }
}
