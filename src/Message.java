import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.List;

public class Message {
    String messageId;
    String timeSent;
    String from;
    String topic;
    String subjects;
    String contents;
    List<String> message;

    public Message(String timeSent, String from, String topic, String subjects, List<String> message) {
        this.timeSent = timeSent;
        this.from = from;
        this.topic = topic;
        this.subjects = subjects;
        this.message = message;
        this.contents = String.format("%d", this.message.size());
        this.messageId = Protocol.getSha256(this.getHeaderWithoutId() + this.getMessage());
    }

    public Message(String messageId, String timeSent, String from, String topic, String subjects, String contents, List<String> message) {
        this.timeSent = timeSent;
        this.from = from;
        this.topic = topic;
        this.subjects = subjects;
        this.contents = contents;
        this.message = message;
        this.messageId = messageId;
    }

    public Message(String messageId, String timeSent, String from, String topic, String subjects, String contents, String message) {
        this.timeSent = timeSent;
        this.from = from;
        this.topic = topic;
        this.subjects = subjects;
        this.contents = contents;
        this.message = encodeStringToMessage(message);
        this.messageId = messageId;
    }

    public Message(String from, String topic, String subjects, String message) {
        this.timeSent = Protocol.getEpochTime();
        this.from = from;
        this.topic = topic;
        this.subjects = subjects;
        this.message = encodeStringToMessage(message);
        this.contents = String.format("%d", this.message.size());
        this.messageId = Protocol.getSha256(this.getHeaderWithoutId() + this.getMessage());
    }

    public String decodeMessageToString(List<String> message) {
        String mes = "";
        for (int i = 0; i < message.size(); i++) {
            mes = mes + message.get(i) + "\n";
        }
        return mes;
    }

    public List<String> encodeStringToMessage(String message) {
        return (List<String>) Arrays.asList(message.split("\n"));
    }

    public String getHeader() {
        return "Message-Id : SHA256 " + this.messageId + '\n' +
                "Time-Sent : " + this.timeSent + '\n' +
                "From : " + this.from + '\n' +
                "Topic : " + this.topic + '\n' +
                "Subject :" + this.subjects + '\n' +
                "Contents :" + this.contents + '\n';
    }

    public String getHeaderWithoutId() {
        return "Time-Sent : " + this.timeSent + '\n' +
                "From : " + this.from + '\n' +
                "Topic : " + this.topic + '\n' +
                "Subject :" + this.subjects + '\n' +
                "Contents :" + this.contents + '\n';
    }

    public String getMessage() {
        return decodeMessageToString(this.message);
    }


    public void save() throws Exception {
        for (Message msg : Protocol.allMessages) {
            if (msg.messageId.equals(this.messageId)) {
                return;
            }
        }

        Protocol.writeJson(Protocol.filename, this);
        if (Protocol.allMessages != null) {
            Protocol.loadAllMessages();
        }
    }

    public String toString() {
        return "Message-Id : SHA256 " + this.messageId + '\n' +
                "Time-Sent : " + this.timeSent + '\n' +
                "From : " + this.from + '\n' +
                "Topic : " + this.topic + '\n' +
                "Subject : " + this.subjects + '\n' +
                "Contents : " + this.contents + '\n' + decodeMessageToString(this.message);
    }
}
