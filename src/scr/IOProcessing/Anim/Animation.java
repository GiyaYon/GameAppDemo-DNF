package scr.IOProcessing.Anim;

import scr.LogicalProcessing.Position.Transform;
import scr.IOProcessing.LoadImage.ImageMerge;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

public class Animation extends Transform {


    public int xPos = 0, yPos = 0;

    public BufferedImage iBuffer;
    public Graphics2D gBuffer;

    // 当前帧的ID，也就是第几张图片
    private int playID = 0;
    // Sprite类的图片数组
    ImageMerge imageMerge;
    int index =0 ;
    public int size = 0;
    String[] l,l2;

    public boolean isRecycle;
    public boolean isFinish = false;
    public Animation(ImageMerge i,boolean isRecycle)
    {
        imageMerge = i;
        this.isRecycle = isRecycle;
        // 加载图片
        size = imageMerge.images.length;
        index =  playID;
        l = imageMerge.positions.get(index).split(": ");
        l2 = l[1].split(", ");
    }

    /* 初始化坐标 */
    public void init(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    /* 设置坐标 */
    public void set(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void flip(int flip)
    {
        flipX = flip;
    }
    public void drawSprite(Graphics g, JPanel panel)
    {
        //新建一个背景图
        iBuffer = new BufferedImage(500, 500,BufferedImage.TYPE_INT_RGB);
        //新建画笔
        gBuffer = iBuffer.createGraphics();
        //让背景图透明
        iBuffer = gBuffer.getDeviceConfiguration().createCompatibleImage(500, 500, Transparency.TRANSLUCENT);
        //释放旧画笔
        gBuffer.dispose();
        //使用新透明背景的新画笔
        gBuffer = iBuffer.createGraphics();

        if(flipX == FLIP_LEFT)
        {
            AffineTransform transform = new AffineTransform(-1,0,0,1,iBuffer.getWidth()-35,0);
            gBuffer.setTransform(transform);
        }

        //在缓冲把角色图片填上去,并确定好提供的贴图中心点坐标
        gBuffer.drawImage(imageMerge.images[playID],Integer.parseInt(l2[0]),Integer.parseInt(l2[1]),panel);


        //gBuffer.drawImage(iBuffer, 0, 0, iBuffer.getWidth(), iBuffer.getHeight(), iBuffer.getWidth() -30, 0, 0-30, iBuffer.getHeight(), panel);
        //切换画布的锚点，使背景图跟画布的坐标统一，方便后续的操作
        g.translate(-225,-330);

        //渲染
        g.drawImage(iBuffer, xPos, yPos, (ImageObserver) panel);
        //画完切换回去
        g.translate(+225,+330);
    }

    public void drawSprite(Graphics g, JPanel panel,int w, int h)
    {
        //新建一个背景图
        iBuffer = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
        //新建画笔
        gBuffer = iBuffer.createGraphics();
        //让背景图透明
        iBuffer = gBuffer.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
        //释放旧画笔
        gBuffer.dispose();
        //使用新透明背景的新画笔
        gBuffer = iBuffer.createGraphics();

        if(flipX == FLIP_LEFT)
        {
            AffineTransform transform = new AffineTransform(-1,0,0,1,iBuffer.getWidth()-35,0);
            gBuffer.setTransform(transform);
        }

        //在缓冲把角色图片填上去,并确定好提供的贴图中心点坐标
        gBuffer.drawImage(imageMerge.images[playID],Integer.parseInt(l2[0]),Integer.parseInt(l2[1]),panel);


        //gBuffer.drawImage(iBuffer, 0, 0, iBuffer.getWidth(), iBuffer.getHeight(), iBuffer.getWidth() -30, 0, 0-30, iBuffer.getHeight(), panel);
        //切换画布的锚点，使背景图跟画布的坐标统一，方便后续的操作
        g.translate(-100,-150);

        //渲染
        g.drawImage(iBuffer, xPos, yPos, (ImageObserver) panel);
        //画完切换回去
        g.translate(+100,+150);
    }
    public void resetFrame()
    {
        isFinish = false;
        playID = 0;
        index =  playID;
        l = imageMerge.positions.get(index).split(": ");
        l2 = l[1].split(", ");

    }
    public void toNextFrame()
    {
        if(!isRecycle && !isFinish)
        {
            // 下一帧图像
            playID++;
            if (playID == (size -1) ) {
                isFinish = true;
            }
            //刷新下一个中心点坐标
            index =  playID;
            l = imageMerge.positions.get(index).split(": ");
            l2 = l[1].split(", ");
        }
        if(isRecycle){
            // 下一帧图像
            playID++;
            if (playID == (size -1) ) {
                // 图像放完后，又从第一帧开始
                playID = 0;
                index =  playID;
                l = imageMerge.positions.get(index).split(": ");
            }
            //刷新下一个中心点坐标
            index =  playID;
            l = imageMerge.positions.get(index).split(": ");
            l2 = l[1].split(", ");
        }
    }


}
