package scr.Model.UI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class GameProcess extends JFrame implements JumpListener{
    public static GameProcess instance;
    private JPanel panel = null;
    public JFrame frame;
    public boolean paused,isDraging;
    int xx,yy,fx = 400,fy = 100;
    public GameProcess()
    {
        if(instance == null)instance = this;
//实现无边框拖拽界面
//监听最初位置
        this.addMouseListener(new MouseAdapter() {    //给JFrame窗体添加一个鼠标监听
            public void mousePressed(MouseEvent e) {     //鼠标点击时记录一下初始位置
                isDraging = true;
                xx = e.getX();
                yy = e.getY();
            }
            public void mouseReleased(MouseEvent e) {  //鼠标松开时
                isDraging = false;
                int left = getLocation().x;
                int top = getLocation().y;
                fx = left + e.getX() - xx;
                fy = top + e.getY() - yy;
            }
        });
//时刻更新鼠标位置
        this.addMouseMotionListener(new MouseMotionAdapter() { //添加指定的鼠标移动侦听器，以接收发自此组件的鼠标移动事件。如果侦听器 l 为 null，则不会抛                                                         出异常并且不执行动作。
            public void mouseDragged(MouseEvent e) {
                //修改位置
                if (isDraging) {                                //只要鼠标是点击的（isDraging），就时刻更改窗体的位置
                    int left = getLocation().x;
                    int top = getLocation().y;
                    setLocation(left + e.getX() - xx, top + e.getY() - yy);

                }
            }
        });

    }

    public void run(JPanel panel)
    {
        if(frame!= null) frame.dispose();
        frame = new JFrame();
        frame.setLocation(400, 100);
        frame.setSize(800, 550);
        this.panel = panel;
        this.add(this.panel);
        this.setLocation(400, 100);
        this.setSize(800, 550);
        //设置当点击窗口结束按钮后程序退出。若无此设置点击窗口 x 按钮后程序仍在执行
        this.setUndecorated(true);
        this.setOpacity(1.0f);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置显示
        this.setVisible(true);
    }
    public void jumpTpStagePage() throws IOException {

        if(frame!= null) frame.dispose();
        frame = new JFrame();
        frame.setLocation(fx, fy);
        frame.setSize(800, 550);
        MainGamePanel panel = new MainGamePanel();
        frame.setContentPane(panel);
        frame.setUndecorated(true);
        frame.setOpacity(0.0f);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        GameProcess gameProcess = new GameProcess();
        MainPagePanel p = new MainPagePanel();
        gameProcess.run(p);
    }

    @Override
    public void GameEventInvoke(JumpEvent event) {

        switch (event.jumpCode)
        {
            case MainPagePanel.STAGE_MODEL:
                try {
                    new Timer().schedule(new TimerTask() {
                        float alpha = 1.0f;
                        @Override
                        public void run() {

                            if (alpha <= 0) {
                                cancel();
                                try {
                                    GameProcess.instance.jumpTpStagePage();
                                    new Timer().schedule(new TimerTask() {
                                        float alpha = 0.0f;
                                        @Override
                                        public void run() {
                                            if (alpha > 1.0) {
                                                cancel();
                                            }
                                            else
                                            {
                                                frame.setOpacity(alpha);
                                            }
                                            alpha = alpha + 0.1f;
                                        }
                                    }, 1000, 100);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                GameProcess.instance.dispose();
                            }
                            else
                            {
                                GameProcess.instance.setOpacity(alpha);
                            }
                            alpha = alpha - 0.1f;
                        }
                    }, 1000, 100);
                }catch (Exception e)
                {
                    JOptionPane.showMessageDialog(null,"Error: "+ e , "Error",JOptionPane.ERROR_MESSAGE);
                }
                break;
            case MainPagePanel.MULTIPLAYER:
                    JOptionPane.showMessageDialog(null, "The Author Will Be Complete This Model", "Warn",JOptionPane.WARNING_MESSAGE);
                break;
            case MainPagePanel.EXIT:
                System.exit(0);
                break;
        }
    }
}



