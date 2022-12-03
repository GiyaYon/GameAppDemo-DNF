package scr.Viewer.Renders;

import scr.Model.Characters.Transform;
import scr.Model.Map.Obscurer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class RenderSequenceManager{

    public ArrayList<IRender> renderMethods = new ArrayList<>();

    public void render(Graphics g, JPanel panel, Transform transform)
    {
        if(renderMethods.size()!=0)
        {
            Collections.sort(renderMethods);
        }
        for (IRender r : renderMethods)
        {
            r.render(g,panel,transform);
        }
    }


}
