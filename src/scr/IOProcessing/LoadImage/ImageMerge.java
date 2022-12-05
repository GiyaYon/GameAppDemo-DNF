package scr.IOProcessing.LoadImage;

import java.awt.*;
import java.util.ArrayList;

public class ImageMerge {

    public String name;
    public Image[] images;
    public ArrayList<String> positions;

    public ImageMerge(String name, Image[] images, ArrayList<String> positions)
    {
        this.name = name;
        this.images = images;
        this.positions = positions;
    }
}
