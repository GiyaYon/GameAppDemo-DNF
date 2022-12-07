package scr.Model.Map;

import scr.IOProcessing.LoadImage.ResSprites;
import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Transform;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 加载素材，根据素材类型创建地图，边界
 */
public abstract class BaseMapModel {

    //碰撞盒
    public ArrayList<BoxCollider> Borders;
    //敌人或可破坏的物品
    public ArrayList<Obscurer> obscurers = new ArrayList<>();

    //------------三层地图：远景+近景+地面
    public Image[] ground;
    String farUrl ,midUrl;
    //------------二层地图：远景+近景
    public Image[] midMaps;
    //------------一张地图：远景
    public Image far;

    public int setMidY = 0;
    public float setMidS = 1;
    //读取多文件的数量
    int midSize,titleSize;
    float farSize = 1;
    int type;

    int titleY=100;

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
    public void setMidYPos(int y,float size)
    {
        setMidY = y;
        setMidS = size;
    }
    public void setFarS(float size)
    {
        farSize = size;
    }
    public void setTitleYPos(int y)
    {
        titleY = y;
    }
    public void copyMidOneMid(int cpIndex)
    {
        midSize++;
        Image[] mp = new Image[midSize];
        System.arraycopy(midMaps, 0, mp, 0, midMaps.length);
        //mp[midSize-1] =  imageMisro(midMaps[cpIndex],1,midUrl);
        mp[midSize-1] =  midMaps[cpIndex];
        midMaps = mp;
    }
    public void copyMidOneMid(int cpIndex,int insertIndex)
    {
        Image copy = midMaps[cpIndex];
        //扩容
        midSize++;
        Image[] mp = new Image[midSize];
        //复制插入之前面的数组
        for(int j = 0; j< mp.length;j++)
        {
            if(j == insertIndex)break;
            mp[j] = midMaps[j];
        }
        //插入想要复制的
        mp[insertIndex] = copy;
        //复制插入之后面的数组
        for(int j = insertIndex; j< mp.length;j++)
        {
            mp[j] = midMaps[j-1];
        }
        //System.arraycopy(i, 0, mp, 0, i.length);
        midMaps = mp;
    }

    public void insertTitleOneMid(int cpIndex,int insertIndex)
    {
        Image copy = ground[cpIndex];
        //扩容
        titleSize++;
        Image[] mp = new Image[titleSize];
        //复制插入之前面的数组
        for(int j = 0; j< mp.length;j++)
        {
            if(j == insertIndex)break;
            mp[j] = ground[j];
        }
        //插入想要复制的
        mp[insertIndex] = copy;
        //复制插入之后面的数组
        for(int j = insertIndex ; j< mp.length;j++)
        {
            mp[j] = ground[j-1];
        }
        //System.arraycopy(i, 0, mp, 0, i.length);
        ground = mp;
    }
    public void copyTitleOneMid(int cpIndex)
    {
        titleSize++;
        Image[] mp = new Image[titleSize];
        System.arraycopy(ground, 0, mp, 0, ground.length);
        mp[titleSize-1] = ground[cpIndex];
        ground = mp;
    }
    public BaseMapModel(int type,String FarName,String NearPath) throws IOException {
        this.type = type;
        switch (type)
        {
            case MapType.SINGLE :
                assert false;
                far = Toolkit.getDefaultToolkit().getImage("src\\res\\far\\" + FarName + ".png");
                break;
            case MapType.DOUBLE:
                midSize = getMidSize(NearPath);
                midMaps = new Image[midSize];
                for (int i = 0; i < midSize; i++) {
                    midMaps[i] = Toolkit.getDefaultToolkit().getImage("src\\res\\bg\\"+NearPath+"\\" +i+ ".png");
                    //midMaps[i] = ImageIO.read(new File("src\\res\\bg\\"+NearPath+"\\" +i+ ".png"));
                }
                far = Toolkit.getDefaultToolkit().getImage("src\\res\\far\\" + FarName + ".png");
                break;
            case MapType.TRIPLE:
                farUrl = "src\\res\\far\\" + FarName + ".png";
                midUrl = "src\\res\\bg\\"+NearPath+"\\" +0+ ".png" ;
                titleSize = getTitleSize(NearPath);
                ground = new Image[titleSize];
                for (int i = 0; i < titleSize; i++) {
                    ground[i] = Toolkit.getDefaultToolkit().getImage("src\\res\\bg\\"+NearPath+"\\title\\" +i+ ".png");
                }
                midSize = getMidSize(NearPath);
                midMaps = new Image[midSize];
                for (int i = 0; i < midSize; i++) {
                    midMaps[i] = Toolkit.getDefaultToolkit().getImage("src\\res\\bg\\"+NearPath+"\\" +i+ ".png");
                    //midMaps[i] = ImageIO.read(new File("src\\res\\bg\\"+NearPath+"\\" +i+ ".png"));

                }
                far = Toolkit.getDefaultToolkit().getImage("src\\res\\far\\" + FarName + ".png");
                break;
        }
    }

    public void render(Graphics g, JPanel panel, Transform transform)
    {
        //先渲染背景图
        g.drawImage(far,-transform.xPos/2,0,(int)(far.getWidth(panel)*farSize),(int)(far.getHeight(panel)*farSize),panel);
        g.drawImage(far,(int)(far.getWidth(panel)*farSize)-transform.xPos/2,0,(int)(far.getWidth(panel)*farSize),(int)(far.getHeight(panel)*farSize),panel);

        //再渲染近景图
        int leng = 0;
        for (int i = 0; i < midSize; i++) {
            g.drawImage(midMaps[i], leng - transform.xPos,setMidY,(int)(midMaps[i].getWidth(panel)*setMidS),(int)(midMaps[i].getHeight(panel)*setMidS),panel);
            leng += (int)(midMaps[i].getWidth(panel)*setMidS);

        }
        //最后渲染地面
        int gleng = 0;
        for (int i = 0; i < titleSize; i++) {
            g.drawImage(ground[i], gleng - transform.xPos,titleY,panel);
            gleng += ground[i].getWidth(panel);
        }

    }
    public abstract void mapRender(Graphics g, JPanel panel, Transform transform);

    public static Image imageMisro(int type,String url)
    {
        try
        {
            //用到了自己写的方法
            BufferedImage bufferedimage = ImageIO.read(new File(url));

            int w = bufferedimage.getWidth();
            int h = bufferedimage.getHeight();

            int[][] datas = new int[w][h];
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    datas[j][i] = bufferedimage.getRGB(j, i);
                }
            }
            int[][] tmps = new int[w][h];
            if (type == 0) {
                for (int i = 0, a = h - 1; i < h; i++, a--) {
                    for (int j = 0; j < w; j++) {
                        tmps[j][a] = datas[j][i];
                    }
                }
            } else if (type == 1) {
                for (int i = 0; i < h; i++) {
                    for (int j = 0, b = w - 1; j < w; j++, b--) {
                        tmps[b][i] = datas[j][i];
                    }
                }
            }
            for (int i = 0; i < h; i++){
                for (int j = 0; j<w ;j++){
                    bufferedimage.setRGB(j, i, tmps[j][i]);
                }
            }

            Image newImage = (Image)bufferedimage;
            return newImage;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
