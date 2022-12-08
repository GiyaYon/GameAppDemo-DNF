package scr.Model.Characters.Properties;

/**
 * 面板属性，生命值，蓝条
 */
public class CharacterUIProperty {

    public float hp;
    public float mp;
    public float atk;

    public CharacterUIProperty() {
        setInit();
    }

    public void setInit()
    {
        hp = 100;
        mp = 100;
        atk = 10;
    }
}
