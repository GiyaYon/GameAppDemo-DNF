package scr.Model.Characters.Properties;

import scr.LogicalProcessing.Collide.Colliders.ICollider;
import scr.LogicalProcessing.Robot.IController;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.DetectsColliders.BodyDetectsCollider;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Characters.Forces.AttackType;

import java.util.ArrayList;

/**
 * 角色属性
 */
public class Property {
    public Property(CharacterBaseModel iCollider) {
        this.iCollider = iCollider;
    }

    public CharacterBaseModel iCollider;
    //·····················输入属性·······················
    //方向
    public Vector2D vector2D;
    //面朝方向
    public int director;
    //状态显示
    public String states;
    //地平线
    public Vector2D horizontal;
    //浮空显示
    public Vector2D flyView;
    public Transform initHorizontalLine;
    public ArrayList<BodyDetectsCollider> bdcs = new ArrayList<>();
    public boolean isReadyNextAttack;
    public int AtkNext = 1;
    public int throwTimes = 0;
    public int fallTimes = 1;
    public int defaultFallTime = 2;
    public boolean isRebound = false;

    //·····················游戏属性·······················

    //移速
    public int moveSpeed = 2;
    //攻速
    public int attackTimes = 55;
    //下次攻击类型
    public AttackType attackType;

}
