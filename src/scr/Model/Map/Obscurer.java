package scr.Model.Map;

import scr.Controller.Events.HitEvent;
import scr.Controller.Events.HitListener;
import scr.Controller.IController;
import scr.Model.Characters.BodyDetectsCollider;
import scr.Model.Characters.Transform;
import scr.Model.Characters.Vector2D;
import scr.Viewer.Renders.IRender;

import javax.swing.*;
import java.awt.*;

public class Obscurer implements IRender ,Comparable , HitListener, IController {

    Image oBox;

    int x,y;

    public BodyDetectsCollider bodyDetectsCollider;

    public Obscurer(int x,int y,JPanel panel) {
        oBox = Toolkit.getDefaultToolkit().getImage("src\\res\\object\\dragonheadfront\\" + 0 + ".png");
        this.x = x;
        this.y = y;
        bodyDetectsCollider = new BodyDetectsCollider(x,y,100,100,new Vector2D(0,0));
    }

    @Override
    public int getYPos() {
        return y;
    }

    @Override
    public void render(Graphics g, JPanel panel, Transform transform) {
        g.drawImage(oBox,x - transform.xPos - oBox.getWidth(panel),y - oBox.getHeight(panel), oBox.getWidth(panel), oBox.getHeight(panel),panel);
        g.drawRect(x - transform.xPos - oBox.getWidth(panel),y - oBox.getHeight(panel), oBox.getWidth(panel), oBox.getHeight(panel));

    }
    public void update(JPanel panel, Transform transform)
    {
        bodyDetectsCollider.updatePosition(new Transform(x - transform.xPos - oBox.getWidth(panel),y - oBox.getHeight(panel)));
    }

    @Override
    public int compareTo(Object o) {
        // TODO Auto-generated method stub
        IRender s = (IRender) o;
        if(this.y-10 > s.getYPos()){
            return 1;
        }else if(this.y-10 < s.getYPos()){
            return -1;
        }
        return 0;
    }

    @Override
    public void hitEvent(HitEvent event) {
        System.out.println("i received" + event.getHitValue()+"damage");
    }
}
