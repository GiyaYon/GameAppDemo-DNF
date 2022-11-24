package scr.Viewer.Anim;


import scr.Model.Characters.Transform;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 播放器 animation player
 *  it controls the slow or fast rate of playing
 */
public class Animator implements ActionListener {

    Timer playRate;
    public AnimationMergeGroup currentAnim;
    int rate;
    public Animator()
    {
        playRate = new Timer(120,this);
        playRate.start();
    }

    public void setPlayRate(int rate)
    {
        this.rate = rate;
        playRate.setDelay(rate);

    }

    public boolean getFinish()
    {
        return currentAnim.igArray.get(currentAnim.igArray.size() - 1).isFinish;
    }

    public void resetAnim(AnimationMergeGroup animationMergeGroup)
    {
        for (var v:animationMergeGroup.igArray)
        {
            v.resetFrame();
        }
    }
    public void play(AnimationMergeGroup anima)
    {
        currentAnim = anima;

    }

    public void Flash(Graphics g, JPanel jPanel, Transform transform)
    {
        for(var v: currentAnim.igArray)
        {
            v.set(transform.xPos,transform.yPos);
            v.flip(transform.flipX);
            v.drawSprite(g,jPanel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        for(var v: currentAnim.igArray)
        {
            v.toNextFrame();
        }
    }
}
