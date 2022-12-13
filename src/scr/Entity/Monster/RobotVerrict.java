package scr.Entity.Monster;

import scr.Entity.Characters.Swordman.SwordsManAnimator;
import scr.Entity.Characters.Verrict.VerrictAnimator;
import scr.Entity.Players.RobotPlayer;

import javax.swing.*;
import java.io.IOException;

public class RobotVerrict extends RobotPlayer {

    public RobotVerrict(JPanel j, String cIDName) throws IOException {
        super(j, cIDName);
        this.Start(new VerrictAnimator(this),100,100);
    }
}
