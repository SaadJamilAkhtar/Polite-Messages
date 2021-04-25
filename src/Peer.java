import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Peer {

    public Socket client;
    ServerSocket server;
    String username;
    DataInputStream in;
    DataOutputStream out;


    public Peer(ServerSocket server, Socket client, String name, Socket acc_client, JLabel conn) throws IOException {
        System.out.println("Creating Peer");
        this.client = client;
        this.server = server;
        this.username = name;
        Thread serverThread = new ServerThread(server, name, acc_client);
        serverThread.start();

        this.in = new DataInputStream(this.client.getInputStream());
        this.out = new DataOutputStream(this.client.getOutputStream());

        Protocol.sendProtocolVersion(out, Protocol.version, name);
        String[] peer_data = Protocol.receiveProtocolVersion(in);
        System.out.println("*******************");
        System.out.println(peer_data[2] + " connected with protocol version " + peer_data[1]);
        conn.setText("Connected to " + peer_data[2]);
        System.out.println("*******************");
    }

    public Peer(ServerSocket server, Socket client, String name, Socket acc_client) throws IOException {
        System.out.println("Creating Peer");
        this.client = client;
        this.server = server;
        this.username = name;
        Thread serverThread = new ServerThread(server, name, acc_client);
        serverThread.start();

        this.in = new DataInputStream(this.client.getInputStream());
        this.out = new DataOutputStream(this.client.getOutputStream());

        Protocol.sendProtocolVersion(out, Protocol.version, name);
        String[] peer_data = Protocol.receiveProtocolVersion(in);
        System.out.println("*******************");
        System.out.println(peer_data[2] + " connected with protocol version " + peer_data[1]);
        System.out.println("*******************");
    }

    public ArrayList<String> clientList(String time, int no, ArrayList<String> headers) throws IOException {
        Protocol.sendListQuery(time, headers, out);
        ArrayList<String> results = new ArrayList<String>();

        String data = Protocol.recvData(in);
        results.add(data);
        for (int i = 0; i < Integer.parseInt(data.split(" ")[1]); i++) {
            results.add(Protocol.recvData(in));
        }
        System.out.println("______sds____-");
        return results;
    }

    public String clientTime() throws IOException {
        Protocol.askTime(out);
        return Protocol.recvData(in);
    }

    public ArrayList<String> clientGetMessage(String sha) throws IOException {
        // FOUND/ SORRY -- data
        Protocol.sendGet(sha, out);
        String data = Protocol.recvData(in);
        ArrayList<String> results = new ArrayList<String>();
        results.add(data);
        if (data.equals("FOUND")) {
            results.add(Protocol.recvData(in));
        }
        return results;
    }

    public void clientBye() throws IOException {
        Protocol.sendBye(out);
        client.close();
    }

    public Message clientAddMessage(String topic, String subject, String data) throws Exception {
        String[] message = data.split("-");
        String newMesage = String.join("\n", message);
        Message msg = new Message(this.username, topic, subject, newMesage);
        msg.save();
        return msg;
    }

}
