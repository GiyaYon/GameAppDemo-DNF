package scr.Entity.Players;

import scr.Entity.Characters.Swordman.SwordsmanCommand;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterEvents.HitListener;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.Commands.ICommand;
import scr.LogicalProcessing.Robot.IController;
import scr.Entity.Characters.Swordman.SwordsManAnimator;

import scr.Model.Characters.DetectsColliders.BodyDetectsCollider;
import scr.Model.Characters.DetectsColliders.PositionDetectsCollider;
import scr.LogicalProcessing.Position.Transform;
import scr.Model.Characters.Properties.CharacterUIProperty;
import scr.Model.Characters.Properties.Property;
import scr.IOProcessing.Renders.IRender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * 玩家类。
 * 控制，
 * 记录，
 * 网络传输输入
 */

public class Player extends CharacterBaseModel implements ActionListener , IRender , IController {

    //控制器
    public PlayerControl playerControl;
    //记录器
    Timer RecordTimer;
    public Queue<ICommand> commands;
    ICommand c;

    //测试用文字说明
    public String info = "test";
    JPanel j;

    public Player(JPanel j,String cIDName) {
        this.j = j;
        commands = new LinkedList<ICommand>();
        RecordTimer = new Timer(50,this);
        RecordTimer.start();
        this.cIDName =cIDName;
    }

    public void Start() throws IOException {

        //属性
        property = new Property(this);
        cProperty = new CharacterUIProperty();
        transform = new Transform();

        //自定义：鬼剑士类命令集
        actionCommands = new SwordsmanCommand(this);

        //TODO 控制器需要抽象：例如控制怪物是否会报错
        playerControl = new PlayerControl(j,this);

        //物理碰撞
        pointCollider = new PositionDetectsCollider(transform.xPos,transform.yPos);
        //自定义：鬼剑士类动画集
        cAnimator = new SwordsManAnimator(this);
        cAnimator.init();
        //受伤判断
        bodyDetectsCollider = new BodyDetectsCollider(transform.xPos-10,transform.yPos-10,50,100,new Vector2D(0,0),this,transform);
        bodyDetectsCollider.hitManager.addHitListener(playerControl);
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.Injure));
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.Throw));
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.InAir));


        transform.xPos = 10;
        transform.yPos = 430;

    }

    public void Update()
    {
        Transform t2 = new Transform(transform.xPos-10,transform.yPos-100);
        bodyDetectsCollider.updatePosition(t2);
        if(!pointCollider.obstacle(stageModel.Borders,this)){
            if(!property.states.equals(BaseStates.Injure)  && !property.states.equals(BaseStates.InAir)  && !property.states.equals(BaseStates.Throw))
           {
               playerControl.detect();
               playerControl.Command();
            }
        }
        playerControl.input.update();
        cAnimator.update();
    }

    @Override
    public int getYPos() {
        return transform.yPos;
    }

    public void render(Graphics g, JPanel panel, Transform transform)
    {
        cAnimator.render(g,j,this.transform);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(c != null)
        {
            commands.offer(c);
        }
        if(commands.size() == 10)
        {
            info = ("The" +System.currentTimeMillis() + " SecondOfcommandElementIs:");
            for(int i = 0;i < 10;i++)
            {
                info += (" " +Objects.requireNonNull(commands.poll()).getClass().getSimpleName());
            }
        }
    }

    @Override
    public int compareTo(Object o) {
        IRender s = (IRender) o;
        if(transform.yPos>s.getYPos()){
            return 1;
        }else if(transform.yPos<s.getYPos()){
            return -1;
        }
        return 0;
    }

    @Override
    public String getName() {
        return cIDName;
    }
}
