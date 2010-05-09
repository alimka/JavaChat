package server;

import common.Packet;
import java.awt.Color;
import java.io.IOException;
import java.util.Enumeration;
import javax.swing.DefaultListModel;

/**
 *
 * @author Bartłomiej Piech
 */
public class ServerChatFrame extends javax.swing.JFrame implements ServerGUI {

    /** Creates new form ServerChatFrame */
    public ServerChatFrame() {
        initComponents();
        jList.setModel(listModel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jList = new javax.swing.JList();
        connectButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new common.ColorTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Serwer");

        jScrollPane2.setPreferredSize(new java.awt.Dimension(50, 131));

        jScrollPane2.setViewportView(jList);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.EAST);

        connectButton.setText("Connect");
        connectButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectButtonActionPerformed(evt);
            }
        });
        getContentPane().add(connectButton, java.awt.BorderLayout.NORTH);

        jScrollPane1.setMinimumSize(new java.awt.Dimension(300, 22));

        textArea.setEditable(false);
        textArea.setMinimumSize(new java.awt.Dimension(200, 20));
        jScrollPane1.setViewportView(textArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectButtonActionPerformed
        if (!connected) {
            connect();
        } else {
            disconnect();
        }
    }//GEN-LAST:event_connectButtonActionPerformed

    /**
     *
     * @param args
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ServerChatFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connectButton;
    private javax.swing.JList jList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private common.ColorTextPane textArea;
    // End of variables declaration//GEN-END:variables
    private int port = 6666;
    private ServerThread serverThread;
    private boolean connected = false;
    private DefaultListModel listModel = new DefaultListModel();

    private void connect() {
        try {
            serverThread = new ServerThread(port, this);
            serverThread.start();
            textArea.append(Color.blue, "Server online\n");
            connectButton.setText("Disconnect");
            connected = true;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void disconnect() {
        serverThread.disconnect();
        serverThread = null;
        textArea.append(Color.blue, "Server offline\n");
        connectButton.setText("Connect");
        connected = false;
    }

    public void processPacket(Packet pack) {
        printMessage(pack);
        switch (pack.type()) {
            case JOIN:
                addUser(pack.from());
                break;
            case LEAVE:
                removeUser(pack.to());
                break;
        }
    }

    /**
     * Wyświetla odpowiednio sformatowaną wiadomość w oknie.
     * @param pack wiadomosc do wyświetlenia w oknie
     */
    public void printMessage(Packet pack) {
        switch (pack.type()) {
            case JOIN:
                textArea.append(Color.green, "Użyszkodnik " + pack.from() + " dołączył do chatu\n");
                break;
            case LEAVE:
                textArea.append(Color.red, "Użytkownik " + pack.to() + " opuścił chat\n");
                break;
            case PRIVATE:
                textArea.append(Color.black, pack.from() + " -> " + pack.to() + ": " + pack.message() + "\n");
                break;
            case PUBLIC:
                textArea.append(Color.black, pack.from() + ": " + pack.message() + "\n");
                break;
            case UNKNOWN:
                textArea.append(Color.red, "ERROR Nieznany format wiadomości: " + pack.toString() + "\n");
                break;
        }
    }

    /**
     * Dodaje użytkownika z listy.
     * @param user użytkownik do dodania
     */
    public void addUser(String user) {
        listModel.addElement(user);
    }

    /**
     * Usuwa użytkownika z listy.
     * @param user użytkownik do usunięcia
     */
    public void removeUser(String user) {
        for (int i = 0; i < listModel.getSize(); ++i) {
            if (user.equals(listModel.get(i))) {
                listModel.remove(i);
                break;
            }
        }
    }

    public String getUsersList() {
        Enumeration<String> users = (Enumeration<String>) listModel.elements();
        StringBuilder usersList = new StringBuilder();
        while (users.hasMoreElements()) {
            usersList.append(users.nextElement() + ",");
        }
        return usersList.toString();
    }
}
