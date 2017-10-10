import java.net.*;
import java.io.*;

public class Broker extends Thread {

    private ServerSocket serverSocket;

    MessChannel messChannel = MessChannel.getInstance();

    public Broker(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(100000);
    }

    public void run() {

        new Recover(5000).start();

        while (true) {
            try {

                System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");

                Socket server = serverSocket.accept();
                new BrokerThread(server).start();

            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
                break;
            } catch (IOException e) {
                System.out.println("Client deconectat");
                e.printStackTrace();
                break;
            }
        }
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