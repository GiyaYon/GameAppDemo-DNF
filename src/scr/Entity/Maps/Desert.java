package scr.Entity.Maps;

import scr.LogicalProcessing.Position.Transform;
import scr.Model.Map.StageModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Desert extends StageModel {

    public Desert(int type, String FarName, String NearPath, int collideWidthX1, int collideWidthX2, int collideHeightY1, int collideHeightY2, JPanel panel) throws IOException {
        super(type,FarName, NearPath, collideWidthX1, collideWidthX2, collideHeightY1, collideHeightY2);
//        copyMidOneMid(0);
//        copyMidOneMid(0);
        copyMidOneMid(0);
        copyMidOneMid(0);
        copyMidOneMid(0);
        copyMidOneMid(0);
        copyMidOneMid(0);
        copyMidOneMid(0);
        setMidYPos(260,1.4f);
        setTitleYPos(0);
        mapIndex = 2;


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
