package scr.Entity.Maps;

import scr.Entity.Monster.RobotScasa;
import scr.Entity.Players.RobotPlayer;
import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.Map.Obscurer;
import scr.Model.Map.StageModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class DragonTower extends StageModel {

    Obscurer o;
    public DragonTower(int type, String FarName, String NearPath, int collideWidthX1, int collideWidthX2, int collideHeightY1, int collideHeightY2, JPanel p) throws IOException {
        super(type,FarName,NearPath,collideWidthX1,collideWidthX2,collideHeightY1,collideHeightY2,p);
        monsters = new ArrayList<>();
//        BoxCollider mapRightBorder = new BoxCollider(collideWidthX2-10,collideHeightY1,10,150,new Vector2D(0,0));
//        Borders.add(mapRightBorder);
        BoxCollider mapRightBorder = new BoxCollider(collideWidthX2-10,collideHeightY1,10,150,new Vector2D(0,0));
        Borders.add(mapRightBorder);
        mapIndex = 2;

        o = new Obscurer(200,470,"DragonTowerDragonHead01");
        obscurers.add(o);
        Borders.add(obscurers.get(0).testBoxCollider);

        RobotPlayer robotPlayer2 = new RobotScasa(p,"Bot2");
        robotPlayer2.setTransform(210,500);
        monsters.add(robotPlayer2);

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
