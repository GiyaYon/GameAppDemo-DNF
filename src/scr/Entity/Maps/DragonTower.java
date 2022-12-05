package scr.Entity.Maps;

import scr.Controller.Collide.Colliders.BoxCollider;
import scr.Model.Characters.Transform;
import scr.Model.Characters.Vector2D;
import scr.Model.Map.MapModel;
import scr.Model.Map.Obscurer;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DragonTower extends MapModel {


    Obscurer o;
    public DragonTower(String FarName, String NearPath,int collideWidthX1,int collideWidthX2,int collideHeightY1,int collideHeightY2,JPanel panel) {
        super(FarName, NearPath);
        cameraWidth = 170;
        this.collideWidthX1 = collideWidthX1;
        this.collideWidthX2 = collideWidthX2;
        this.collideHeightY1 = collideHeightY1;
        this.collideHeightY2 = collideHeightY2;

        Borders = new ArrayList<>();
        BoxCollider mapTopBorder = new BoxCollider(0,300,1200,100,new Vector2D(0,1));
        BoxCollider mapBottomBorder = new BoxCollider(0,550,1200,100,new Vector2D(0,-1));
        BoxCollider mapLeftBorder = new BoxCollider(0,400,10,150,new Vector2D(1,0));
        BoxCollider mapRightBorder = new BoxCollider(700,400,10,150,new Vector2D(-1,0));
        Borders.add(mapTopBorder);
        Borders.add(mapBottomBorder);
        Borders.add(mapLeftBorder);
        Borders.add(mapRightBorder);

        o = new Obscurer(200,470,panel);
        obscurers = new ArrayList<>();
        obscurers.add(o);
        Borders.add(obscurers.get(0).testBoxCollider);


    }



    @Override
    public void mapRender(Graphics g, JPanel panel, Transform transform) {
        render(g,panel,transform);
    }


}
