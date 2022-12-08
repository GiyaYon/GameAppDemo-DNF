package scr.Model.BasePlayer;

import scr.Entity.Characters.Swordman.SwordsManAnimator;
import scr.Entity.Characters.Swordman.SwordsmanCommand;
import scr.IOProcessing.Renders.IRender;
import scr.LogicalProcessing.Position.Transform;
import scr.LogicalProcessing.Robot.IController;
import scr.Model.Characters.DetectsColliders.BodyDetectsCollider;
import scr.Model.Characters.DetectsColliders.PositionDetectsCollider;
import scr.Model.Characters.Properties.Property;
import scr.Model.Map.StageModel;

import java.io.IOException;

/**
 * 只需要给角色添加控制器即可使用
 */
public abstract class CharacterBaseModel implements IRender, IController {

    //动画机：自行修改
    public SwordsManAnimator swordsManAnimator;
    //命令：自行修改
    public SwordsmanCommand swordsmanCommand;
    //坐标
    public Transform transform;
    //坐标点
    public PositionDetectsCollider pointCollider;
    //身体检测盒：自行修改
    public BodyDetectsCollider bodyDetectsCollider;
    //外界认知：自行修改
    public StageModel stageModel;
    //通用属性
    public Property property;

    public abstract void Start() throws IOException;
    public abstract void Update();


}
