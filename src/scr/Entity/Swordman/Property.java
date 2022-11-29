package scr.Entity.Swordman;

import scr.Controller.StateMachine.States;
import scr.Entity.Players.Player;
import scr.Model.Characters.Vector2D;

public class Property {
    //逻辑属性
    public Vector2D vector2D;
    //状态显示
    public States states;
    //游戏属性
    public int MoveSpeed = 2;

    public int attackTimes = 55;
    public Player player;

    public Property(Player player) {
        this.player = player;
    }
}
