package scr;

import scr.Entity.Maps.MapManager;
import scr.Entity.Players.Player;
import scr.LogicalProcessing.Position.Transform;
import scr.IOProcessing.Camera.CameraMag;
import scr.IOProcessing.Renders.RenderSequenceManager;
import scr.Model.Map.TransportEvent;
import scr.Model.Map.TransportListener;

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
class TestPanel extends JPanel implements Runnable , TransportListener {

    //渲染顺序
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
    //---------------------地图管理
    MapManager mapManager;


    public TestPanel() throws IOException {

        player = new Player(this);
        player.Start();
        transform = player.transform;


        mapManager = new MapManager(this);
//-------------------------地图相关----------------------------//
        //渲染检测
        if(mapManager.currentMap.obscurers.size()>0)
        {
            renderManager.renderMethods.addAll(mapManager.currentMap.obscurers);
        }
        //包围盒碰撞检测
        for (var v : mapManager.currentMap.obscurers)
        {
            player.property.bdcs.add(v.bodyDetectsCollider);
        }
        //阻挡物检测
        player.stageModel = mapManager.currentMap;
//--------------------------------------------------------//


        //受击事件
        //player.hitManager.addHitListener(dragonTower.obscurers.get(0));
        //渲染玩家
        renderManager.renderMethods.add(player);

        //传送
        player.pointCollider.transportEvent.addHitListener(this);
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

    public void changeMap()
    {
        mapManager.currentMap = mapManager.maps.get(mapManager.index);

        renderManager.renderMethods.clear();
        //渲染检测
        if(mapManager.currentMap.obscurers.size()>0)
        {
            renderManager.renderMethods.addAll(mapManager.currentMap.obscurers);
        }
        renderManager.renderMethods.add(player);

        player.property.bdcs.clear();
        //包围盒碰撞检测
        for (var v : mapManager.currentMap.obscurers)
        {
            player.property.bdcs.add(v.bodyDetectsCollider);
        }
        //阻挡物检测
        player.stageModel = mapManager.currentMap;
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
        mapManager.currentMap.mapRender(g,this,new Transform(cameraMag.cameraMove.getMapMoving(),transform.yPos));
        //管理类渲染
        renderManager.render(g,this,transform,cameraMag);

        //修改后坐标点
        g.setColor(Color.green);
//        g.fillOval(transform.xPos, transform.yPos, 10, 10);

        //显示文字信息
//        int y = 20;
//        for (String line : player.info.split(" "))
//        {
//            g.drawString(line,20,y);
//            y += 20;
//        }
//
        if(transform!= null)
        {
            g.drawString("x="+ transform.xPos +" ,y=" +transform.yPos,200,20);
            g.drawString("Camerax="+ cameraMag.cameraMove.getMapMoving(),200,40);
        }
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
//                if(player.playerControl.input.isKeyDown(KeyEvent.VK_V))
//              {
//                  mapManager.nextMap();
//                  changeMap();
//              }
//                if(player.playerControl.input.isKeyDown(KeyEvent.VK_B))
//                {
//                    mapManager.lastMap();
//                    changeMap();
//                }

                repaint();// 窗口重绘
            }


        }
    }

    @Override
    public void GameEventInvoke(TransportEvent event) {
        mapManager.nextMap();
        changeMap();
    }
}
