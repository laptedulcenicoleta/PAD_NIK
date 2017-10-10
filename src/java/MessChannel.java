
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class MessChannel {

    private static MessChannel instance = null;
    private static Object mutex = new Object();

    private ArrayList<String> listMessages = new ArrayList<String>();

    public ConcurrentHashMap<String, Topic> topic = new ConcurrentHashMap<String, Topic>();

    private MessChannel() {
    }

    public static MessChannel getInstance() {

        if (instance == null) {
            synchronized (mutex) {
                if (instance == null)
                    instance = new MessChannel();
            }
        }
        return instance;
    }

    public synchronized void addMes(String topName, String mes) {

        if (topic.containsKey(topName)) {
            topic.get(topName).listMessages.add(mes);

        } else {

            Topic t = new Topic();
            t.listMessages.add(mes);

            topic.put(topName, t);
        }
    }

    public synchronized void sendToReceiver(String topName, Socket server) throws IOException {

        DataOutputStream out = new DataOutputStream(server.getOutputStream());

        for (String key : topic.keySet()) {

            if (Pattern.matches(topName, key)) {
                if (topic.get(key).listMessages.isEmpty() == false) {
                    out.writeUTF(topic.get(key).listMessages.poll());
                }
            }
        }
    }
}
