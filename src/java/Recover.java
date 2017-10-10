import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Recover extends Thread {

    private Integer time;

    Recover(Integer time) {
        this.time = time;
    }

    static MessChannel messChannel = MessChannel.getInstance();

    public void run() {

        try {
            recoveryMessages();
        } catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }

        while (true) {
            try {
                Thread.sleep(time);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            try {

                saveMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public synchronized void saveMessages() throws IOException {

        FileWriter wriKey = new FileWriter("keys.json");
        for (String key : messChannel.topic.keySet()) {

            wriKey.write(key + "\n");

            FileWriter writer = new FileWriter(key + ".json");
            for (String str : messChannel.topic.get(key).listMessages) {
                writer.write(str + "\n");
            }
            writer.close();
        }
        wriKey.close();
    }

    public synchronized void recoveryMessages() throws FileNotFoundException {

        Scanner sk = new Scanner(new File("keys.json"));
        String key;
        while (sk.hasNextLine()) {

            key = sk.nextLine();
            Scanner s = new Scanner(new File(key + ".json"));

            while (s.hasNextLine()) {

                messChannel.addMes(key, s.nextLine());
            }
            s.close();
        }
        sk.close();
    }


}
