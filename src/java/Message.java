public class Message {
    public Message(String type, String command, String message) {
        this.type=type;
        this.command=command;
        this.message=message;
    }

    public Message(String topName,String type, String command, String message) {
        this.topName=topName;
        this.type=type;
        this.command=command;
        this.message=message;
    }
    public Message() {
    }

    public String topName;

    public String type;

    public String command;

    public String message;
}
