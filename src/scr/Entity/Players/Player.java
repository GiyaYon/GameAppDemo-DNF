package scr.Entity.Players;

import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.Commands.ICommand;
import scr.LogicalProcessing.Robot.IController;
import scr.Entity.Characters.Swordman.SwordsMan;

import scr.Model.Characters.DetectsColliders.BodyDetectsCollider;
import scr.Model.Characters.DetectsColliders.PositionDetectsCollider;
import scr.LogicalProcessing.Position.Transform;
import scr.Model.Map.StageModel;
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
 * 控制角色，
 * 记录控制
 * 网络传输输入
 */

public class Player implements ActionListener , IRender , IController {

    public SwordsMan swordsMan;
    public Transform transform;
    PlayerControl playerControl;
    PositionDetectsCollider pointCollider;
    BodyDetectsCollider bodyDetectsCollider;
    public StageModel stageModel;



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
        swordsMan = new SwordsMan(this);
        playerControl = new PlayerControl(j,this);

        pointCollider = new PositionDetectsCollider(transform.xPos,transform.yPos);
        bodyDetectsCollider = new BodyDetectsCollider(transform.xPos-10,transform.yPos-10,30,30,new Vector2D(0,0));
        transform.xPos = 10;
        transform.yPos = 430;


    }

    public void Update()
    {
        bodyDetectsCollider.updatePosition(transform);
        if(!pointCollider.obstacle(stageModel.Borders,this)){
            if(!swordsMan.property.states.equals(BaseStates.Injure)  && !swordsMan.property.states.equals(BaseStates.InAir)  && !swordsMan.property.states.equals(BaseStates.Throw))
           {
               playerControl.detect();
               playerControl.Command();
            }
        }
//        if(playerControl.input.isKeyDown(KeyEvent.VK_V))
//        {
//            if(swordsMan.property.states.equals(BaseStates.InAir) ||swordsMan.property.states.equals(BaseStates.Throw))
//            {
//                swordsMan.property.horizontal = new Vector2D(transform.xPos,swordsMan.property.horizontal.y);
//                swordsMan.getFsm().ChangeState(BaseStates.Throw);
//            }
//            else {
//                swordsMan.property.throwTimes = 1;
//                swordsMan.property.fallTimes = 1;
//                swordsMan.property.horizontal = new Vector2D(transform.xPos,transform.yPos);
//                swordsMan.property.initHorizontalLine = transform;
//                swordsMan.getFsm().ChangeState(BaseStates.Throw);
//            }
//
//        }
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
