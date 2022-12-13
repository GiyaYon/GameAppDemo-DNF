package scr.Entity.Monster;

import scr.Entity.Characters.giselle.GiselleAnimator;
import scr.Entity.Characters.youngscasa.ScasaAnimator;
import scr.Entity.Players.RobotPlayer;
import scr.Model.Characters.CharacterEvents.HitEvent;
import scr.Model.Characters.Commands.DeathCommand;
import scr.Model.Characters.Commands.InjureCommand;
import scr.Model.Characters.Forces.AttackEffect;
import scr.Model.Characters.Forces.AttackType;

import javax.swing.*;
import java.io.IOException;

public class RobotScasa extends RobotGiselle {

    public RobotScasa(JPanel j, String cIDName) throws IOException {
        super(j, cIDName);
        this.Start(new ScasaAnimator(this),150,100,120,100);
    }

}
