package scr.Entity.Players;

import scr.Controller.Commander.ICommand;
import scr.Controller.Events.HitManager;
import scr.Controller.IController;
import scr.Entity.Swordman.SwordsMan;

import scr.Model.Characters.PositionDetectsCollider;
import scr.Model.Characters.Transform;
import scr.Model.Map.MapModel;
import scr.Viewer.Renders.IRender;

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
 * 控制角色，
 * 记录控制
 * 网络传输输入
 */

public class Player implements ActionListener , IRender , IController {

    public SwordsMan swordsMan;
    public Transform transform;
    PlayerControl playerControl;
    PositionDetectsCollider pointCollider;
    public MapModel mapModel;

    public HitManager hitManager;



    Timer RecordTimer;
    public Queue<ICommand> commands;
    ICommand c;
    public String info = "test";
    JPanel j;
    public Player(JPanel j)
    {
        this.j = j;
        commands = new LinkedList<ICommand>();
        RecordTimer = new Timer(50,this);
        RecordTimer.start();
    }

    public void Start() throws IOException {
        transform = new Transform();
        swordsMan = new SwordsMan();
        playerControl = new PlayerControl(j,this);

        pointCollider = new PositionDetectsCollider(transform.xPos,transform.yPos);

        transform.xPos = 10;
        transform.yPos = 430;

        hitManager = new HitManager();
    }

    public void Update()
    {
        if(!pointCollider.obstacle(mapModel.Borders,this)){
            playerControl.detect();
            playerControl.Command();
        }
        playerControl.input.update();


        swordsMan.update();

    }

    @Override
    public int getYPos() {
        return transform.yPos;
    }

    public void render(Graphics g, JPanel panel, Transform transform)
    {
        swordsMan.render(g,j,this.transform);
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
}
