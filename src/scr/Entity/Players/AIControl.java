package scr.Entity.Players;

import scr.LogicalProcessing.Position.Vector2D;
import scr.LogicalProcessing.Robot.RStates.RobotStatesTable;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.Commands.MoveCommand;
import scr.Model.Characters.Commands.NoneCommand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AIControl implements ActionListener {
    public static AIControl instance;
    RobotPlayer c;
    Timer timer,changeTimer;
    ChangeCommon changeCommon;
    String currentState;

    public AIControl(RobotPlayer c) {
        this.c = c;
        instance = this;
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
        c.c = new NoneCommand(c.actionCommands);
        c.c.Execute();
    }
    public void Update()
    {

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(c.property.states.equals(BaseStates.Injure) || c.property.states.equals(BaseStates.InAir)||c.property.states.equals(BaseStates.Throw)
        ||c.property.states.equals(RobotStatesTable.Attack))
        {
            return;
        }
        if(c.a!= null)
        {
            if(currentState.equals(RobotStatesTable.Chase))
            {
                Vector2D v = new Vector2D( c.a.get(c.a.size()-2).x - c.a.get(c.a.size()-1).x , c.a.get(c.a.size()-2).y -c.a.get(c.a.size()-1).y );
                c.c = new MoveCommand(c.actionCommands,v,c.transform);
                c.c.Execute();
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

            AIControl.instance.currentState = list[index];
            index++;
            if(index >= list.length)
            {
                index = 0;
            }
        }
    }
}
