package scr.Model.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

//public class MainUi extends JFrame{//继承JFrame、JFrame对应窗口，可以当作一个画框
//
//
//    //主函数
//    public static void main(String[] args) {
//
//        GameProcess gameProcess = new GameProcess();
//
//         MainUi ui = new MainUi();
//         ui.init();
//    }
//    //构造器
//    public MainUi(){
//
//
//    }
//    public void init()
//    {
//        //初始化面板
//        panel = new Panel();
//        //面板放入画框
//        this.add(panel);
//        this.setLocation(400, 100);
//        this.setSize(800, 590);
//        //设置当点击窗口结束按钮后程序退出。若无此设置点击窗口 x 按钮后程序仍在执行
//        this.setUndecorated(true);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        //设置显示
//        this.setVisible(true);
//    }
//
//}
public class MainPagePanel extends JPanel implements KeyListener {
    //panel是一个画板
    //graphics g--是一个画笔
    int selecting = 114;
    public static final int STAGE_MODEL = 114;
    public static final int MULTIPLAYER = 115;
    public static final int EXIT = 116;

    public JumpManager jm;

    public MainPagePanel()
    {
        setFocusable(true);
        addKeyListener(this);
        jm = new JumpManager();
        jm.addHitListener(GameProcess.instance);
    }

    @Override
    public void paint(Graphics g) { //这是一个绘图方法
        //
        int w = getSize().width;
        int h = getSize().height;
        Image backGround = Toolkit.getDefaultToolkit().getImage("src\\res\\UI\\bg.png");
        g.drawImage(backGround,0,0,(int)(backGround.getWidth(this)*1.3),(int)(backGround.getHeight(this)*1.3),this);
        g.setColor(Color.white);
        g.drawString("Program Author: GiyaYon,2022/11/25,For JavaSE Curriculum Design",w/2,h-20);

        g.drawString("D&F Java_SE version",20,h-20);

        switch (selecting)
        {
            case STAGE_MODEL:

                Font f=new Font(null,Font.HANGING_BASELINE,17);
                g.setFont(f);
                g.setColor(Color.yellow);
                g.drawString("Stage Model",100, 250);

                g.setColor(Color.white);
                Font f1=new Font(null,Font.HANGING_BASELINE,15);
                g.setFont(f1);
                g.drawString("Multiplayer",100,300);
                g.drawString("Exit",100,350);
                break;
            case MULTIPLAYER:
                Font f2=new Font(null,Font.HANGING_BASELINE,17);
                g.setFont(f2);
                g.setColor(Color.yellow);
                g.drawString("Multiplayer",100, 300);

                g.setColor(Color.white);
                Font f3=new Font(null,Font.HANGING_BASELINE,15);
                g.setFont(f3);
                g.drawString("Stage Model",100,250);
                g.drawString("Exit",100,350);
                break;
            case EXIT:
                Font f4=new Font(null,Font.HANGING_BASELINE,17);
                g.setFont(f4);
                g.setColor(Color.yellow);
                g.drawString("Exit",100, 350);

                g.setColor(Color.white);
                Font f5=new Font(null,Font.HANGING_BASELINE,15);
                g.setFont(f5);
                g.drawString("Stage Model",100,250);
                g.drawString("Multiplayer",100,300);
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_DOWN) {
            selecting++;
            if(selecting >EXIT)
            {
                selecting = STAGE_MODEL;
            }
            repaint();
        }
        if (keyCode == KeyEvent.VK_UP) {
            selecting--;
            if(selecting < STAGE_MODEL)
            {
                selecting = EXIT;
            }
            repaint();
        }
        if(keyCode == KeyEvent.VK_ENTER)
        {
            jm.jump(selecting);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}

/*
 * 绘图方法：
 *       画直线：drawLine(int var1, int var2, int var3, int var4)
 *       画矩形边框：drawRect(int x, int y, int width, int height)
 *       画椭圆边框：drawOval(int var1, int var2, int var3, int var4)
 *       填充矩形：fillRect(int var1, int var2, int var3, int var4)
 *       设置画笔颜色：setColor(Color.blue);
 *       填充椭圆:fillOval(int var1, int var2, int var3, int var4)
 *       画图片：
 *               //获取图片资源---Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/picture.png"))
 *               //画图片：Image image = drawImage(Image var1, int var2, int var3, int var4, int var5, ImageObserver var6)
 *                       例：drawImage(image, 125, 125, 200, 200, this)
 *       设置画笔字体：setFont(Font var1)
 *       画字符串:drawString(String var1, int var2, int var3)
 * */

/*
 * 说明paint()方法被调用的情况：
 *       1、当组件第一次在屏幕显示时会自动调用paint()方法
 *       2、窗口最小化，再最大化
 *       3、窗口大小发生变化
 *       4、repaint（）函数被调用
 * */
//绘制一个面板，继承JPanel
