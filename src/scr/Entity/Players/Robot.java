package scr.Entity.Players;

import scr.IOProcessing.Renders.IRender;
import scr.LogicalProcessing.Position.Transform;
import scr.Model.BasePlayer.CharacterBaseModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Robot extends CharacterBaseModel {
    @Override
    public int getYPos() {
        return transform.yPos;
    }

    @Override
    public void render(Graphics g, JPanel panel, Transform transform) {

    }

    @Override
    public void Start() throws IOException {

    }

    @Override
    public void Update() {

    }

    @Override
    public int compareTo(Object o) {
        IRender s = (IRender) o;
        if(transform.yPos>s.getYPos()){
            return 1;
        }else if(transform.yPos<s.getYPos()){
            return -1;
        }
        return 0;
    }

    @Override
    public String getName() {
        return cIDName;
    }
}
