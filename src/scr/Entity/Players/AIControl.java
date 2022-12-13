package scr.Entity.Players;

import scr.Entity.Characters.Swordman.SwordsmanCommand;
import scr.LogicalProcessing.Physics.Force;
import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.Robot.RStates.RobotStatesTable;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.Commands.AttackCommand;
import scr.Model.Characters.Commands.MoveCommand;
import scr.Model.Characters.Commands.NoneCommand;
import scr.Model.Characters.Forces.AttackEffect;
import scr.Model.Characters.Forces.AttackType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AIControl implements ActionListener {
    RobotPlayer robotPlayer;
    Timer timer,changeTimer;
    ChangeCommon changeCommon;
    String currentState;
    int attackTime;

    public AIControl(RobotPlayer robotPlayer) {
        this.robotPlayer = robotPlayer;
    }

    public void init()
    {
        timer = new Timer(50,this);
        timer.start();
        executeIdle();
        changeCommon = new ChangeCommon();
        changeTimer = new Timer(3000,changeCommon);
        changeTimer.start();
        currentState = RobotStatesTable.Idle;
    }
    public void executeIdle()
    {
        robotPlayer.iCommand = new NoneCommand(robotPlayer.actionCommands);
        robotPlayer.iCommand.Execute();
    }
    public void Update()
    {
        //攻击
        if(robotPlayer.attackRange.isIntersect(robotPlayer.attackRange.s1, robotPlayer.target.s1) && (int)System.currentTimeMillis() - attackTime > 1500)
        {
            if(!robotPlayer.property.states.equals(BaseStates.Throw)&&!robotPlayer.property.states.equals(BaseStates.InAir) &&!robotPlayer.property.states.equals(BaseStates.Injure))
            {
                if(robotPlayer.targetTransform!=null && Math.abs(robotPlayer.targetTransform.yPos - robotPlayer.transform.yPos) <10 )
                {
                    robotPlayer.property.states = RobotStatesTable.Attack;
                    robotPlayer.property.director = robotPlayer.transform.flipX;
                    robotPlayer.property.initHorizontalLine = robotPlayer.transform;
                    robotPlayer.property.attackType = new AttackType(new Vector2D(robotPlayer.property.director,0),10, AttackEffect.Light,new Force(robotPlayer.property.director,1,0));
                    robotPlayer.iCommand = new AttackCommand((SwordsmanCommand) robotPlayer.actionCommands,1);
                    robotPlayer.iCommand.Execute();
                    attackTime = (int)System.currentTimeMillis();
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(robotPlayer.property.states.equals(BaseStates.Death))return;
        if(robotPlayer.property.states.equals(BaseStates.Injure) || robotPlayer.property.states.equals(BaseStates.InAir)|| robotPlayer.property.states.equals(BaseStates.Throw)
        || robotPlayer.property.states.equals(RobotStatesTable.Attack))
        {
            return;
        }
        if(robotPlayer.a!= null)
        {
            if(currentState.equals(RobotStatesTable.Chase))
            {
                Vector2D v = new Vector2D( robotPlayer.a.get(robotPlayer.a.size()-2).x - robotPlayer.a.get(robotPlayer.a.size()-1).x , robotPlayer.a.get(robotPlayer.a.size()-2).y - robotPlayer.a.get(robotPlayer.a.size()-1).y );
                robotPlayer.iCommand = new MoveCommand(robotPlayer.actionCommands,v, robotPlayer.transform);
                robotPlayer.iCommand.Execute();
            }
        }
        if(currentState.equals(RobotStatesTable.Idle))
        {
            executeIdle();
        }
    }

    class ChangeCommon implements ActionListener
    {
        String[] list = new String[]{RobotStatesTable.Idle,RobotStatesTable.Chase};
        int index = 0;
        @Override
        public void actionPerformed(ActionEvent e) {

            currentState = list[index];
            index++;
            if(index >= list.length)
            {
                index = 0;
            }
        }
    }
}
