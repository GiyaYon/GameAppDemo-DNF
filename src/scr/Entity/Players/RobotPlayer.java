package scr.Entity.Players;

import scr.Entity.Characters.Swordman.SwordsManAnimator;
import scr.Entity.Characters.Swordman.SwordsmanCommand;
import scr.IOProcessing.Renders.IRender;
import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.Robot.AStar;
import scr.LogicalProcessing.Robot.IController;
import scr.LogicalProcessing.Robot.Node;
import scr.LogicalProcessing.Robot.RStates.RobotStatesTable;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterEvents.HitEvent;
import scr.Model.Characters.CharacterEvents.HitListener;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.Commands.*;
import scr.Model.Characters.DetectsColliders.BodyDetectsCollider;
import scr.Model.Characters.DetectsColliders.PositionDetectsCollider;
import scr.Model.Characters.Forces.AttackEffect;
import scr.Model.Characters.Forces.AttackType;
import scr.Model.Characters.Properties.CharacterUIProperty;
import scr.Model.Characters.Properties.Property;
import scr.Model.Map.IObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Bot玩家类
 */

public class RobotPlayer extends CharacterBaseModel implements  IRender , IController, IObject,HitListener {

    //控制器
    public AIControl control;

    ArrayList<Node> a;

    BoxCollider attackRange;
    public BodyDetectsCollider target;
    Transform targetTransform;
    ICommand c;
    //测试用文字说明
    public String info = "test";
    JPanel j;

    int attackTime;

    public RobotPlayer(JPanel j, String cIDName) {
        this.j = j;
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

        transform.xPos = 150;
        transform.yPos = 430;
        //受伤判断
        bodyDetectsCollider = new BodyDetectsCollider(transform.xPos-50,transform.yPos-50,50,100,new Vector2D(0,0),this,transform);
        bodyDetectsCollider.hitManager.addHitListener(this);
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.Injure));
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.Throw));
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.InAir));

        attackRange = new BoxCollider(transform.xPos-50,transform.yPos-50,100,100,new Vector2D(0,0));
        control  = new AIControl(this);
        control.init();

    }

    public void setTransform(int x,int y)
    {
        this.transform.xPos = x;
        this.transform.yPos = y;
    }
    public void Update()
    {
        if(property.states.equals(BaseStates.Death))return;
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

        //攻击
        if(attackRange.isIntersect(attackRange.s1,target.s1) && (int)System.currentTimeMillis() - attackTime > 1500)
        {
            if(!property.states.equals(BaseStates.Throw)&&!property.states.equals(BaseStates.InAir) &&!property.states.equals(BaseStates.Injure))
            {
                if(targetTransform!=null && Math.abs(targetTransform.yPos - this.transform.yPos) <10 )
                {
                    property.states = RobotStatesTable.Attack;
                    property.director = transform.flipX;
                    property.initHorizontalLine = transform;
                    c = new AttackCommand((SwordsmanCommand) actionCommands,1);
                    c.Execute();
                    attackTime = (int)System.currentTimeMillis();
                }
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
        t2.flipX = this.transform.flipX;
        //System.out.println(Math.abs(t2.xPos) + transform.xPos);
        if(property.horizontal == null)
        {
            bodyDetectsCollider.updatePosition(new Transform(t2.xPos-20,t2.yPos-100),this.transform);
        }
        else
        {
            bodyDetectsCollider.updatePosition(new Transform(t2.xPos-20,property.horizontal.y-100),this.transform);
        }
        if(this.transform.flipX == 1)
        {
            attackRange.updatePosition(new Transform(t2.xPos,t2.yPos-100));
        }
        else
        {
            attackRange.updatePosition(new Transform(t2.xPos-80,t2.yPos-100));
        }
//        g.setColor(Color.green);
//        g.drawRect(bodyDetectsCollider.s1.x,bodyDetectsCollider.s1.y,bodyDetectsCollider.s1.w,bodyDetectsCollider.s1.h);
        //g.drawLine(t2.xPos,t2.yPos,transform.xPos,transform.yPos);
//        g.setColor(Color.red);
//        g.drawRect(attackRange.s1.x,attackRange.s1.y,attackRange.s1.w,attackRange.s1.h);
        if(this.transform.xPos != transform.xPos && this.transform.yPos != transform.yPos)a = findPath(transform,this.transform);

        //测试AStar
//        if(a.size()>0)
//        {
//            for (int i = a.size()-1; i > 0; i--) {
//                if(i+1 < a.size())g.drawLine(a.get(i).x,a.get(i).y,a.get(i).x,a.get(i).y);
////            this.transform.xPos = a.get(i).x;
////            this.transform.yPos = a.get(i).y;
//            }
//        }
        cAnimator.render(g,j,t2);
        targetTransform = transform;
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
                    cProperty.hp -= attackType.attackValue;
                    c = new InjureCommand(actionCommands, attackType);
                    c.Execute();
                } else if (attackType.effect.equals(AttackEffect.Heavy)) {
                    cProperty.hp -= attackType.attackValue * 1.5f;
                    c = new ThrowFlyCommand(actionCommands, attackType);
                    c.Execute();
                }
            }
            if(cProperty.hp - attackType.attackValue < 0)
            {
                c = new DeathCommand(actionCommands);
                c.Execute();
            }
        }
    }


    public ArrayList<Node> findPath(Transform target,Transform start) {

        ArrayList<Node> arrayList = new ArrayList<Node>();
        Node startNode = new Node(start.xPos, start.yPos);
        Node endNode = new Node(target.xPos, target.yPos);
        if(startNode.equals(endNode))return arrayList;
        Node parent = new AStar().findPath(startNode, endNode);// 返回的是终点，但是此时父节点已经确立，可以追踪到开始节点

        while (parent != null) {// 遍历刚才找到的路径。没问题
            // System.out.println(parent.x + ", " + parent.y);
            arrayList.add(new Node(parent.x, parent.y));
            parent = parent.parent;
        }
        return arrayList;
    }
}
