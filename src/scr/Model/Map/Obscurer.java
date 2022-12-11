package scr.Model.Map;

import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.Model.Characters.CharacterEvents.HitEvent;
import scr.Model.Characters.CharacterEvents.HitListener;
import scr.Model.Characters.DetectsColliders.BodyDetectsCollider;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Position.Vector2D;
import scr.IOProcessing.Renders.IRender;

import javax.swing.*;
import java.awt.*;

public class Obscurer implements IRender ,Comparable , HitListener , IObject {

    Image oBox;

    int x,y,w,h;

    public BodyDetectsCollider bodyDetectsCollider;
    public BoxCollider testBoxCollider;
    public String cIDName;

    public Transform transform;
    public Obscurer(int x,int y,String cIDName) {
        this.cIDName = cIDName;
        oBox = Toolkit.getDefaultToolkit().getImage("src\\res\\object\\dragonheadfront\\" + 0 + ".png");
        this.x = x;
        this.y = y;
        transform = new Transform(x,y);
        bodyDetectsCollider = new BodyDetectsCollider(x,y,100,100,new Vector2D(0,0),this,transform);
        testBoxCollider = new BoxCollider(x,y, 30, 20,new Vector2D(1,0));
        bodyDetectsCollider.hitManager.addHitListener(this);
    }
    public Obscurer(int x,int y,int w,int h,boolean needImage)
    {
        this.x = x;
        this.y = y;
        this.w =w;
        this.h = h;
    }
    @Override
    public int getYPos() {
        return y;
    }


    @Override
    public void render(Graphics g, JPanel panel, Transform transform) {
        bodyDetectsCollider.updatePosition(new Transform(x*2 - (oBox.getWidth(panel) /2) -transform.xPos ,y -oBox.getHeight(panel)),new Vector2D(oBox.getWidth(panel), oBox.getHeight(panel)));
        testBoxCollider.updatePosition(new Transform(x*2 - (oBox.getWidth(panel) /2) -transform.xPos ,y -(oBox.getHeight(panel))/4),new Vector2D(oBox.getWidth(panel), oBox.getHeight(panel)/4));
        g.drawImage(oBox,x*2 - (oBox.getWidth(panel) /2) -transform.xPos ,y -oBox.getHeight(panel) , oBox.getWidth(panel), oBox.getHeight(panel),panel);
        g.drawRect(x*2 - (oBox.getWidth(panel) /2) -transform.xPos ,y -oBox.getHeight(panel) , oBox.getWidth(panel), oBox.getHeight(panel));

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
    public void GameEventInvoke(HitEvent event) {
        System.out.println("i " + getName()+" received" + event.getHitValue()+"damage from " +event.getPlayValue().getName());
    }

    @Override
    public String getName() {
        return cIDName;
    }
    
}
