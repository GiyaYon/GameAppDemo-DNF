package scr.Entity.Monster;

import scr.Entity.Characters.goblin.GoblinAnimator;
import scr.Entity.Players.RobotPlayer;

import javax.swing.*;
import java.io.IOException;

public class RobotGoblin extends RobotPlayer {
    public RobotGoblin(JPanel j, String cIDName) throws IOException {
        super(j, cIDName);
        this.Start(new GoblinAnimator(this),50,50);
    }
}
