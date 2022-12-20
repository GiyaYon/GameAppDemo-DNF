package scr.Entity.Players;

import scr.Entity.Characters.Swordman.SwordsManStatesTable;
import scr.Entity.Characters.Swordman.SwordsmanCommand;
import scr.LogicalProcessing.NetWork.BufferType;
import scr.LogicalProcessing.Physics.Force;
import scr.Model.Characters.CharacterEvents.HitEvent;
import scr.Model.Characters.CharacterEvents.HitListener;
import scr.Model.Characters.Commands.*;
import scr.LogicalProcessing.KeyBoardControl.KeyBoardInput;
import scr.LogicalProcessing.KeyBoardControl.KeyInfo;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Characters.Forces.AttackEffect;
import scr.Model.Characters.Forces.AttackType;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;

public class PlayerControl implements HitListener ,ActionListener{
    Player player;
    public KeyBoardInput input;
    //攻击按键限制
    boolean isAtkKeyRelease = true;
    int AtkSpeed = (int)System.currentTimeMillis();
    //跳跃按键限制
    boolean isJumpKeyRelease = true;
    int jumpTime = (int)System.currentTimeMillis();

    //受伤检测
    boolean isHitRelease = true;
    int hitTime = (int)System.currentTimeMillis();

    ICommand c;
    public ArrayList<Integer> Keylist = new ArrayList<Integer>();
    public KeyInfo[] keyInfos = new KeyInfo[]
            {
                    new KeyInfo(KeyEvent.VK_RIGHT,0,0),
                    new KeyInfo(KeyEvent.VK_LEFT,0,0),
                    new KeyInfo(KeyEvent.VK_UP,0,0),
                    new KeyInfo(KeyEvent.VK_DOWN,0,0),

            };

    public PlayerControl(JPanel p,Player player)
    {
        input = KeyBoardInput.getKeyBoardInput();
        p.addKeyListener(input);
        this.player = player;

        c = new NoneCommand(player.actionCommands);
        player.command = c;
        c.Execute();
    }

    public void Command()
    {
        if(!player.property.states.equals(SwordsManStatesTable.Attack)&& !player.property.states.equals(SwordsManStatesTable.Attack2)&&
                !player.property.states.equals(SwordsManStatesTable.Attack3)) {
            Vector2D v = moveVectorInput(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
            if (player.property.states.equals(BaseStates.Run)) {
                c = new RunCommand((SwordsmanCommand) player.actionCommands, v, player.transform);
                player.command = c;
                c.Execute();
                return;
            }
            if (input.isKeyDown(KeyEvent.VK_LEFT)) {
                //持续按下，跑步状态延缓
                if (player.property.states.equals(SwordsManStatesTable.Fall) || player.property.states.equals(SwordsManStatesTable.Jump)) {
                    player.transform.xPos += v.x * player.property.moveSpeed;
                    return;
                }
                player.property.director = -1;
                c = new MoveCommand(player.actionCommands, v, player.transform);
                player.command = c;
                c.Execute();
                return;
            }
            if (input.isKeyDown(KeyEvent.VK_RIGHT)) {
                if (player.property.states.equals(SwordsManStatesTable.Fall) || player.property.states.equals(SwordsManStatesTable.Jump)) {
                    player.transform.xPos += v.x * player.property.moveSpeed;
                    return;
                }
                player.property.director = 1;
                c = new MoveCommand(player.actionCommands, v, player.transform);
                player.command = c;
                c.Execute();
                return;
            }
            if (player.property.states.equals(SwordsManStatesTable.Fall) || player.property.states.equals(SwordsManStatesTable.Jump)) {
                player.transform.yPos += v.y * player.property.moveSpeed;
                player.property.initHorizontalLine.yPos = player.transform.yPos;
                return;
            }
            if (input.isKeyDown(KeyEvent.VK_UP)) {
                c = new MoveCommand(player.actionCommands, v, player.transform);
                player.command = c;
                c.Execute();
                return;
            }
            if (input.isKeyDown(KeyEvent.VK_DOWN)) {
                c = new MoveCommand(player.actionCommands, v, player.transform);
                player.command = c;
                c.Execute();
                return;
            }
        }

        if(input.isKeyDown(KeyEvent.VK_X) && isAtkKeyRelease)
        {
            isAtkKeyRelease = false;
            player.property.initHorizontalLine = player.transform;
            if(player.property.states.equals(SwordsManStatesTable.Attack)|| player.property.states.equals(SwordsManStatesTable.Attack2)||
                    player.property.states.equals(SwordsManStatesTable.Attack3))
            {
                player.property.isReadyNextAttack = true;
            }
            //限制连续按键
            if((int)System.currentTimeMillis() - AtkSpeed >600 && (int)System.currentTimeMillis() - AtkSpeed < 1100)
            {
                //player.hitManager.fireHit(new Random().nextInt());
                if(player.property.states.equals(SwordsManStatesTable.Attack)|| player.property.states.equals(SwordsManStatesTable.Attack2)||
                        player.property.states.equals(SwordsManStatesTable.Attack3))
                {
                    return;
                }
                AtkSpeed = (int)System.currentTimeMillis();
                c = new AttackCommand((SwordsmanCommand)player.actionCommands,player.property.AtkNext);
                player.property.AtkNext++;

                if(player.property.AtkNext == 4)
                {
                    player.property.AtkNext = 1;
                }
                player.command = c;
                c.Execute();
                return;
            }
            //长时间不操作则初始化
            if((int)System.currentTimeMillis() - AtkSpeed >=1100)
            {
                player.property.AtkNext = 1;
                AtkSpeed = (int)System.currentTimeMillis();

                if(player.property.states.equals(SwordsManStatesTable.Attack)|| player.property.states.equals(SwordsManStatesTable.Attack2)||
                        player.property.states.equals(SwordsManStatesTable.Attack3))
                {
                    return;
                }
                c = new AttackCommand((SwordsmanCommand)player.actionCommands,player.property.AtkNext);
                player.property.AtkNext++;
                player.command = c;
                c.Execute();

                return;
            }
        }
        if(input.isKeyUp(KeyEvent.VK_X) && !isAtkKeyRelease)
        {
            isAtkKeyRelease = true;
        }

        //跳跃按键按下逻辑
        if(input.isKeyDown(KeyEvent.VK_C) && isJumpKeyRelease && (int)System.currentTimeMillis() - jumpTime >2000)
        {
            isJumpKeyRelease = false;
            player.property.horizontal = new Vector2D(player.transform.xPos,player.transform.yPos);
            player.property.initHorizontalLine = player.transform;
            jumpTime = (int)System.currentTimeMillis();
            c = new JumpCommand((SwordsmanCommand)player.actionCommands,player.transform);
            player.command = c;
            c.Execute();

            return;
        }
        //跳跃按键松开逻辑
        if(input.isKeyUp(KeyEvent.VK_C) && !isJumpKeyRelease)
        {
            isJumpKeyRelease = true;
        }

        //测试受伤键
        if(input.isKeyDown(KeyEvent.VK_V)&& isHitRelease &&(int)System.currentTimeMillis() - jumpTime >1000)
        {
            isHitRelease = false;
            player.property.attackType = new AttackType(new Vector2D(-1,0),10,AttackEffect.Light,new Force(1,1,0));
            //player.property.attackType = new AttackType(new Vector2D(-1,0),10,AttackEffect.Heavy,new Force(1,1,0));
            player.bodyDetectsCollider.test(player);
        }
        if(input.isKeyUp(KeyEvent.VK_V) && !isHitRelease)
        {
            isHitRelease = true;
        }

        if(!player.property.states.equals(SwordsManStatesTable.Jump) && !player.property.states.equals(SwordsManStatesTable.Fall)&&
        !player.property.states.equals(SwordsManStatesTable.Attack)&&!player.property.states.equals(SwordsManStatesTable.Attack2)&&
                !player.property.states.equals(SwordsManStatesTable.Attack3) && !player.property.states.equals(BaseStates.Injure)
        && !player.property.states.equals(BaseStates.InAir) &&!player.property.states.equals(BaseStates.Throw))
        {
            c = new NoneCommand(player.actionCommands);
            player.command = c;
            c.Execute();
        }



    }

    public void detect()
    {
        Vector2D vector2D = moveVectorInput(KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN);
        for (var v :keyInfos)
        {
            if(v.count == 1 && input.isKeyDown(v.key))
            {
                int i = (int)System.currentTimeMillis() - v.time;
                if( i < 400 &&  i > 100 &&!player.property.states.equals(SwordsManStatesTable.Jump) && !player.property.states.equals(SwordsManStatesTable.Fall) )
                {
                    //触发跑步状态
                    c = new RunCommand((SwordsmanCommand)player.actionCommands,vector2D,player.transform);
                    player.command = c;
                    c.Execute();
                }
                v.count = 0;
                v.time = 0;
                i = 0;
            }

            if(input.isKeyDown(v.key))
            {
                v.count++;
                v.time = (int) System.currentTimeMillis();

            }
        }

    }

    /// <summary>
    /// 移动输入方法
    /// </summary>
    /// <param name="left"></param>
    /// <param name="right"></param>
    /// <param name="top"></param>
    /// <param name="bottom"></param>
    /// <returns></returns>
    public Vector2D moveVectorInput(int left, int right, int top, int bottom)
    {
        Vector2D vector2D = new Vector2D();
        vector2D.x = (input.isKeyDown(right) ? 1 : 0) - (input.isKeyDown(left) ? 1 : 0);
        vector2D.y = (input.isKeyDown(bottom) ? 1 : 0) - (input.isKeyDown(top) ? 1 : 0);
        return vector2D;
    }



    @Override
    public void GameEventInvoke(HitEvent event) {
        AttackType attackType = event.getPlayValue().property.attackType;
        if(attackType!=null)
        {
            if(player.property.states.equals(BaseStates.Throw)||player.property.states.equals(BaseStates.InAir))
            {
                c = new ThrowFlyCommand(player.actionCommands, attackType);
                c.Execute();
            }
            else
            {
                if(attackType.effect.equals(AttackEffect.Light))
                {
                    c = new InjureCommand(player.actionCommands, attackType);
                    player.command = c;
                    c.Execute();
                } else if (attackType.effect.equals(AttackEffect.Heavy)) {
                    c = new ThrowFlyCommand(player.actionCommands, attackType);
                    player.command = c;
                    c.Execute();
                }
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Vector2D vector2D = moveVectorInput(KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_DOWN);
        if(!vector2D.compare(new Vector2D(0,0)))
        {
            if(player.property.states.equals(BaseStates.Run))
            {
                player.playerNetWork.sedCommand.add("Run_"+vector2D.x+","+vector2D.y);
                return;
            }
            player.playerNetWork.sedCommand.add("Walk_"+vector2D.x+","+vector2D.y);
        }
        else
        {
            player.playerNetWork.sedCommand.add("Idle_"+0+","+0);
        }


    }

}
