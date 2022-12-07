package scr.Entity.Maps;

import scr.Model.Map.MapType;
import scr.Model.Map.StageModel;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class MapManager {
    public ArrayList<StageModel> maps;
    public StageModel currentMap;
    public int index;
    public MapManager(JPanel panel) throws IOException {

        TimeGateBegin timeGateBegin = new TimeGateBegin(MapType.DOUBLE, "4","timeGateFinal",0,770,400,550,panel);
        Forest forest = new Forest(MapType.TRIPLE, "5","forest",0,770,360,550,panel);
        //timeGateBegin
        Desert desert = new Desert(MapType.TRIPLE,"5","desert",0,770,360,550,panel);
        maps = new ArrayList<>();
        maps.add(timeGateBegin);
        maps.add(forest);
        maps.add(desert);
        Collections.sort(maps);
        currentMap = maps.get(0);
    }

    public void lastMap()
    {
        if(index-1 >=0)
        {
            index--;

        }
    }
    public void nextMap()
    {
        if(index+1 < maps.size())
        {
            index++;
        }
    }
}
