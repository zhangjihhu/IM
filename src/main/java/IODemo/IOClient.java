package IODemo;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class IOClient {

    public static void main(String[] args) {
        new Thread(() -> {
            Socket socket = null;
            try {
                socket = new Socket("127.0.0.1",8000);
                while (true) {
                    socket.getOutputStream().write((new Date() + ":hello world").getBytes());
                    Thread.sleep(2000);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }).start();
    }


}
