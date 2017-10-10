
import java.net.*;

import com.google.gson.Gson;

import java.io.*;

public class Receiver {

    public static void main(String[] args) throws InterruptedException {

        String serverName = "localhost";
        int port = 6066;
        String topName = args[0];

        Message message = new Message(topName, "command", "read", "read");
        Gson gson = new Gson();
        String jsonMessage = gson.toJson(message);
        try {

            System.out.println("Connecting to " + serverName + " on port " + port);
            Socket client = new Socket(serverName, port);

            System.out.println("Just connected to Broker ");

            OutputStream outToServer = client.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            out.writeUTF(jsonMessage);

            Thread.sleep(100);

            while (true) {
                InputStream inFromServer = client.getInputStream();
                DataInputStream in = new DataInputStream(inFromServer);
                Thread.sleep(100);

                System.out.println("Server says " + in.readUTF());
            }

        } catch (IOException e) {
            System.out.println("Brocket disconected with Ctrl+c");
        }
    }
}