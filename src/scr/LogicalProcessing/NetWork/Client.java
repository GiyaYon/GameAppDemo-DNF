package scr.LogicalProcessing.NetWork;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class Client {

    Socket socket;
    public  String outMsg = null;
    public void start() throws IOException {
        // 创建socket，并指定服务器的ip(host) 和 端口
        socket = new Socket("localhost", 7072);
        // 获取socket所绑定的本地地址
        System.out.println("启动客户端：" + socket.getLocalPort());
        // 通过socket获取字节流，并包装成字符缓冲流
        BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter socketWrite = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        // 通过控制台输入发送给服务端的消息，并把字节流包装成字符缓冲流
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    String inMsg = null;
                    try {
                        inMsg = socketReader.readLine();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    process(inMsg);
                    //System.out.println(inMsg);
                }
                // 读取并显示来自服务器的消息
            }
        }).start();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // 向服务器发送一行消息，因为服务器每次读取一行
                if(outMsg  != null && !outMsg.equals(""))
                {
                    try
                    {
                        socketWrite.write(outMsg);
                        socketWrite.write("\n");
                        socketWrite.flush();
                        outMsg = "";
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }, 10, 30);
        // 获取控制台输入内容，每次一行

        // 关闭资源，socket关闭时，其对应的流也会关闭，为了防止内存泄漏，
        // 可以手动关闭其他流对象，这里偷个懒
//        socket.close();
//        System.out.println("客户端已关闭");
    }

    public void process(String msg)
    {
        String[] port = msg.split(":");
        String[] command = port[1].split("\\|");
        if(!String.valueOf(socket.getLocalPort()).equals(port[0]))
        {
            PlayerNetWorkControl.instance.resCommand.addAll(Arrays.asList(command));
        }
    }
}
