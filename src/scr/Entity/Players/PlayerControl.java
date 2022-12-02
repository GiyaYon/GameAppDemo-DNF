package scr.Entity.Players;

import scr.Controller.Commander.ICommand;
import scr.Controller.KeyBoardControl.KeyBoardInput;
import scr.Controller.KeyBoardControl.KeyInfo;
import scr.Controller.StateMachine.States;
import scr.Entity.Swordman.AttackCommand;
import scr.Entity.Swordman.JumpCommand;
import scr.Entity.Swordman.RunCommand;
import scr.Controller.Commander.GameObjectCommands.MoveCommand;
import scr.Controller.Commander.GameObjectCommands.NoneCommand;
import scr.Model.Characters.JumpForce;
import scr.Model.Characters.Vector2D;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.*;

public class PlayerControl {

    Player player;
    KeyBoardInput input;


    boolean isAtkKeyRelease = true;
    int AtkSpeed = (int)System.currentTimeMillis();
    int AtkNext = 1;


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
            if(player.swordsMan.property.states.equals(States.Run))
            {
                c = new RunCommand(player.swordsMan.sc,vector2D,player.transform);
                player.c = c;
                //commands.offer(c);
                c.Execute();
                return;
            }
            c = new MoveCommand(player.swordsMan.sc,vector2D,player.transform);
            //commands.offer(c);
            player.c = c;
            c.Execute();
            return;
        }
        if(input.isKeyDown(KeyEvent.VK_RIGHT))
        {
            Vector2D vector2D = moveVectorInput(KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN);
            if(player.swordsMan.property.states.equals(States.Run))
            {
                c = new RunCommand(player.swordsMan.sc,vector2D,player.transform);
                player.c = c;
                //commands.offer(c);
                c.Execute();
                return;
            }
            c = new MoveCommand(player.swordsMan.sc,vector2D,player.transform);
            //commands.offer(c);
            player.c = c;
            c.Execute();
            return;
        }
        if(input.isKeyDown(KeyEvent.VK_UP))
        {
            Vector2D vector2D = moveVectorInput(KeyEvent.VK_LEFT,KeyEvent.VK_RIGHT,KeyEvent.VK_UP,KeyEvent.VK_DOWN);
            if(player.swordsMan.property.states.equals(States.Run))
            {
                c = new RunCommand(player.swordsMan.sc,vector2D,player.transform);
                player.c = c;
                //commands.offer(c);
                c.Execute();
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
            if(player.swordsMan.property.states.equals(States.Run))
            {
                c = new RunCommand(player.swordsMan.sc,vector2D,player.transform);
                player.c = c;
                //commands.offer(c);
                c.Execute();
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
            if((int)System.currentTimeMillis() - AtkSpeed >600 && (int)System.currentTimeMillis() - AtkSpeed < 1100)
            {
                AtkSpeed = (int)System.currentTimeMillis();
                c = new AttackCommand(player.swordsMan.sc,AtkNext);
                AtkNext++;
                if(AtkNext == 4)
                {
                    AtkNext = 1;
                }
                //commands.offer(c);
                player.c = c;
                c.Execute();
                return;
            }
            if((int)System.currentTimeMillis() - AtkSpeed >=1100)
            {
                AtkNext = 1;
                AtkSpeed = (int)System.currentTimeMillis();
                c = new AttackCommand(player.swordsMan.sc,AtkNext);
                AtkNext++;
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
            jumpTime = (int)System.currentTimeMillis();
            c = new JumpCommand(player.swordsMan.sc,player.transform);
            //commands.offer(c);
            player.c = c;
            c.Execute();
            return;
        }

        if(player.swordsMan.property.states.equals(States.Jump))
        {
            player.transform.yPos += player.swordsMan.property.horizontal.y;
        }

        if(input.isKeyUp(KeyEvent.VK_C) && !isJumpKeyRelease)
        {
            isJumpKeyRelease = true;
        }

        c = new NoneCommand(player.swordsMan.sc);
        player.c = c;
        c.Execute();
    }

    public void detect()
    {
        for (var v :keyInfos)
        {
            if(v.count == 1 && input.isKeyDown(v.key))
            {
                int i = (int)System.currentTimeMillis() - v.time;
                if( i < 400 &&  i > 100)
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
