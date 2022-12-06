package scr.Entity.Players;

import scr.Entity.Characters.Swordman.SwordsManStatesTable;
import scr.Model.Characters.Commands.ICommand;
import scr.LogicalProcessing.KeyBoardControl.KeyBoardInput;
import scr.LogicalProcessing.KeyBoardControl.KeyInfo;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Entity.Characters.Swordman.AttackCommand;
import scr.Entity.Characters.Swordman.JumpCommand;
import scr.Entity.Characters.Swordman.RunCommand;
import scr.Model.Characters.Commands.GameObjectCommands.MoveCommand;
import scr.Model.Characters.Commands.GameObjectCommands.NoneCommand;
import scr.LogicalProcessing.Position.Vector2D;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.*;

public class PlayerControl {

    Player player;
    KeyBoardInput input;


    boolean isAtkKeyRelease = true;
    int AtkSpeed = (int)System.currentTimeMillis();



    boolean isJumpKeyRelease = true;
    int jumpTime = (int)System.currentTimeMillis();


    ICommand c;
    public ArrayList<Integer> Keylist = new ArrayList<Integer>();
    public KeyInfo[] keyInfos = new KeyInfo[]
            {
                    new KeyInfo(KeyEvent.VK_RIGHT,0,0),
                    new KeyInfo(KeyEvent.VK_LEFT,0,0),
                    new KeyInfo(KeyEvent.VK_UP,0,0),
                    new KeyInfo(KeyEvent.VK_DOWN,0,0)
            };

    public PlayerControl(JPanel p,Player player)
    {
        input = KeyBoardInput.getKeyBoardInput();
        p.addKeyListener(input);
        this.player = player;

        c = new NoneCommand(player.swordsMan.sc);
        player.c = c;
        c.Execute();
    }

    public void Command()
    {
        if(input.isKeyDown(KeyEvent.VK_LEFT))
        {

            Vector2D vector2D = moveVectorInput(KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN);
            //持续按下，跑步状态延缓
            if(player.swordsMan.property.states.equals(BaseStates.Run))
            {
                c = new RunCommand(player.swordsMan.sc,vector2D,player.transform);
                player.c = c;
                //commands.offer(c);
                c.Execute();
                return;
            }
            if(player.swordsMan.property.states.equals(SwordsManStatesTable.Fall) || player.swordsMan.property.states.equals(SwordsManStatesTable.Jump))
            {
                player.transform.xPos += vector2D.x * player.swordsMan.property.moveSpeed;

                return;
            }
            player.swordsMan.property.director = -1;
            c = new MoveCommand(player.swordsMan.sc,vector2D,player.transform);

            //commands.offer(c);
            player.c = c;
            c.Execute();
            return;
        }
        if(input.isKeyDown(KeyEvent.VK_RIGHT))
        {
            Vector2D vector2D = moveVectorInput(KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN);
            if(player.swordsMan.property.states.equals(BaseStates.Run))
            {
                c = new RunCommand(player.swordsMan.sc,vector2D,player.transform);
                player.c = c;
                //commands.offer(c);
                c.Execute();
                return;
            }
            if(player.swordsMan.property.states.equals(SwordsManStatesTable.Fall) || player.swordsMan.property.states.equals(SwordsManStatesTable.Jump))
            {
                player.transform.xPos += vector2D.x * player.swordsMan.property.moveSpeed;
                return;
            }
            player.swordsMan.property.director = 1;
            c = new MoveCommand(player.swordsMan.sc,vector2D,player.transform);
            //commands.offer(c);
            player.c = c;
            c.Execute();
            return;
        }
        if(input.isKeyDown(KeyEvent.VK_UP))
        {
            Vector2D vector2D = moveVectorInput(KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN);
            if(player.swordsMan.property.states.equals(BaseStates.Run))
            {
                c = new RunCommand(player.swordsMan.sc,vector2D,player.transform);
                player.c = c;
                //commands.offer(c);
                c.Execute();
                return;
            }
            if(player.swordsMan.property.states.equals(SwordsManStatesTable.Fall) || player.swordsMan.property.states.equals(SwordsManStatesTable.Jump))
            {
                player.transform.yPos += vector2D.y * player.swordsMan.property.moveSpeed;
                player.swordsMan.property.initHorizontalLine.yPos = player.transform.yPos;

                return;
            }
            c = new MoveCommand(player.swordsMan.sc,vector2D,player.transform);
            //commands.offer(c);
            player.c = c;
            c.Execute();
            return;
        }
        if(input.isKeyDown(KeyEvent.VK_DOWN))
        {
            Vector2D vector2D = moveVectorInput(KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN);
            if(player.swordsMan.property.states.equals(BaseStates.Run))
            {
                c = new RunCommand(player.swordsMan.sc,vector2D,player.transform);
                player.c = c;
                //commands.offer(c);
                c.Execute();
                return;
            }
            if(player.swordsMan.property.states.equals(SwordsManStatesTable.Fall) || player.swordsMan.property.states.equals(SwordsManStatesTable.Jump))
            {
                player.transform.yPos += vector2D.y * player.swordsMan.property.moveSpeed;
                player.swordsMan.property.initHorizontalLine.yPos = player.transform.yPos;

                return;
            }
            c = new MoveCommand(player.swordsMan.sc,vector2D,player.transform);
            //commands.offer(c);
            player.c = c;
            c.Execute();
            return;
        }

        if(input.isKeyDown(KeyEvent.VK_X) && isAtkKeyRelease)
        {
            isAtkKeyRelease = false;
            player.swordsMan.property.initHorizontalLine = player.transform;
            if(player.swordsMan.property.states.equals(SwordsManStatesTable.Attack)|| player.swordsMan.property.states.equals(SwordsManStatesTable.Attack2)||
                    player.swordsMan.property.states.equals(SwordsManStatesTable.Attack3))
            {
                player.swordsMan.property.isReadyNextAttack = true;
            }
            //限制连续按键
            if((int)System.currentTimeMillis() - AtkSpeed >600 && (int)System.currentTimeMillis() - AtkSpeed < 1100)
            {
                //player.hitManager.fireHit(new Random().nextInt());
                if(player.swordsMan.property.states.equals(SwordsManStatesTable.Attack)|| player.swordsMan.property.states.equals(SwordsManStatesTable.Attack2)||
                        player.swordsMan.property.states.equals(SwordsManStatesTable.Attack3))
                {
                    return;
                }
                AtkSpeed = (int)System.currentTimeMillis();
                c = new AttackCommand(player.swordsMan.sc,player.swordsMan.property.AtkNext);

                player.swordsMan.property.AtkNext++;

                if(player.swordsMan.property.AtkNext == 4)
                {
                    player.swordsMan.property.AtkNext = 1;
                }
                //commands.offer(c);
                player.c = c;
                c.Execute();
                return;
            }
            //长时间不操作则初始化
            if((int)System.currentTimeMillis() - AtkSpeed >=1100)
            {
                player.swordsMan.property.AtkNext = 1;
                AtkSpeed = (int)System.currentTimeMillis();

                if(player.swordsMan.property.states.equals(SwordsManStatesTable.Attack)|| player.swordsMan.property.states.equals(SwordsManStatesTable.Attack2)||
                        player.swordsMan.property.states.equals(SwordsManStatesTable.Attack3))
                {
                    return;
                }
                c = new AttackCommand(player.swordsMan.sc,player.swordsMan.property.AtkNext);
                player.swordsMan.property.AtkNext++;
                //commands.offer(c);
                player.c = c;
                c.Execute();
                return;
            }

        }
        if(input.isKeyUp(KeyEvent.VK_X) && !isAtkKeyRelease)
        {
            isAtkKeyRelease = true;
        }

        if(input.isKeyDown(KeyEvent.VK_C) && isJumpKeyRelease && (int)System.currentTimeMillis() - jumpTime >1000)
        {
            isJumpKeyRelease = false;
            player.swordsMan.property.horizontal = new Vector2D(player.transform.xPos,player.transform.yPos);
            player.swordsMan.property.initHorizontalLine = player.transform;
            jumpTime = (int)System.currentTimeMillis();
            c = new JumpCommand(player.swordsMan.sc,player.transform);
            //commands.offer(c);
            player.c = c;
            c.Execute();
            return;
        }

        if(input.isKeyDown(KeyEvent.VK_V))
        {

            player.swordsMan.property.horizontal = new Vector2D(player.transform.xPos,player.transform.yPos);
            player.swordsMan.property.initHorizontalLine = player.transform;
            player.swordsMan.getFsm().ChangeState(BaseStates.Throw);
        }

        if(input.isKeyUp(KeyEvent.VK_C) && !isJumpKeyRelease)
        {
            isJumpKeyRelease = true;
        }

        if(!player.swordsMan.property.states.equals(SwordsManStatesTable.Jump) && !player.swordsMan.property.states.equals(SwordsManStatesTable.Fall)&&
        !player.swordsMan.property.states.equals(SwordsManStatesTable.Attack)&&!player.swordsMan.property.states.equals(SwordsManStatesTable.Attack2)&&
                !player.swordsMan.property.states.equals(SwordsManStatesTable.Attack3) && !player.swordsMan.property.states.equals(BaseStates.Injure)
        && !player.swordsMan.property.states.equals(BaseStates.InAir) &&!player.swordsMan.property.states.equals(BaseStates.Throw))
        {
            c = new NoneCommand(player.swordsMan.sc);
            player.c = c;
            c.Execute();
        }

    }

    public void detect()
    {
        for (var v :keyInfos)
        {
            if(v.count == 1 && input.isKeyDown(v.key))
            {
                int i = (int)System.currentTimeMillis() - v.time;
                if( i < 400 &&  i > 100 &&!player.swordsMan.property.states.equals(SwordsManStatesTable.Jump) && !player.swordsMan.property.states.equals(SwordsManStatesTable.Fall) )
                {
                    //触发跑步状态
                    Vector2D vector2D = moveVectorInput(KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN);
                    c = new RunCommand(player.swordsMan.sc,vector2D,player.transform);
                    player.c = c;
                    //commands.offer(c);
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


}
