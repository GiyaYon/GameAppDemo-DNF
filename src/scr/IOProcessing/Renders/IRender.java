package scr.IOProcessing.Renders;

import scr.LogicalProcessing.Position.Transform;
import scr.Model.Map.IObject;

import javax.swing.*;
import java.awt.*;

public interface IRender extends Comparable, IObject {

    int getYPos();
    void render(Graphics g, JPanel panel, Transform transform);
}
