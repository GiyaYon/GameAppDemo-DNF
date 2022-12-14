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
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.CharacterEvents.HitEvent;
import scr.Model.Characters.CharacterEvents.HitListener;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.Commands.*;
import scr.Model.Characters.DetectsColliders.BodyDetectsCollider;
import scr.Model.Characters.DetectsColliders.PositionDetectsCollider;
import scr.Model.Characters.Forces.AttackEffect;
import scr.Model.Characters.Forces.AttackType;
import scr.Model.Characters.Properties.CharacterAnimator;
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
    BoxCollider attackRange;
    public BodyDetectsCollider target;
    Transform targetTransform;
    protected ICommand iCommand;
    JPanel j;

    public int attackRangeW,attackRangeH,bodyW,bodyH;

    public RobotPlayer(JPanel j, String cIDName) {
        this.j = j;
        this.cIDName =cIDName;
        //属性
        property = new Property(this);
        cProperty = new CharacterUIProperty();
        transform = new Transform();
    }


    public void Start() throws IOException {



        // 自定义：鬼剑士类命令集
        actionCommands = new SwordsmanCommand(this);


        //物理碰撞
        pointCollider = new PositionDetectsCollider(transform.xPos,transform.yPos);

        //TODO 出生位置 : 改位置
        transform.xPos = 150;
        transform.yPos = 430;
        //TODO 受伤判断 : 改大小
        bodyDetectsCollider = new BodyDetectsCollider(transform.xPos-50,transform.yPos-50,50,100,new Vector2D(0,0),this,transform);
        bodyDetectsCollider.hitManager.addHitListener(this);
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.Injure));
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.Throw));
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.InAir));

        //TODO 攻击距离: 改大小
        attackRange = new BoxCollider(transform.xPos-50,transform.yPos-50,100,100,new Vector2D(0,0));
        control  = new AIControl(this);
        control.init();

//        //TODO 动画集：改动画集
//        cAnimator = new SwordsManAnimator(this);
//        cAnimator.init();
    }

    public void Start(CharacterAnimator ca,int attackRangeW,int attackRangeH) throws IOException
    {

        // 自定义：鬼剑士类命令集
        actionCommands = new SwordsmanCommand(this);

        //TODO 动画集：改动画集
        cAnimator = ca;
        cAnimator.init();
        //物理碰撞
        pointCollider = new PositionDetectsCollider(transform.xPos,transform.yPos);

        //TODO 出生位置 : 改位置
        transform.xPos = 150;
        transform.yPos = 430;
        //TODO 受伤判断 : 改大小
        bodyDetectsCollider = new BodyDetectsCollider(transform.xPos-50,transform.yPos-50,50,100,new Vector2D(0,0),this,transform);
        bodyDetectsCollider.hitManager.addHitListener(this);
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.Injure));
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.Throw));
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.InAir));

        //TODO 攻击距离: 改大小
        attackRange = new BoxCollider(transform.xPos-50,transform.yPos-50,attackRangeW,attackRangeH,new Vector2D(0,0));
        control  = new AIControl(this);
        control.init();
        this.attackRangeW = attackRangeW;
        this.attackRangeH = attackRangeH;

    }

    public void Start(CharacterAnimator ca,int attackRangeW,int attackRangeH,int bodyW,int bodyH) throws IOException
    {

        // 自定义：鬼剑士类命令集
        actionCommands = new SwordsmanCommand(this);

        //TODO 动画集：改动画集
        cAnimator = ca;
        cAnimator.init();
        //物理碰撞
        pointCollider = new PositionDetectsCollider(transform.xPos,transform.yPos);

        //TODO 出生位置 : 改位置
        transform.xPos = 150;
        transform.yPos = 430;
        //TODO 受伤判断 : 改大小
        bodyDetectsCollider = new BodyDetectsCollider(transform.xPos-50,transform.yPos-50,bodyW,bodyH,new Vector2D(0,0),this,transform);
        bodyDetectsCollider.hitManager.addHitListener(this);
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.Injure));
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.Throw));
        bodyDetectsCollider.hitManager.addHitListener((HitListener) cAnimator.getState(BaseStates.InAir));

        //TODO 攻击距离: 改大小
        attackRange = new BoxCollider(transform.xPos-50,transform.yPos-50,attackRangeW,attackRangeH,new Vector2D(0,0));
        control  = new AIControl(this);
        control.init();
        this.attackRangeW = attackRangeW;
        this.attackRangeH = attackRangeH;
        this.bodyW = bodyW;
        this.bodyH = bodyH;

    }
    public void setTransform(int x,int y)
    {
        this.transform.xPos = x;
        this.transform.yPos = y;
    }


    public void Update()
    {
        if(property.states.equals(BaseStates.Death))return;
        if(!pointCollider.obstacle(stageModel.Borders,this)){}
        control.Update();
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
            attackRange.updatePosition(new Transform(t2.xPos-attackRangeW,t2.yPos-100));
        }
//        g.setColor(Color.green);
//        g.drawRect(bodyDetectsCollider.s1.x,bodyDetectsCollider.s1.y,bodyDetectsCollider.s1.w,bodyDetectsCollider.s1.h);
//        g.setColor(Color.red);
//        g.drawRect(attackRange.s1.x,attackRange.s1.y,attackRange.s1.w,attackRange.s1.h);

        if(this.transform.xPos != transform.xPos && this.transform.yPos != transform.yPos)control.a = control.findPath(transform,this.transform);
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
                iCommand = new ThrowFlyCommand(actionCommands, attackType);
                iCommand.Execute();
            }
            else
            {
                if(attackType.effect.equals(AttackEffect.Light))
                {
                    cProperty.hp -= attackType.attackValue;
                    iCommand = new InjureCommand(actionCommands, attackType);
                    iCommand.Execute();
                } else if (attackType.effect.equals(AttackEffect.Heavy)) {
                    cProperty.hp -= attackType.attackValue * 1.5f;
                    iCommand = new ThrowFlyCommand(actionCommands, attackType);
                    iCommand.Execute();
                }
            }
            if(cProperty.hp - attackType.attackValue < 0)
            {
                iCommand = new DeathCommand(actionCommands);
                iCommand.Execute();
            }
        }
    }


}
