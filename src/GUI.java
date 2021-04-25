import org.json.simple.parser.ParseException;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
    }


    private void initComponents() {

        headerField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        messageField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        topicField = new javax.swing.JTextField();
        subjectField = new javax.swing.JTextField();
        ipField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        outputTextArea = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        byeButton = new javax.swing.JButton();
        timeButton = new javax.swing.JButton();
        listButton = new javax.swing.JButton();
        getField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        timeField = new javax.swing.JTextField();
        connectButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        connectLabel = new javax.swing.JLabel();
        getButton = new javax.swing.JButton();
        headerNoField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        usernameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        headerField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        headerField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headerFieldActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel5.setText("Header");

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton7.setText("");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        addButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        addButton.setText("ADD");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    outputTextArea.setText("Added\n" + peer.clientAddMessage(topicField.getText(),
                            subjectField.getText(), messageField.getText()).toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }
//                addButtonActionPerformed(evt);
            }
        });

        messageField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        messageField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageFieldActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("MESSAGE");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setText("TOPIC");

        topicField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        topicField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topicFieldActionPerformed(evt);
            }
        });

        subjectField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        subjectField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subjectFieldActionPerformed(evt);
            }
        });

        ipField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ipField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipFieldActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("SUBJ");

        outputTextArea.setColumns(20);
        outputTextArea.setRows(5);
        jScrollPane1.setViewportView(outputTextArea);

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("ADD NEW MESSAGE");

        byeButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        byeButton.setText("BYE!");
        byeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                byeButtonActionPerformed(evt);
                try {
                    peer.clientBye();
                    outputTextArea.setText("Connection Terminated");
                    connectLabel.setText("Connection Terminated");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        timeButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timeButton.setText("TIME?");
        timeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                timeButtonActionPerformed(evt);
                try {
                    outputTextArea.setText(peer.clientTime());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        listButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        listButton.setText("LIST?");
        listButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                listButtonActionPerformed(evt);
                String time = timeField.getText();
                timeField.setText("");
                int no = Integer.parseInt(headerNoField.getText());
                headerNoField.setText("");
                ArrayList<String> headers = new ArrayList<String>();
                if(no > 0){
                String[] temp = headerField.getText().split(",");
                headerField.setText("");
                Collections.addAll(headers, temp);
                }
                try {
                    ArrayList<String> res = peer.clientList(time, no, headers);
                    outputTextArea.setText(String.join("\n",res));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        getField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getFieldActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("TIME");

        timeField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        timeField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                timeFieldActionPerformed(evt);
            }
        });

        connectButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Protocol.available(Protocol.port)) {
                    ServerSocket server = null;
                    try {
                        server = new ServerSocket(Protocol.port);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println("Waiting fo peer...");
                    Socket acc_client = null;
                    try {
                        acc_client = server.accept();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Socket client = null;
                    try {
                        client = new Socket(acc_client.getRemoteSocketAddress().toString().split(":")[0].replace("/",
                                ""), Protocol.port + 1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        peer = new Peer(server, client, usernameField.getText(), acc_client, connectLabel);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }else {

                    Socket client = null;
                    try {
                        client = new Socket(ipField.getText(), Protocol.port);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    ServerSocket server = null;
                    try {
                        server = new ServerSocket(Protocol.port + 1);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    Socket acc_client = null;
                    try {
                        acc_client = server.accept();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        peer = new Peer(server, client, usernameField.getText(), acc_client, connectLabel);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                }
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("IP");

        connectLabel.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        connectLabel.setText("Connect to..");

        getButton.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        getButton.setText("GET?");
        getButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    ArrayList<String> res = peer.clientGetMessage(getField.getText());
                    if(res.size() > 1){
                    outputTextArea.setText(res.get(0) + "\n" + res.get(1));
                    }else{
                        outputTextArea.setText(res.get(0));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                getButtonActionPerformed(evt);
            }
        });

        headerNoField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        headerNoField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headerNoFieldActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("h.no");

        usernameField.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usernameField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usernameFieldActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Username");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addComponent(jButton7)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(jLabel4)
                                                                                        .addGap(18, 18, 18)
                                                                                        .addComponent(headerNoField, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(jLabel3)
                                                                                        .addGap(18, 18, 18)
                                                                                        .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                                .addGroup(layout.createSequentialGroup()
                                                                                        .addComponent(jLabel5)
                                                                                        .addGap(18, 18, 18)
                                                                                        .addComponent(headerField, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                        .addComponent(listButton))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                        .addComponent(jLabel6)
                                                                        .addGroup(layout.createSequentialGroup()
                                                                                .addComponent(jLabel8)
                                                                                .addGap(8, 8, 8))
                                                                        .addComponent(jLabel7))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                                .addComponent(subjectField, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(topicField, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(addButton))
                                                                        .addComponent(jLabel9)))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(getButton)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(getField, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(byeButton)
                                                                .addGap(36, 36, 36)
                                                                .addComponent(timeButton))))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(66, 66, 66)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel2)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel1)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(ipField, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(connectButton)
                                                        .addComponent(connectLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(ipField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1)
                                        .addComponent(connectButton))
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel2)
                                        .addComponent(connectLabel))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(getButton)
                                        .addComponent(getField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(byeButton)
                                        .addComponent(timeButton))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(listButton)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel9)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(timeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel3))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(headerNoField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel4))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(headerField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel5)
                                                        .addComponent(jLabel6))
                                                .addGap(18, 18, 18)
                                                .addComponent(jButton7))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(3, 3, 3)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(topicField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel7))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(subjectField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jLabel8))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(addButton)))
                                .addGap(41, 41, 41)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(33, Short.MAX_VALUE))
        );

        pack();
    }

    private void headerFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void messageFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void topicFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void subjectFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void ipFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void byeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void timeButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void listButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void getFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void timeFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void getButtonActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void headerNoFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void usernameFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }


    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                try {
                    init("messages.txt");
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("Welcome to Polite Message");
                try {
                    new Message("bc18ecb5316e029af586fdec9fd533f413b16652bafe079b23e021a6d8ed69aa",
                            "1614686400", "martin.brain@city.ac.uk", "#announcements" ,
                            "Hello!", "2",
                            "Hello everyone!\nThis is the first message sent using PM.").save();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new GUI().setVisible(true);
            }
        });
    }

    public static void init(String filename) throws IOException, ParseException {
        File f = new File(filename);
        if(!f.exists()){
            f.createNewFile();
        }
        Protocol.setFilename(filename);
        Protocol.loadAllMessages();
    }

    // Variables declaration - do not modify
    private javax.swing.JButton addButton;
    private javax.swing.JButton byeButton;
    private javax.swing.JButton connectButton;
    private javax.swing.JLabel connectLabel;
    private javax.swing.JButton getButton;
    private javax.swing.JTextField getField;
    private javax.swing.JTextField headerField;
    private javax.swing.JTextField headerNoField;
    private javax.swing.JTextField ipField;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton listButton;
    private javax.swing.JTextField messageField;
    private javax.swing.JTextArea outputTextArea;
    private javax.swing.JTextField subjectField;
    private javax.swing.JButton timeButton;
    private javax.swing.JTextField timeField;
    private javax.swing.JTextField topicField;
    private javax.swing.JTextField usernameField;
    private Peer peer;
    // End of variables declaration
}
