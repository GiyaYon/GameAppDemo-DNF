package scr.IOProcessing.Renders;

import scr.Entity.Players.Player;
import scr.Model.Characters.Position.Transform;
import scr.IOProcessing.Camera.CameraMag;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class RenderSequenceManager{

    public ArrayList<IRender> renderMethods = new ArrayList<>();

    public void render(Graphics g, JPanel panel, Transform transform, CameraMag cameraMag)
    {
        if(renderMethods.size()!=0)
        {
            Collections.sort(renderMethods);
        }
        for (IRender r : renderMethods)
        {
            if(!r.getClass().isAssignableFrom(Player.class))
            {
                r.render(g,panel,new Transform(cameraMag.cameraMove.getMapMoving(),transform.yPos));
            }
            else {
                r.render(g,panel,transform);
            }

        }
    }


}
