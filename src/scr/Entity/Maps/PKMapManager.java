package scr.Entity.Maps;

import scr.Model.Map.MapType;
import scr.Model.Map.StageModel;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class PKMapManager {
    public ArrayList<StageModel> maps;
    public StageModel currentMap;
    public int index;
    public PKMapManager(JPanel panel) throws IOException {

        PKForest forest = new PKForest(MapType.TRIPLE, "5","forest",0,770,360,550,panel);
        maps = new ArrayList<>();
        maps.add(forest);
        Collections.sort(maps);
        currentMap = maps.get(0);
    }

}
