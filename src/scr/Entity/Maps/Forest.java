package scr.Entity.Maps;

import scr.LogicalProcessing.Position.Transform;
import scr.Model.Map.StageModel;

import javax.swing.*;
import java.awt.*;

public class Forest extends StageModel {


    public Forest(int type,String FarName, String NearPath, int collideWidthX1, int collideWidthX2, int collideHeightY1, int collideHeightY2, JPanel panel) {
        super(type,FarName, NearPath, collideWidthX1, collideWidthX2, collideHeightY1, collideHeightY2);
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
