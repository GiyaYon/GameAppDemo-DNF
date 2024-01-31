# JavaSE Game Application - Dungeon Fighter Reprinted Version
JavaSE 应用程序游戏 地下城与勇士复刻版
video： https://www.bilibili.com/video/BV1QY411S7wf/?share_source=copy_web&vd_source=aa81619aeb086fe8c61caae58ee361bb
## 1.介绍（Introduction）
* 作者(Author): **GiyaYon** 
* 时间(Date): **2022/11/25**
* 实现需求(Implemented Requirements):
  1. 完成人物素材导入，动画播放，控制执行，剧情交互
  #### The Characters image files are imported and processed into animations for playing the user will control it to do some things by keyboard,Fighting with robot character and getting 2D game experience from the character story of a play.
  2. 完成根据配置表读取图片素材与自定义配置数据完成单个人物，地图制作，扩展游戏玩法
  #### the system will refer the configuring table to read the image files and customizing data file for instantiating the Character and the Map,increasing the interest of game.
  3. 实现网络联机对战
  #### multiplayer real time fighting game
 

## 2. 项目文件模块阐述（Project scr Function Modular Describe）

实现游戏的UI界面，游戏场景（人物，地图），网络联机，文件读取，等功能，为此将代码结构定义如下:
<br>
#### For Implementing the game needs like UIFrame,GameScence(Character and Map),multiplayer,configuring file reading and so on functions
#### this project struct was defined be like:
>### 1.Entity --- 业务实体模块(service Entity Modular)
>负责具体业务实现，为实体扩展自定义功能，像角色鬼剑士的三段攻击，怪物的一段攻击，神枪手的多段攻击，
地图的动画效果，地图出现的物件，此外特定的UI界面等等具体业务功能。
> #### Having responsible for the service to implement in detail and extendable.For example,Character Swordsman exists triple attacking action,but Character goblin just have one,they are implemented by Model and extended from Model
>

>### 2.Model--- 业务模型模块 (service Model Modular)
>负责具体业务逻辑建模，做好基础必要的功能，通过**IOProcessing模块** 与 **LogicalProcessing模块**提供的底层代码实现
> #### Having responsible for model to build the service of base,Built By IOProcessing Modular and LogicalProcessing Modular supplying base functions code.

>### 3.IOProcessing--- 输入输出处理模块(InputOutput Processing Modular)
>负责外部读取与向外的画面渲染处理，文件IO流读取，渲染画面处理
> #### Having responsible for outside reading file or network request and screen rendering

>### 4.LogicalProcessing ---游戏逻辑处理模块(GameBaseLogicalProcessing Modular)
>游戏底层业务逻辑，一系列与游戏场景相关的逻辑
> #### Having responsible for basic service logic, a series of  relevant GameScence logic.
