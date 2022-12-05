package scr.Model.Map;

import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Transform;
import scr.IOProcessing.LoadImage.ResSprites;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public abstract class MapModel {

    //碰撞盒
    public ArrayList<BoxCollider> Borders;
    //
    public ArrayList<Obscurer> obscurers;
    //远景
    public Image far;
    //近景
    public Image[] maps;
    //地面
    public Image ground;
    int size;

    protected int cameraWidth,
            cameraWidth2;
    public int collideWidthX1,collideWidthX2,collideHeightY1,collideHeightY2;

    public MapModel(String FarName,String NearPath)
    {
        size = ResSprites.getFilesCount(new File("src\\res\\bg\\"+NearPath),"png");
        maps = new Image[size];
        for (int i = 0; i < size; i++) {
            maps[i] = Toolkit.getDefaultToolkit().getImage("src\\res\\bg\\"+NearPath+"\\" +i+ ".png");
        }
        far = Toolkit.getDefaultToolkit().getImage("src\\res\\far\\" + FarName + ".png");
    }


    public void render(Graphics g, JPanel panel, Transform transform)
    {

        //先渲染背景图
        g.drawImage(far,0- transform.xPos/2,0,far.getWidth(panel)*2,far.getHeight(panel)*2,panel);
        g.setColor(Color.green);
        //地图卷轴移动效果
        int leng = 0;
        for (int i = 0; i < size; i++) {
            g.drawImage(maps[i], leng - transform.xPos,0,panel);
            leng += maps[i].getWidth(panel);

        }



    }

    public abstract void mapRender(Graphics g, JPanel panel, Transform transform);




}
