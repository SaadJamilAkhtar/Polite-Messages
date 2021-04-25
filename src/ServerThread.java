import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class ServerThread extends Thread {

    private ServerSocket serverSocket;
    String peer;
    String username;
    Socket client;


    public ServerThread(ServerSocket server, String username, Socket client) throws IOException {
        serverSocket = server;
        this.username = username;
        this.client = client;

    }

    public void run() {
        super.run();
        try {


            DataInputStream in = new DataInputStream(this.client.getInputStream());
            DataOutputStream out = new DataOutputStream(this.client.getOutputStream());

            String[] peer_data = Protocol.receiveProtocolVersion(in);
            Protocol.sendProtocolVersion(out, Protocol.version, username);
            this.peer = peer_data[2];
            System.out.println("*******************");
            System.out.println(this.peer + " connected with protocol version " + peer_data[1]);
            System.out.println("*******************");

            while (true) {
                String full_command = in.readUTF();
                String command = full_command.split(" ")[0];
                System.out.println(full_command);
                System.out.println(command);
                if (command.equals(Protocol.time)) {
                    Protocol.sendTime(out);
                } else if (command.equals(Protocol.get)) {
                    Protocol.sendGetMessage(out, full_command);
                } else if (command.equals(Protocol.list)) {
                    String time = in.readUTF();
                    ArrayList<Message> messages = Protocol.filterByTime(time,
                            Protocol.searchOnAllHeaders(Protocol.recvHeaders(full_command,
                                    in), Protocol.allMessages));
                    System.out.println("++++++++++++" + messages.size() + "=============");
                    Protocol.sendMessageCount(messages, out);
                } else if (command.equals(Protocol.bye)) {
                        serverSocket.close();
                    break;
                } else if (command.equals(Protocol.protocol)) {
                    String[] data = Protocol.receiveProtocolVersion(in);
                    this.peer = data[2];
                } else {
                    System.out.println("Invalid Command");
                }

                System.out.println("CHOOSE AN OPTION:\n\t1.LIST?\n\t2.GET?\n\t3.TIME?\n\t4.BYE!\n\t5.ADD MESSAGE");

            }


        } catch (SocketTimeoutException s) {
            System.out.println("Socket timed out!");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}




