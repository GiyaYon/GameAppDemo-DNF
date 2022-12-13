package scr.Model.Map;

import scr.Entity.Players.RobotPlayer;
import scr.IOProcessing.Renders.IRender;
import scr.LogicalProcessing.Collide.Colliders.BoxCollider;
import scr.LogicalProcessing.Position.Vector2D;
import scr.Model.BasePlayer.CharacterBaseModel;
import scr.Model.Characters.DetectsColliders.BodyDetectsCollider;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 定义关卡的敌人数量，对话事件触发等等
 */
public abstract class StageModel extends BaseMapModel implements Comparable{

    //当前关卡序列
    public JPanel p;
    public int mapIndex;
    public ArrayList<RobotPlayer> monsters;
    public int collideWidthX1,collideWidthX2,collideHeightY1,collideHeightY2;

    public BodyDetectsCollider tatget;
    public StageModel(int type,String FarName, String NearPath, int collideWidthX1, int collideWidthX2, int collideHeightY1, int collideHeightY2,JPanel p) throws IOException {
        super(type,FarName,NearPath);
        this.p = p;
        this.collideWidthX1 = collideWidthX1;
        this.collideWidthX2 = collideWidthX2;
        this.collideHeightY1 = collideHeightY1;
        this.collideHeightY2 = collideHeightY2;

        Borders = new ArrayList<>();
        BoxCollider mapTopBorder = new BoxCollider(collideWidthX1,collideHeightY1-100,1200,100,new Vector2D(0,1));
        BoxCollider mapBottomBorder = new BoxCollider(collideWidthX1,collideHeightY2,1200,100,new Vector2D(0,-1));
        BoxCollider mapLeftBorder = new BoxCollider(collideWidthX1-100,collideHeightY1,100,300,new Vector2D(1,0));
        BoxCollider mapRightBorder = new BoxCollider(collideWidthX2,collideHeightY1,100,300,new Vector2D(-1,0));
        Borders.add(mapTopBorder);
        Borders.add(mapBottomBorder);
        Borders.add(mapLeftBorder);
        Borders.add(mapRightBorder);
    }

    public abstract void Update();
    public abstract void Init();

}
