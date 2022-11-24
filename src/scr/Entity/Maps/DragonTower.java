package scr.Entity.Maps;

import scr.Model.Characters.Transform;
import scr.Model.Map.MapModel;

import javax.swing.*;
import java.awt.*;

public class DragonTower extends MapModel {
    public DragonTower(String FarName, String NearPath,int collideWidthX1,int collideWidthX2,int collideHeightY1,int collideHeightY2) {
        super(FarName, NearPath);
        cameraWidth = 170;
        this.collideWidthX1 = collideWidthX1;
        this.collideWidthX2 = collideWidthX2;
        this.collideHeightY1 = collideHeightY1;
        this.collideHeightY2 = collideHeightY2;
    }

    @Override
    public void mapRender(Graphics g, JPanel panel, Transform transform) {
        render(g,panel,transform);

    }


}
