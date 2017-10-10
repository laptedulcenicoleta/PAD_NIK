
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

import org.json.JSONException;
import org.json.JSONObject;

public class BrokerThread extends Thread {

    protected Socket server;
    protected String topName;

    public BrokerThread(Socket clientSocket) {
        this.server = clientSocket;

    }

    public void run() {

        MessChannel messChannel = MessChannel.getInstance();

        while (true) {
            try {

                DataInputStream in = new DataInputStream(server.getInputStream());

                String input = in.readUTF();

                String command = getAttribute("command", input);
                String topName = getAttribute("topName", input);

                if (command.equals("ShutDown")) {
                    System.out.println("COMMAND SHOWT DOWN From Sender");
                    server.close();
                    break;
                } else if (command.equals("send")) {

                    messChannel.addMes(topName, input);

                } else if (command.equals("read")) {

                    while (true) {

                        messChannel.sendToReceiver(topName, server);

                        Random rand = new Random();
                        int randomNum = rand.nextInt(200 - 100) + 100;

                        Thread.sleep(randomNum);
                    }

                }

            } catch (IOException e) {
                System.out.println("Client deconectat With Ctrl+C");
                return;
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getAttribute(String key, String input) throws JSONException {

        JSONObject jObject = new JSONObject(input);
        String command = jObject.getString(key);

        return command;
    }

}
