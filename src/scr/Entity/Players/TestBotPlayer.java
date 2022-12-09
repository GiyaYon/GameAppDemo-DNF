package scr.Entity.Players;

import scr.Entity.Characters.Swordman.SwordsManAnimator;
import scr.Entity.Characters.Swordman.SwordsmanCommand;
import scr.IOProcessing.Renders.IRender;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.Robot.IController;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterEvents.HitEvent;
import scr.Model.Characters.CharacterEvents.HitListener;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.Commands.ICommand;
import scr.Model.Characters.Commands.InjureCommand;
import scr.Model.Characters.Commands.NoneCommand;
import scr.Model.Characters.Commands.ThrowFlyCommand;
import scr.Model.Characters.DetectsColliders.BodyDetectsCollider;
import scr.Model.Characters.DetectsColliders.PositionDetectsCollider;
import scr.Model.Characters.Forces.AttackEffect;
import scr.Model.Characters.Forces.AttackType;
import scr.Model.Characters.Properties.CharacterUIProperty;
import scr.Model.Characters.Properties.Property;
import scr.Model.Map.IObject;

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

public class TestBotPlayer extends CharacterBaseModel implements ActionListener , IRender , IController, IObject,HitListener {

    //控制器
    //public PlayerControl playerControl;
    //记录器
    Timer RecordTimer;
    public Queue<ICommand> commands;
    ICommand c;
    //测试用文字说明
    public String info = "test";
    JPanel j;

    public TestBotPlayer(JPanel j, String cIDName) {
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


        //物理碰撞
        pointCollider = new PositionDetectsCollider(transform.xPos,transform.yPos);
        //自定义：鬼剑士类动画集
        cAnimator = new SwordsManAnimator(this);
        cAnimator.init();

        transform.xPos = 400;
        transform.yPos = 430;
        //受伤判断
        bodyDetectsCollider = new BodyDetectsCollider(transform.xPos-50,transform.yPos-50,50,100,new Vector2D(0,0),this);
        bodyDetectsCollider.hitManager.addHitListener(this);
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.Injure));
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.Throw));
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.InAir));

        ICommand c = new NoneCommand(actionCommands);
        c.Execute();
    }

    public void Update()
    {

        //bodyDetectsCollider.updatePosition(transform);
        if(!pointCollider.obstacle(stageModel.Borders,this)){
            if(!property.states.equals(BaseStates.Injure)  && !property.states.equals(BaseStates.InAir)  && !property.states.equals(BaseStates.Throw))
           {
//               if(playerControl!=null)
//               {
//                   playerControl.detect();
//                   playerControl.Command();
//               }
            }
        }
        //if(playerControl!=null)playerControl.input.update();
        cAnimator.update();
    }

    @Override
    public int getYPos() {
        return transform.yPos;
    }

    public void render(Graphics g, JPanel panel, Transform transform)
    {


        Transform t2 = new Transform(this.transform.xPos*2 - transform.xPos,this.transform.yPos);
        //System.out.println(Math.abs(t2.xPos) + transform.xPos);
        if(property.horizontal == null)
        {
            bodyDetectsCollider.updatePosition(new Transform(t2.xPos-20,t2.yPos-100));
        }
        else
        {
            bodyDetectsCollider.updatePosition(new Transform(t2.xPos-20,property.horizontal.y-100));
        }
        cAnimator.render(g,j,t2);
        g.setColor(Color.green);
        g.drawRect(bodyDetectsCollider.s1.x,bodyDetectsCollider.s1.y,bodyDetectsCollider.s1.w,bodyDetectsCollider.s1.h);
        System.out.println(this.transform.xPos);
        //logicTransform.xPos = (Math.abs(t2.xPos) + transform.xPos)/2;
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

    @Override
    public void GameEventInvoke(HitEvent event) {
        System.out.println("test");
        AttackType attackType = event.getPlayValue().property.attackType;
        if(attackType!=null)
        {
            if(property.states.equals(BaseStates.Throw)||property.states.equals(BaseStates.InAir))
            {
                c = new ThrowFlyCommand(actionCommands, attackType);
                c.Execute();
            }
            else
            {
                if(attackType.effect.equals(AttackEffect.Light))
                {
                    c = new InjureCommand(actionCommands, attackType);
                    c.Execute();
                } else if (attackType.effect.equals(AttackEffect.Heavy)) {
                    c = new ThrowFlyCommand(actionCommands, attackType);
                    c.Execute();
                }
            }
        }
    }
}
