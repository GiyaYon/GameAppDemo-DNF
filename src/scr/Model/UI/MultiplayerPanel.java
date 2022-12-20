package scr.Model.UI;

import scr.Entity.Maps.MapManager;
import scr.Entity.Maps.PKMapManager;
import scr.Entity.Players.Player;
import scr.IOProcessing.Camera.CameraMag;
import scr.IOProcessing.Renders.RenderSequenceManager;
import scr.LogicalProcessing.Position.Transform;
import scr.Model.Map.TransportEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MultiplayerPanel extends JPanel implements Runnable{

    //渲染顺序
    public RenderSequenceManager renderManager = new RenderSequenceManager();
    //----------------------主玩家
    Player player;
    //绑定主角玩家的Transform
    Transform playerMaintransform;
    //摄像机
    CameraMag cameraMag;

    //---------------------双缓冲流
    public Image iBuffer;
    public Graphics gBuffer;
    //---------------------地图管理
    PKMapManager mapManager;


    public MultiplayerPanel() throws IOException {

        player = new Player(this, "Miren13");
        player.Start();
        player.startNetWorkConnect();

        playerMaintransform = player.transform;
        mapManager = new PKMapManager(this);

//-------------------------地图相关----------------------------//
        mapManager.currentMap.tatget = player.bodyDetectsCollider;
        mapManager.currentMap.Init();
        //渲染检测
        if (mapManager.currentMap.obscurers.size() > 0) {
            renderManager.renderMethods.addAll(mapManager.currentMap.obscurers);
        }
        for (var character : mapManager.currentMap.monsters) {
            renderManager.renderMethods.add(character);
            player.property.bdcs.add(character.bodyDetectsCollider);
        }
        //包围盒碰撞检测
        for (var v : mapManager.currentMap.obscurers) {
            player.property.bdcs.add(v.bodyDetectsCollider);
        }
        //阻挡物检测
        player.stageModel = mapManager.currentMap;
//--------------------------------------------------------//
        //渲染玩家
        renderManager.renderMethods.add(player);
        //摄像头
        cameraMag = new CameraMag();
        // 创建一个新线程，this就是实现了Runnable接口的实现类
        Thread t = new Thread(this);
        // 启动线程
        t.start();
        // 设定焦点在本面板并作为监听对象
        setFocusable(true);
    }
    @Override
    public void paint(Graphics g) {

        if (iBuffer == null) {
            iBuffer = createImage(getWidth(), getHeight());
            gBuffer = iBuffer.getGraphics();
        }
        g.clearRect(0, 0, getWidth(), getHeight());
        //摄像头更新
        cameraMag.cameraMoving(playerMaintransform);
        //地图渲染
        mapManager.currentMap.mapRender(g, this, new Transform(cameraMag.cameraMove.getMapMoving(), playerMaintransform.yPos));
        //管理类渲染
        renderManager.render(g, this, playerMaintransform, cameraMag);
    }
    @Override
    public void run() {
        long lastUpdate = System.currentTimeMillis();
        int fps = 30;
        while (true) {// 线程中的无限循环

            while (GameProcess.instance.paused) {
                repaint();
            }

            long interval = 1000 / fps;
            long curr = System.currentTimeMillis();
            if (curr - lastUpdate < interval) {
                try {
                    Thread.sleep(interval - (curr - lastUpdate));
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                lastUpdate = curr;
                player.Update();
                mapManager.currentMap.Update();
                repaint();// 窗口重绘
            }
        }
    }

}

