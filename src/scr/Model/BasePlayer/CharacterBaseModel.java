package scr.Model.BasePlayer;

import scr.IOProcessing.Renders.IRender;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Robot.IController;
import scr.Model.Characters.Commands.GameObjectAction;
import scr.Model.Characters.DetectsColliders.BodyDetectsCollider;
import scr.Model.Characters.DetectsColliders.PositionDetectsCollider;
import scr.Model.Characters.Properties.CharacterAnimator;
import scr.Model.Characters.Properties.Property;
import scr.Model.Map.IObject;
import scr.Model.Map.StageModel;

import java.io.IOException;

/**
 * 只需要给角色添加控制器即可使用
 */
public abstract class CharacterBaseModel implements IRender, IController, IObject {

    //物品独立id
    public String cIDName;
    //动画机：自行新建类定义
    public CharacterAnimator cAnimator;
    //命令：自行新建类定义
    public GameObjectAction actionCommands;
    //坐标
    public Transform transform;
    //坐标点
    public PositionDetectsCollider pointCollider;
    //身体检测盒：自行修改尺寸
    public BodyDetectsCollider bodyDetectsCollider;
    //外界认知：自行添加
    public StageModel stageModel;
    //通用属性
    public Property property;

    public abstract void Start() throws IOException;
    public abstract void Update();


}
