
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Topic {

    public Queue<String> listMessages = new ConcurrentLinkedQueue<String>();

}
