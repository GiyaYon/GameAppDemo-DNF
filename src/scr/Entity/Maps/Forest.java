package scr.Entity.Maps;

import scr.Entity.Monster.RobotGoblin;
import scr.Entity.Monster.RobotSwordsman;
import scr.Entity.Players.RobotPlayer;
import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Map.StageModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Forest extends StageModel {

    public Forest(int type,String FarName, String NearPath, int collideWidthX1, int collideWidthX2, int collideHeightY1, int collideHeightY2,JPanel p) throws IOException {
        super(type,FarName, NearPath, collideWidthX1, collideWidthX2, collideHeightY1, collideHeightY2,p);
        monsters = new ArrayList<>();
        copyMidOneMid(0);
        copyMidOneMid(0);
        copyTitleOneMid(0);
        copyTitleOneMid(0);
        copyTitleOneMid(0);
        copyTitleOneMid(0);
        copyTitleOneMid(0);
        copyTitleOneMid(0);
        setMidYPos(60,1f);
        setTitleYPos(340);
        mapIndex = 0;
        BoxCollider mapRightBorder = new BoxCollider(collideWidthX2-10,collideHeightY1,10,150,new Vector2D(0,0));
        Borders.add(mapRightBorder);


//        RobotPlayer robotPlayer = new RobotSwordsman(p,"Bot");
//        robotPlayer.setTransform(200,440);
//        monsters.add(robotPlayer);
        for (int i = 0; i < 2; i++) {
            RobotPlayer robotPlayer2 = new RobotGoblin(p,"Bot" + i);
            robotPlayer2.setTransform(100 + i * 100,500);
            monsters.add(robotPlayer2);
        }
    }

    @Override
    public void mapRender(Graphics g, JPanel panel, Transform transform) {
        render(g,panel,transform);
    }

    @Override
    public int compareTo(Object o) {
        StageModel s = (StageModel) o;
        if(mapIndex>s.mapIndex){
            return 1;
        }else if(mapIndex<s.mapIndex){
            return -1;
        }
        return 0;
    }

    @Override
    public void Update()
    {
        for (var monster : monsters)
        {
            monster.Update();
        }
    }

    @Override
    public void Init() {
        for (var monster : monsters)
        {
            monster.target = tatget;
            monster.property.bdcs.add(tatget);
            monster.stageModel = this;
        }
    }
}
