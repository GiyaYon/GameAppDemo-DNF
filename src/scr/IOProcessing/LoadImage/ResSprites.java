package scr.IOProcessing.LoadImage;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ResSprites {

    //读取txt坐标
    FileReader fr;
    BufferedReader br;
    ArrayList<String> positions;

    // 读取的图片数组
    private Image pics[] = null;
    String txtFilePath,picFilePath;


    public ResSprites(String path) throws IOException {

        // 加载文档
        txtFilePath = "src\\res\\"+path+"\\x.txt";
        fr = new FileReader(txtFilePath);
        br =new BufferedReader(fr);
        positions = new ArrayList<>();
        String s;
        while ((s = br.readLine()) != null)
        {
            positions.add(s);
        }
        br.close();


        // 加载图片
        picFilePath = "src\\res\\"+ path;
        int size = getFilesCount(new File(picFilePath),"png");
        pics = new Image[size];
        int num = 0;
        for (int i = 0; i < size; i++) {
            pics[i] = Toolkit.getDefaultToolkit().getImage(picFilePath+"\\" + num + ".png");
            num++;
        }
    }
    public Image[] getImageArray (int first,int last)
    {
        int index = first;
        Image[] il = new Image[last - first +1];
        for (int i = 0; i <  last - first +1; i++)
        {
            il[i] = getImageFromArray(index);
            index++;
        }
        return il;
    }
    public ArrayList<String> getPositionsArray(int first,int last)
    {
        int index = first;
        ArrayList<String> a = new ArrayList<>();
        for ( int i = 0 ;i<= last - first +1; i++)
        {
            a.add(positions.get(index));
            index++;
        }
        return a;
    }
    public String getPositionFromArray(int index)
    {
       return positions.get(index);
    }
    public Image getImageFromArray(int index)
    {
        return pics[index];
    }

    public static int getFilesCount(File srcFile,String type){
        // 判断传入的文件是不是为空
        int count = 0;
        if (srcFile == null) {
            throw new NullPointerException();
        }
        // 把所有目录、文件放入数组
        File[] files = srcFile.listFiles();
        // 遍历数组每一个元素
        assert files != null;
        for (File f : files) {
            // 判断元素是不是文件夹，是文件夹就重复调用此方法（递归）
            if (f.isDirectory()) {
                getFilesCount(f,type);
            }else {
                // 判断文件是不是以.png结尾的文件，并且count++（注意：文件要显示扩展名）
                if (f.getName().endsWith("."+ type)) {
                    count++;
                }
            }
        }
        // 返回.txt文件个数
        return count;
    }

}
