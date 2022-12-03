package scr.Model.Map;

import scr.Model.Characters.Transform;
import scr.Viewer.Renders.IRender;

import javax.swing.*;
import java.awt.*;

public class Obscurer implements IRender ,Comparable {

    Image oBox;

    int x,y;

    public Obscurer(int x,int y) {
        oBox = Toolkit.getDefaultToolkit().getImage("src\\res\\object\\dragonheadfront\\" + 0 + ".png");
        this.x = x;
        this.y = y;
    }

    @Override
    public int getYPos() {
        return y;
    }

    @Override
    public void render(Graphics g, JPanel panel, Transform transform) {
        g.drawImage(oBox,x - transform.xPos - oBox.getWidth(panel),y - oBox.getHeight(panel), oBox.getWidth(panel), oBox.getHeight(panel),panel);
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        IRender s = (IRender) o;
        if(this.y > s.getYPos()){
            return 1;
        }else if(this.y < s.getYPos()){
            return -1;
        }
        return 0;
    }
}
