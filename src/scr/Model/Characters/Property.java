package scr.Model.Characters;

import scr.Controller.StateMachine.States;
import scr.Model.Characters.Transform;
import scr.Model.Characters.Vector2D;

import java.util.ArrayList;

/**
 * 角色属性
 */
public class Property {
    //·····················输入属性·······················

    //方向
    public Vector2D vector2D;
    //
    public int director;
    //状态显示
    public States states;
    //地平线
    public Vector2D horizontal;
    //浮空显示
    public Vector2D flyView;
    public Transform initHorizontalLine;
    public ArrayList<BodyDetectsCollider> bdcs = new ArrayList<>();
    //·····················游戏属性·······················

    //移速
    public int moveSpeed = 2;
    //攻速
    public int attackTimes = 55;


}
