package scr.IOProcessing.Renders;

import scr.Model.Characters.Position.Transform;

import javax.swing.*;
import java.awt.*;

public interface IRender extends Comparable {

    int getYPos();
    void render(Graphics g, JPanel panel, Transform transform);
}
