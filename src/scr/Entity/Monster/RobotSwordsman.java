package scr.Entity.Monster;

import scr.Entity.Characters.Swordman.SwordsManAnimator;
import scr.Entity.Players.RobotPlayer;

import javax.swing.*;
import java.io.IOException;

public class RobotSwordsman extends RobotPlayer {

    public RobotSwordsman(JPanel j, String cIDName) throws IOException {
        super(j, cIDName);
        this.Start(new SwordsManAnimator(this),100,100);

    }
}
