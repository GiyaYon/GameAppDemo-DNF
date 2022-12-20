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

    //��Ⱦ˳��
    public RenderSequenceManager renderManager = new RenderSequenceManager();
    //----------------------�����
    Player player;
    //��������ҵ�Transform
    Transform playerMaintransform;
    //�����
    CameraMag cameraMag;

    //---------------------˫������
    public Image iBuffer;
    public Graphics gBuffer;
    //---------------------��ͼ����
    PKMapManager mapManager;


    public MultiplayerPanel() throws IOException {

        player = new Player(this, "Miren13");
        player.Start();
        player.startNetWorkConnect();

        playerMaintransform = player.transform;
        mapManager = new PKMapManager(this);

//-------------------------��ͼ���----------------------------//
        mapManager.currentMap.tatget = player.bodyDetectsCollider;
        mapManager.currentMap.Init();
        //��Ⱦ���
        if (mapManager.currentMap.obscurers.size() > 0) {
            renderManager.renderMethods.addAll(mapManager.currentMap.obscurers);
        }
        for (var character : mapManager.currentMap.monsters) {
            renderManager.renderMethods.add(character);
            player.property.bdcs.add(character.bodyDetectsCollider);
        }
        //��Χ����ײ���
        for (var v : mapManager.currentMap.obscurers) {
            player.property.bdcs.add(v.bodyDetectsCollider);
        }
        //�赲����
        player.stageModel = mapManager.currentMap;
//--------------------------------------------------------//
        //��Ⱦ���
        renderManager.renderMethods.add(player);
        //����ͷ
        cameraMag = new CameraMag();
        // ����һ�����̣߳�this����ʵ����Runnable�ӿڵ�ʵ����
        Thread t = new Thread(this);
        // �����߳�
        t.start();
        // �趨�����ڱ���岢��Ϊ��������
        setFocusable(true);
    }
    @Override
    public void paint(Graphics g) {

        if (iBuffer == null) {
            iBuffer = createImage(getWidth(), getHeight());
            gBuffer = iBuffer.getGraphics();
        }
        g.clearRect(0, 0, getWidth(), getHeight());
        //����ͷ����
        cameraMag.cameraMoving(playerMaintransform);
        //��ͼ��Ⱦ
        mapManager.currentMap.mapRender(g, this, new Transform(cameraMag.cameraMove.getMapMoving(), playerMaintransform.yPos));
        //��������Ⱦ
        renderManager.render(g, this, playerMaintransform, cameraMag);
    }
    @Override
    public void run() {
        long lastUpdate = System.currentTimeMillis();
        int fps = 30;
        while (true) {// �߳��е�����ѭ��

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
                repaint();// �����ػ�
            }
        }
    }

}

