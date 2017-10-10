
import java.net.*;
import com.google.gson.Gson;
import java.io.*;

public class Sender {

    public static void main(String[] args) throws InterruptedException {

        String topName = args[0];
        String serverName = "localhost";
        int port = 6066;

        try {

            Socket client = new Socket(serverName, port);

            OutputStream outToServer = null;
            DataOutputStream out = null;
            int i = 0;

            System.out.println("Connecting to " + serverName + " on port " + port);
            System.out.println("Just connected to Broker");
            outToServer = client.getOutputStream();
            out = new DataOutputStream(outToServer);

            while (i < 110) {

                i++;

                Message mes = new Message(topName, "command", "send", "value =  " + i);

                Gson gson = new Gson();
                String jmes = gson.toJson(mes);

                out.writeUTF(jmes);

                Thread.sleep(1000);
            }

            Message mes = new Message(topName, "command", "ShutDown", "STOP");

            Gson gson = new Gson();
            String jmes = gson.toJson(mes);

            out.writeUTF(jmes);

            client.close();

        } catch (IOException e) {
            System.out.println("Brocket disconected with Ctrl+c");
        }
    }

}