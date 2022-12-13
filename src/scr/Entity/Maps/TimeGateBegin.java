package scr.Entity.Maps;

import scr.Entity.Monster.RobotGoblin;
import scr.Entity.Monster.RobotSwordsman;
import scr.Entity.Players.RobotPlayer;
import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Map.StageModel;
import scr.Model.Map.Obscurer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class TimeGateBegin extends StageModel {

    JPanel p;
    Obscurer o;
    //--测试玩家

    public TimeGateBegin(int type, String FarName, String NearPath, int collideWidthX1, int collideWidthX2, int collideHeightY1, int collideHeightY2, JPanel panel) throws IOException {
        super(type,FarName,NearPath,collideWidthX1,collideWidthX2,collideHeightY1,collideHeightY2);
        monsters = new ArrayList<>();
        copyMidOneMid(1,2);
        copyMidOneMid(1,2);
        copyMidOneMid(1,2);
        copyMidOneMid(1,2);
        setFarS(1.2f);
        setMidYPos(370,1);
        BoxCollider mapRightBorder = new BoxCollider(collideWidthX2-10,collideHeightY1,10,150,new Vector2D(0,0));
        Borders.add(mapRightBorder);
        mapIndex = 0;
        p = panel;

        o = new Obscurer(200,470,"TimeGateBeginDragonHead02");
        obscurers.add(o);
        Borders.add(obscurers.get(0).testBoxCollider);


        RobotPlayer robotPlayer = new RobotSwordsman(p,"Bot");
        robotPlayer.setTransform(200,440);
        monsters.add(robotPlayer);


        RobotPlayer robotPlayer2 = new RobotGoblin(p,"Bot2");
        robotPlayer2.setTransform(210,500);
        monsters.add(robotPlayer2);

    }


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
}
