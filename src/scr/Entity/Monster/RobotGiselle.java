package scr.Entity.Monster;

import scr.Entity.Characters.Verrict.VerrictAnimator;
import scr.Entity.Characters.giselle.GiselleAnimator;
import scr.Entity.Players.RobotPlayer;
import scr.Model.Characters.CharacterEvents.HitEvent;
import scr.Model.Characters.CharacterState.BaseStates;
import scr.Model.Characters.Commands.DeathCommand;
import scr.Model.Characters.Commands.InjureCommand;
import scr.Model.Characters.Commands.ThrowFlyCommand;
import scr.Model.Characters.Forces.AttackEffect;
import scr.Model.Characters.Forces.AttackType;

import javax.swing.*;
import java.io.IOException;

public class RobotGiselle extends RobotPlayer {

    public RobotGiselle(JPanel j, String cIDName) throws IOException {
        super(j, cIDName);
        this.Start(new GiselleAnimator(this),300,100,100,100);
    }

    @Override
    public void GameEventInvoke(HitEvent event) {
        AttackType attackType = event.getPlayValue().property.attackType;
        if(attackType!=null)
        {
            if(attackType.effect.equals(AttackEffect.Light))
            {
                cProperty.hp -= attackType.attackValue;
                iCommand = new InjureCommand(actionCommands, attackType);
                iCommand.Execute();
            } else if (attackType.effect.equals(AttackEffect.Heavy)) {
                cProperty.hp -= attackType.attackValue * 1.5f;
                iCommand = new InjureCommand(actionCommands, attackType);
                iCommand.Execute();
            }

            if(cProperty.hp - attackType.attackValue < 0)
            {
                iCommand = new DeathCommand(actionCommands);
                iCommand.Execute();
            }
        }
    }
}
