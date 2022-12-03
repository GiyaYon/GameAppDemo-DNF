package scr.Model.Map;

import scr.Controller.Collide.Colliders.BoxCollider;
import scr.Model.Characters.Transform;
import scr.Viewer.LoadImage.ResSprites;
import scr.Viewer.Renders.IRender;
import scr.Viewer.Renders.RenderSequenceManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public abstract class MapModel {

    //碰撞盒
    public ArrayList<BoxCollider> Borders;
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
        g.drawImage(far,0,0,far.getWidth(panel)*2,far.getHeight(panel)*2,panel);
        g.setColor(Color.green);
        //地图卷轴移动效果
        int leng = -200;
        for (int i = 0; i < size; i++) {

            //镜头移动左边界
            if(transform.xPos - cameraWidth < 0)
            {
                g.drawImage(maps[i], (170) + leng,0,panel);
                leng += maps[i].getWidth(panel);
                //镜头区域
                g.drawRect(170 , 400, 700+340, 150);
                //碰撞区域
                g.drawRect(0, 400, 700+340+170+100, 150);
            }
            //镜头移动右边界
            else if (350- transform.xPos < -340){

                g.drawImage(maps[i], -340 + leng,0,panel);
                leng += maps[i].getWidth(panel);
                //镜头区域
                g.drawRect(-340 , 400, 700+340, 150);
                //碰撞区域
                g.drawRect(-340, 400, 700+340+170+100, 150);
            }
            else
            {
                g.drawImage(maps[i], (350 - transform.xPos) + leng,0,panel);
                leng += maps[i].getWidth(panel);
                //镜头区域
                g.drawRect(340 - transform.xPos, 400, 700+340, 150);
                //碰撞区域
                g.drawRect(170 - transform.xPos, 400, 700+340+170+100, 150);
            }

        }



    }

    public abstract void mapRender(Graphics g, JPanel panel, Transform transform);




}
