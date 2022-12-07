package scr.Model.Map;

import scr.IOProcessing.LoadImage.ResSprites;
import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Transform;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

/**
 * 加载素材，根据素材类型创建地图，边界
 */
public abstract class BaseMapModel {

    //碰撞盒
    public ArrayList<BoxCollider> Borders;
    //敌人或可破坏的物品
    public ArrayList<Obscurer> obscurers;

    //------------三层地图：远景+近景+地面
    public Image[] ground;
    //------------二层地图：远景+近景
    public Image[] midMaps;
    //------------一张地图：远景
    public Image far;

    public int setMidY,setMidS = 0;
    //读取多文件的数量
    int midSize,titleSize;

    public int getTitleSize(String NearPath)
    {
        int s=0;
        s = ResSprites.getFilesCount(new File("src\\res\\bg\\"+NearPath+"\\title"),"png");
        return s;
    }
    public int getMidSize(String NearPath)
    {
        int s=0;
        s = ResSprites.getFilesCount(new File("src\\res\\bg\\"+NearPath),"png");
        return s;
    }
    public void setMidYPos(int y,int size)
    {
        setMidY = y;
        setMidS = size;
    }
    public BaseMapModel(int type,String FarName,String NearPath) {
        switch (type)
        {
            case MapType.SINGLE :
                far = Toolkit.getDefaultToolkit().getImage("src\\res\\far\\" + FarName + ".png");
                break;
            case MapType.DOUBLE:
                midSize = getMidSize(NearPath);
                midMaps = new Image[midSize];
                for (int i = 0; i < midSize; i++) {
                    midMaps[i] = Toolkit.getDefaultToolkit().getImage("src\\res\\bg\\"+NearPath+"\\" +i+ ".png");
                }
                far = Toolkit.getDefaultToolkit().getImage("src\\res\\far\\" + FarName + ".png");
                break;
            case MapType.TRIPLE:
                titleSize = getTitleSize(NearPath);
                ground = new Image[titleSize];
                for (int i = 0; i < titleSize; i++) {
                    ground[i] = Toolkit.getDefaultToolkit().getImage("src\\res\\bg\\"+NearPath+"\\title\\" +i+ ".png");
                }
                midSize = getMidSize(NearPath);
                midMaps = new Image[midSize];
                for (int i = 0; i < midSize; i++) {
                    midMaps[i] = Toolkit.getDefaultToolkit().getImage("src\\res\\bg\\"+NearPath+"\\" +i+ ".png");
                }
                far = Toolkit.getDefaultToolkit().getImage("src\\res\\far\\" + FarName + ".png");
                break;
        }
    }

    public void render(Graphics g, JPanel panel, Transform transform)
    {

        //先渲染背景图
        g.drawImage(far,-transform.xPos/2,0,far.getWidth(panel)*2,far.getHeight(panel)*2,panel);

        //再渲染近景图
        int leng = 0;
        for (int i = 0; i < midSize; i++) {
            g.drawImage(midMaps[i], leng - transform.xPos,setMidY,midMaps[i].getWidth(panel)*2,midMaps[i].getHeight(panel)*2,panel);
            leng += midMaps[i].getWidth(panel);

        }
        //最后渲染地面
        int gleng = 0;
        for (int i = 0; i < titleSize; i++) {
            g.drawImage(ground[i], gleng - transform.xPos,75,panel);//TODO
            gleng += ground[i].getWidth(panel);
        }

    }
    public abstract void mapRender(Graphics g, JPanel panel, Transform transform);
}
