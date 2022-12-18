package scr.LogicalProcessing.NetWork;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * 多线程:专门来连接数据，还有读取数据，防止阻塞
 *
 */


public class Server {

    public static ArrayList<Socket> ipMap;
    public static void main(String[] args) throws Exception {
        ipMap = new ArrayList<>();
        // 创建一个ServerSocket对象，指定服务端端口、地址
        ServerSocket serverSocket = new ServerSocket(7072, 50, InetAddress.getByName("localhost"));
        System.out.println("服务器启动：" + serverSocket);

        while (true) {
            System.out.println("等待连接...");
            // 等待客户端连接
            Socket activeSocket = serverSocket.accept();
            ipMap.add(activeSocket);
            System.out.println("接收到一个连接，来自：" + activeSocket);
            // 接收到一个连接，就开启一个线程处理
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    handleClientRequest(activeSocket);
                }
            };
            new Thread(runnable).start();
        }
    }

    private static void handleClientRequest(Socket socket) {

        BufferedReader socketReader = null;
        BufferedWriter socketWrite = null;
        try {
            // 通过socket获取字节流，然后包装成字符缓冲流
            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inMsg = null;
            // 获取客户端传输到服务端的消息
            while ((inMsg = socketReader.readLine()) != null) {
                System.out.println("接收到客户端"+ socket.getPort() +"的消息：" + inMsg);
                for(var i : ipMap)
                {
                    socketWrite = new BufferedWriter(new OutputStreamWriter(new BufferedOutputStream(i.getOutputStream())));
                    // 向客户端响应消息
                    socketWrite.write(socket.getPort() +":" + inMsg);
                    socketWrite.write("\n");
                    socketWrite.flush();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 手动关闭资源
            if (socketReader != null) {
                try {
                    socketReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socketWrite != null) {
                try {
                    socketWrite.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}