package scr.Entity.Monster;

import scr.Entity.Characters.Swordman.SwordsManAnimator;
import scr.Entity.Characters.Swordman.SwordsManStatesTable;
import scr.Entity.Characters.Swordman.SwordsmanCommand;
import scr.Entity.Players.AIControl;
import scr.Entity.Players.RobotPlayer;
import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.NetWork.PlayerNetWorkControl;
import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.Robot.RStates.RobotStatesTable;
import scr.Model.Characters.CharacterEvents.HitEvent;
import scr.Model.Characters.CharacterEvents.HitListener;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.Commands.*;
import scr.Model.Characters.DetectsColliders.BodyDetectsCollider;
import scr.Model.Characters.DetectsColliders.PositionDetectsCollider;
import scr.Model.Characters.Forces.AttackEffect;
import scr.Model.Characters.Forces.AttackType;
import scr.Model.Characters.Properties.CharacterAnimator;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class NetworkSwordsMan extends RobotPlayer {

    public NetworkSwordsMan(JPanel j, String cIDName) throws IOException {
        super(j, cIDName);
        Start(new SwordsManAnimator(this),100,100);
    }
    @Override
    public void Start(CharacterAnimator ca, int attackRangeW, int attackRangeH) throws IOException
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
//        control  = new AIControl(this);
//        control.init();
        property.vector2D = new Vector2D(0, 0);
        iCommand = new NoneCommand(actionCommands);
        iCommand.Execute();
        this.attackRangeW = attackRangeW;
        this.attackRangeH = attackRangeH;

    }
    @Override
    public void Update()
    {
        if(property.states.equals(BaseStates.Death))return;
        if(!pointCollider.obstacle(stageModel.Borders,this)){}
            if (PlayerNetWorkControl.instance.resCommand.size() > 0) {
        try {

            String p = PlayerNetWorkControl.instance.resCommand.poll();
            String[] p1 = p.split("_");
            String[] p3 = p1[1].split(",");
            if (p1[0].equals("Walk")) {
                property.vector2D = new Vector2D(Integer.parseInt(p3[0]), Integer.parseInt(p3[1]));
                iCommand = new MoveCommand(actionCommands, property.vector2D, this.transform);
                iCommand.Execute();
            } else if (p1[0].equals("Run")) {
                property.vector2D = new Vector2D(Integer.parseInt(p3[0]), Integer.parseInt(p3[1]));
                iCommand = new RunCommand((SwordsmanCommand) actionCommands, property.vector2D, this.transform);
                iCommand.Execute();
            } else if (p1[0].equals("Attack")) {
                property.states = RobotStatesTable.Attack;
                property.director = transform.flipX;
                property.initHorizontalLine = transform;
                property.attackType = new AttackType(new Vector2D(property.director,0),10, AttackEffect.Light,new Force(property.director,1,0));
                iCommand = new AttackCommand((SwordsmanCommand)actionCommands,Integer.parseInt(p3[0]));
                iCommand.Execute();
            }else if(p1[0].equals("Jump"))
            {
                property.horizontal = new Vector2D(transform.xPos,transform.yPos);
                property.initHorizontalLine = transform;
                iCommand = new JumpCommand((SwordsmanCommand)actionCommands,transform);
                iCommand.Execute();
            }
            if (p1[0].equals("Idle") && !property.states.equals(BaseStates.Injure) &&! property.states.equals(BaseStates.Throw)&& !property.states.equals(BaseStates.InAir) && !property.states.equals(SwordsManStatesTable.Jump) && !property.states.equals(SwordsManStatesTable.Fall)) {
                property.vector2D = new Vector2D(0, 0);
                iCommand = new NoneCommand(actionCommands);
                iCommand.Execute();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        //control.Update();
        cAnimator.update();
    }

    @Override
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
        g.setColor(Color.red);
        g.drawString("P2",t2.xPos,t2.yPos-110);
        cAnimator.render(g,panel,t2);
        targetTransform = transform;
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
                    iCommand = new InjureCommand(actionCommands, attackType);
                    iCommand.Execute();
                } else if (attackType.effect.equals(AttackEffect.Heavy)) {
                    iCommand = new ThrowFlyCommand(actionCommands, attackType);
                    iCommand.Execute();
                }
            }

        }
    }
}
