package scr.Entity.Maps;

import scr.LogicalProcessing.Position.Transform;
import scr.Model.Map.StageModel;
import scr.Model.Map.Obscurer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DragonTower extends StageModel {

    JPanel p;
    Obscurer o;
    public DragonTower(int type, String FarName, String NearPath,int collideWidthX1,int collideWidthX2,int collideHeightY1,int collideHeightY2,JPanel panel) {
        super(type,FarName,NearPath,collideWidthX1,collideWidthX2,collideHeightY1,collideHeightY2);

        mapIndex = 0;
        p = panel;


        o = new Obscurer(200,470,panel);
        obscurers = new ArrayList<>();
        obscurers.add(o);
        Borders.add(obscurers.get(0).testBoxCollider);


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
