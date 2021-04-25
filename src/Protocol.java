import com.sun.net.httpserver.Headers;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.UserDataHandler;

import javax.swing.*;
import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class Protocol {
    public static String protocol = "PROTOCOL?";
    public static String time = "TIME?";
    public static String list = "LIST?";
    public static String messages = "MESSAGES";
    public static String get = "GET?";
    public static String bye = "BYE!";

    public static String filename;
    public static String version = "1";
    public static int port = 20111;

    public static ArrayList<Message> allMessages = new ArrayList<Message>();


    public static boolean available(int port) {

        ServerSocket ss = null;
        DatagramSocket ds = null;
        try {
            ss = new ServerSocket(port);
            ss.setReuseAddress(true);
            ds = new DatagramSocket(port);
            ds.setReuseAddress(true);
            return true;
        } catch (IOException e) {
        } finally {
            if (ds != null) {
                ds.close();
            }

            if (ss != null) {
                try {
                    ss.close();
                } catch (IOException e) {
                    /* should not be thrown */
                }
            }
        }

        return false;
    }

    public static String generateProtocolString(String version, String name) {
        return Protocol.protocol + " " + version + " " + name;
    }

    public static String getEpochTime() {
        return String.valueOf(Instant.now().getEpochSecond());
    }

    // protocol related
    public static void sendProtocolVersion(DataOutputStream out, String version, String name) throws IOException {
        out.writeUTF(Protocol.generateProtocolString(version, name));
    }

    public static String[] receiveProtocolVersion(DataInputStream in) throws IOException {
        String[] data = in.readUTF().split(" ");
        return data;
    }

    public static void closeConnection(Socket sock) throws IOException {
        sock.close();
    }

    // time related
    public static void askTime(DataOutputStream out) throws IOException {
        System.out.println("Sending... " + Protocol.time);
        out.writeUTF(Protocol.time);
    }

    public static void sendTime(DataOutputStream out) throws IOException {
        out.writeUTF("NOW " + Protocol.getEpochTime());
    }


    // List related
    public static void sendListQuery(String time, ArrayList<String> headers, DataOutputStream out) throws IOException {
        System.out.println("Sending... " + Protocol.list + " " + time + " " + headers.size());
        out.writeUTF(Protocol.list + " " + time + " " + headers.size());
        out.writeUTF(time);
        sendHeaders(headers, out);
    }

    public static List<String> recvListQuery(String query, DataInputStream in) throws IOException {
        int noOfHeaders = Integer.parseInt(query.split(" ")[2]);
        ArrayList<String> headers = new ArrayList<String>();
        for (int i = 0; i < noOfHeaders; i++) {
            headers.add(in.readUTF());
        }
        return headers;
    }

    public static ArrayList<Message> filterByTime(String time, ArrayList<Message> messages) {
        if (Long.parseLong(time) > Long.parseLong(Protocol.getEpochTime())) {
            return null;
        }
        ArrayList<Message> new_list = new ArrayList<Message>();
        Message msg;
        for (int i = 0; i < messages.size(); i++) {
            msg = messages.get(i);
            if (Long.parseLong(msg.timeSent) < Long.parseLong(time)) {
                new_list.add(msg);
            }
        }
        return new_list;
    }

    public static ArrayList<String> recvHeaders(String command, DataInputStream in) throws IOException {

        int no = Integer.parseInt(command.split(" ")[2]);
        ArrayList<String> headers = new ArrayList<String>();
        for (int i = 0; i < no; i++) {
            headers.add(in.readUTF());
        }
        return headers;
    }

    public static ArrayList<Message> searchOnSingleHeader(String header, ArrayList<Message> messages) {
        String[] data = header.split(" ");
        System.out.println(data[0] + " " + data[1]);
        ArrayList<Message> new_list = new ArrayList<Message>();
        Message msg;
        for (int i = 0; i < messages.size(); i++) {
            msg = messages.get(i);
            if (data[0].equals("Topic:") && data[1].equals(msg.topic)) {
                new_list.add(msg);
            } else if (data[0].equals("Subject:") && data[1].equals(msg.subjects)) {
                new_list.add(msg);
            } else if (data[0].equals("Contents:") && data[1].equals(msg.contents)) {
                new_list.add(msg);
            }
        }

        return new_list;
    }

    public static ArrayList<Message> searchOnAllHeaders(ArrayList<String> headers, ArrayList<Message> messages) {
        ArrayList<Message> new_list = messages;
        for (int i = 0; i < headers.size(); i++) {
            new_list = searchOnSingleHeader(headers.get(i), new_list);
        }
        return new_list;
    }

    public static void sendHeaders(ArrayList<String> headers, DataOutputStream out) throws IOException {
        for (String header : headers) {
            System.out.println(header);
            out.writeUTF(header);
        }
    }

    public static void sendMessageCount(ArrayList<Message> messages, DataOutputStream out) throws IOException {
        System.out.println("Sending... " + Protocol.messages + " " + messages.size());
        if (messages != null) {
            out.writeUTF(Protocol.messages + " " + messages.size());
            for (Message msg : messages) {
                out.writeUTF(msg.messageId);
            }
        }else{
            out.writeUTF(Protocol.messages + " 0");
        }
    }

    public static String recvData(DataInputStream in) throws IOException {
        return in.readUTF();
    }


    public static ArrayList<String> recvMessageCount(String count, DataInputStream in) throws IOException {
        int data = Integer.parseInt(count.split(" ")[1]);
        if (data != 0) {
            ArrayList<String> messageHeaders = new ArrayList<String>();
            for (int i = 0; i < data; i++) {
                messageHeaders.add(in.readUTF());
            }
            return messageHeaders;
        }
        return null;
    }


    // GET related

    public static void sendGet(String sha, DataOutputStream out) throws IOException {
        System.out.println("Sending... " + Protocol.get + " " + sha);
        out.writeUTF(Protocol.get + " " + sha);
    }

    public static Message filterBySha(String sha, ArrayList<Message> messages) {
        String actual_sha = sha.split(" ")[1];
        Message msg;
        for (int i = 0; i < messages.size(); i++) {
            msg = messages.get(i);
            if (msg.messageId.equals(actual_sha)) {
                return msg;
            }
        }
        return null;
    }

    public static Message filterBySha(String sha) {
        return filterBySha(sha, Protocol.allMessages);
    }

    public static void sendGetMessage(DataOutputStream out, String sha) throws IOException {
        Message msg = filterBySha(sha);
        if (msg != null) {
            out.writeUTF("FOUND");
            out.writeUTF(msg.toString());
        } else {
            out.writeUTF("SORRY");
        }
    }

    // Bye related
    public static void sendBye(DataOutputStream out) throws IOException {
        System.out.println("Sending... " + Protocol.bye);
        out.writeUTF(Protocol.bye);
    }

    // JSON related
    public static void writeJson(String filename, Message msg) throws Exception {
        JSONObject sampleObject = new JSONObject();
        sampleObject.put("Message-Id", msg.messageId);
        sampleObject.put("Time-Sent", msg.timeSent);
        sampleObject.put("From", msg.from);
        sampleObject.put("Topic", msg.topic);
        sampleObject.put("Subject", msg.subjects);
        sampleObject.put("Contents", msg.contents);

        JSONArray messages = new JSONArray();
        for (String line : msg.message) {
            messages.add(line);
        }
        sampleObject.put("message", messages);
        Files.write(Paths.get(filename), (sampleObject.toJSONString() + "\n").getBytes(), StandardOpenOption.APPEND);
    }

    public static Object readJsonSigle(String filename) throws Exception {
        FileReader reader = new FileReader(filename);
        JSONParser jsonParser = new JSONParser();
        return jsonParser.parse(reader);
    }

    public static Message JsonToMessage(JSONObject obj) {
        return new Message((String) obj.get("Message-Id"), (String) obj.get("Time-Sent"),
                (String) obj.get("From"), (String) obj.get("Topic"), (String) obj.get("Subject"),
                (String) obj.get("Contents"), (List<String>) obj.get("message"));
    }


    public static synchronized ArrayList<JSONObject> ReadJSON(File MyFile) throws FileNotFoundException, ParseException, ParseException {
        Scanner scn = new Scanner(MyFile, "UTF-8");
        ArrayList<JSONObject> json = new ArrayList<JSONObject>();
        while (scn.hasNext()) {
            JSONObject obj = (JSONObject) new JSONParser().parse(scn.nextLine());
            json.add(obj);
        }
        return json;
    }


    public static String getSha256(String value) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(value.getBytes());
            return bytesToHex(md.digest());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

    public static void loadAllMessages() throws FileNotFoundException, ParseException {
        List<JSONObject> messages = Protocol.ReadJSON(new File(Protocol.filename));
        ArrayList<Message> all_messages = new ArrayList<Message>();
        for (JSONObject obj : messages) {
            all_messages.add(Protocol.JsonToMessage(obj));
        }
        Protocol.allMessages = all_messages;
    }

    public static void setFilename(String filename) {
        Protocol.filename = filename;
    }

}
