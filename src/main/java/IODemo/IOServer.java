package IODemo;


import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class IOServer {


    public static void main(String[] args) throws IOException {

        final ServerSocket serverSocket = new ServerSocket(8000);

        new Thread(() -> {


            try {

                // (1)阻塞方法获取新的连接
                Socket socket = serverSocket.accept();
                // (2)每一个新的连接都创建一个线程，负责读取数据
                new Thread(()->{
                    int len;
                    byte[] data = new byte[1024];
                    try {
                        InputStream inputStream = socket.getInputStream();
                        while ((len = inputStream.read(data)) != -1) {
                            System.out.println(new String(data, 0, len));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }).start();



            } catch (IOException e) {
                e.printStackTrace();
            }


        } ).start();


    }



}
