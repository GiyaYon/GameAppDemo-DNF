package scr.LogicalProcessing.NetWork;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

/**
 * ÿ��x���player�Ĳ������͸�����IO������
 * ���ص����ݿ����ƶ�
 */
public class PlayerNetWorkControl implements ActionListener {
    Timer timer;
    public Client playerClient;
    public Queue<String> sedCommand;
    public Queue<String> resCommand;
    public static PlayerNetWorkControl instance= new PlayerNetWorkControl();
    public PlayerNetWorkControl()
    {

    }

    public void start()
    {
        sedCommand = new LinkedList<String>();
        resCommand = new LinkedList<>();
        timer = new Timer(100,this);
        timer.start();
        playerClient = new Client();
        try {
            playerClient.start();
        }catch ( IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String s = "";
        for (int i = 0; i < 3; i++) {
            String sed = sedCommand.poll();
            if(sed == null)
            {
                sed = BufferType.IDLE;
            }
            if(i == 2){ s+= sed;continue;}
            s += sed+"|";
        }
        playerClient.outMsg = s;

    }

}
