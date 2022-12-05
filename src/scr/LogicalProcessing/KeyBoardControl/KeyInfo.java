package scr.LogicalProcessing.KeyBoardControl;

public class KeyInfo {
   public int count;
   public int key;

   public int time;
   public KeyInfo(int key,int count,int time)
   {
       this.count = count;
       this.key = key;
   }
}
