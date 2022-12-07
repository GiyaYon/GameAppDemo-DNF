package scr;

import scr.Entity.Maps.DragonTower;
import scr.Entity.Maps.Forest;
import scr.Entity.Players.Player;
import scr.LogicalProcessing.Position.Transform;
import scr.IOProcessing.Camera.CameraMag;
import scr.IOProcessing.Renders.RenderSequenceManager;
import scr.Model.Map.MapType;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();
        frame.setLocation(400, 100);
        frame.setSize(800, 590);

        TestPanel panel = new TestPanel();
        frame.setContentPane(panel);

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
class TestPanel extends JPanel implements Runnable {

    //遮挡盒
    public RenderSequenceManager renderManager = new RenderSequenceManager();

    //----------------------主玩家
    Player player;
    //绑定主角玩家的Transform
    Transform transform;
    //摄像机
    CameraMag cameraMag;

    //---------------------双缓冲流
    public Image iBuffer;
    public Graphics gBuffer;



    //TODO 地图要扩展成地图池
    DragonTower dragonTower;
    Forest forest;

    public TestPanel() throws IOException {

        player = new Player(this);
        player.Start();
        transform = player.transform;


        dragonTower = new DragonTower(MapType.DOUBLE, "1","dragontowner",0,700,400,550,this);
        forest = new Forest(MapType.TRIPLE, "3","forest",0,700,400,550,this);
        forest.setMidYPos(0,2);
        //渲染检测
        renderManager.renderMethods.addAll(dragonTower.obscurers);
        renderManager.renderMethods.add(player);

//        //受击事件
//        player.hitManager.addHitListener(dragonTower.obscurers.get(0));

        //包围盒碰撞检测
//        for (var v : dragonTower.obscurers)
//        {
//            player.swordsMan.property.bdcs.add(v.bodyDetectsCollider);
//        }

        //阻挡物检测
        player.stageModel = forest;
        //player.mapModel = dragonTower;
        //摄像头
        cameraMag = new CameraMag();

        // 创建一个新线程，this就是实现了Runnable接口的实现类
        Thread t = new Thread(this);
        // 启动线程
        t.start();
        // 设定焦点在本面板并作为监听对象
        setFocusable(true);
        //addKeyListener(this);
    }

    @Override
    public void paint(Graphics g) {

        if(iBuffer == null)
        {
            iBuffer = createImage(getWidth(),getHeight());
            gBuffer = iBuffer.getGraphics();
        }
        g.clearRect(0, 0, getWidth(), getHeight());

        //摄像头更新
        cameraMag.cameraMoving(transform);
        //地图渲染
        forest.mapRender(g,this,new Transform(cameraMag.cameraMove.getMapMoving(),transform.yPos));
        //dragonTower.mapRender(g,this,new Transform(cameraMag.cameraMove.getMapMoving(),transform.yPos));
        //管理类渲染
        renderManager.render(g,this,transform,cameraMag);

        //修改后坐标点
        //g.setColor(Color.green);
        //g.fillOval(transform.xPos, transform.yPos, 10, 10);

        //显示文字信息
//        int y = 20;
//        for (String line : player.info.split(" "))
//        {
//            g.drawString(line,20,y);
//            y += 20;
//        }
//
//        if(transform!= null)
//        {
//            g.drawString("x="+ transform.xPos +" ,y=" +transform.yPos,200,20);
//            g.drawString("Camerax="+ cameraMag.cameraMove.getMapMoving(),200,40);
//        }
//        g.drawString(String.valueOf(player.swordsMan.property.states),200,60);
//        if(player.swordsMan.property.horizontal!= null)
//        {
//            g.drawString("ViewY:"+ player.swordsMan.property.horizontal.y,200,80);
//        }

    }


    @Override
    public void run() {
        long lastUpdate = System.currentTimeMillis();
        int fps = 30;
        while (true) {// 线程中的无限循环

            long interval = 1000/fps;
            long curr = System.currentTimeMillis();
            if(curr - lastUpdate < interval)
            {
                try {
                    Thread.sleep(interval -(curr - lastUpdate) );
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }else
            {
                lastUpdate = curr;


                player.Update();


                repaint();// 窗口重绘
            }


        }
    }



}
