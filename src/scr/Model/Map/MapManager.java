package scr.Model.Map;

import java.util.ArrayList;
import java.util.Collections;

public class MapManager {
    public ArrayList<StageModel> maps;
    public StageModel currentMap;
    public int index;
    public MapManager() {
        Collections.sort(maps);
    }

    public void ChangeMap(int i)
    {
        if(maps.get(i) !=null)
        {
            currentMap = maps.get(i);
        }
    }
    public void lastMap()
    {
        if(index-1 >=0)
        {
            index--;
            currentMap = maps.get(index);
        }
    }
    public void nextMap()
    {
        if(index+1 < maps.size())
        {
            index++;
            currentMap = maps.get(index);
        }
    }
}
