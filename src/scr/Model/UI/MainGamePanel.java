package scr.Model.UI;

import scr.Entity.Maps.MapManager;
import scr.Entity.Players.Player;
import scr.LogicalProcessing.Position.Transform;
import scr.IOProcessing.Camera.CameraMag;
import scr.IOProcessing.Renders.RenderSequenceManager;
import scr.Model.Map.TransportEvent;
import scr.Model.Map.TransportListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

//public class Main {
//    public static void main(String[] args) throws IOException {
//        JFrame frame = new JFrame();
//        frame.setLocation(400, 100);
//        frame.setSize(800, 590);
//
//        MainGamePanel panel = new MainGamePanel();
//        frame.setContentPane(panel);
//
//        frame.setVisible(true);
//        frame.setResizable(false);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
//}
public class MainGamePanel extends JPanel implements Runnable , TransportListener,KeyListener {

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
    MapManager mapManager;


    int selecting = 114;
    public static final int AGAIN = 114;
    public static final int EXIT = 115;


    public MainGamePanel() throws IOException {

        player = new Player(this, "Miren13");
        player.Start();
        playerMaintransform = player.transform;
        mapManager = new MapManager(this);

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
        addKeyListener(this);
    }

    public void changeMap() {
        player.property.bdcs.clear();
        renderManager.renderMethods.clear();

        mapManager.currentMap = mapManager.maps.get(mapManager.index);
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
        renderManager.renderMethods.add(player);

        //包围盒碰撞检测
        for (var v : mapManager.currentMap.obscurers) {
            player.property.bdcs.add(v.bodyDetectsCollider);
        }
        //阻挡物检测
        player.stageModel = mapManager.currentMap;

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

        if (GameProcess.instance.paused) {
            Image backGround = Toolkit.getDefaultToolkit().getImage("src\\res\\UI\\pause.png");
            g.drawImage(backGround, 0, 0, this);
            Image again = Toolkit.getDefaultToolkit().getImage("src\\res\\UI\\tryAgain.png");
            g.drawImage(again, 95, 55, this);
            Image again2 = Toolkit.getDefaultToolkit().getImage("src\\res\\UI\\back.png");
            g.drawImage(again2, 95, 85, this);
            if (selecting == AGAIN) {
                Image again1 = Toolkit.getDefaultToolkit().getImage("src\\res\\UI\\tryAgainL.png");
                g.drawImage(again1, 95, 55, this);
            } else if (selecting == EXIT) {
                Image again3 = Toolkit.getDefaultToolkit().getImage("src\\res\\UI\\backL.png");
                g.drawImage(again3, 95, 85, this);
            }


        }
        //修改后坐标点
//        g.setColor(Color.green);
//        g.fillOval(playerMaintransform.xPos, playerMaintransform.yPos, 10, 10);

        //显示文字信息
//        int y = 20;
//        for (String line : player.info.split(" "))
//        {
//            g.drawString(line,20,y);
//            y += 20;
//        }
//
//        if(playerMaintransform != null)
//        {
//            g.drawString("x="+ playerMaintransform.xPos +" ,y=" + playerMaintransform.yPos,200,20);
//            //g.drawString("Bot="+ ,200,40);
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

    @Override
    public void GameEventInvoke(TransportEvent event) {
        mapManager.nextMap();
        changeMap();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("p");
            GameProcess.instance.paused = !GameProcess.instance.paused;
        }
        if (GameProcess.instance.paused) {
            int keyCode = e.getKeyCode();
            if (keyCode == KeyEvent.VK_DOWN) {
                selecting++;
                if (selecting > EXIT) {
                    selecting = AGAIN;
                }
                repaint();
            }
            if (keyCode == KeyEvent.VK_UP) {
                selecting--;
                if (selecting < AGAIN) {
                    selecting = EXIT;
                }
                repaint();
            }
            if (keyCode == KeyEvent.VK_ENTER) {
                switch (selecting) {
                    case AGAIN:
                        GameProcess.instance.frame.setOpacity(1.0f);
                        new Timer().schedule(new TimerTask() {
                            float alpha = 1.0f;
                            @Override
                            public void run() {

                                if (alpha <= 0) {
                                    try {
                                        GameProcess.instance.jumpTpStagePage();
                                    } catch (IOException ex) {
                                        throw new RuntimeException(ex);
                                    }
                                    reload();
                                    cancel();
                                } else {
                                    GameProcess.instance.frame.setOpacity(alpha);
                                }
                                alpha = alpha - 0.1f;
                            }
                        }, 1000, 100);

                        break;
                    case EXIT:
                            GameProcess.instance.run(new MainPagePanel());
                            reload();
                        break;
                }

            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void reload() {
            new Timer().schedule(new TimerTask() {
                float alpha = 0.0f;
                @Override
                public void run() {
                    if (alpha > 1.0) {
                        cancel();
                    } else {
                        GameProcess.instance.frame.setOpacity(alpha);
                    }
                    alpha = alpha + 0.1f;
                }
            }, 1000, 100);

    }
}
