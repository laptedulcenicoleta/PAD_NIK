
import java.net.*;
import java.io.*;

public class Broker extends Thread {

    private ServerSocket serverSocket;


    public Broker(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
    }


    public static void main(String[] args) {

        int port = 6066;
        try {

            Thread t = new Broker(port);
            t.start();

        } catch (IOException e) {

            System.out.println("Client deconectat");

            e.printStackTrace();
        }
    }
}