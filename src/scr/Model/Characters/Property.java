package scr.Model.Characters;

import scr.Controller.StateMachine.States;
import scr.Model.Characters.Transform;
import scr.Model.Characters.Vector2D;

/**
 * 角色属性
 */
public class Property {
    //·····················输入属性·······················

    //方向
    public Vector2D vector2D;
    //状态显示
    public States states;
    //地平线
    public Vector2D horizontal;
    //浮空显示
    public Transform flyView;

    //·····················游戏属性·······················

    //移速
    public int moveSpeed = 2;
    //攻速
    public int attackTimes = 55;


}
