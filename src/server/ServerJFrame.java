package server;

import clientserver.Message;
import java.io.IOException;
import java.util.Enumeration;
import javax.swing.DefaultListModel;

/**
 *
 * @author Bartłomiej Piech
 */
public class ServerJFrame extends javax.swing.JFrame implements ServerGUI {

    /** Creates new form ServerJFrame */
    public ServerJFrame() {
        initComponents();
        jList.setModel(listModel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList = new javax.swing.JList();
        connectButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Serwer");

        jTextArea.setColumns(20);
        jTextArea.setRows(5);
        jScrollPane1.setViewportView(jTextArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

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
                new ServerJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connectButton;
    private javax.swing.JList jList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea;
    // End of variables declaration//GEN-END:variables
    private int port = 6666;
    private ServerThread serverThread;
    private boolean connected = false;
    private DefaultListModel listModel = new DefaultListModel();

    private void connect() {
        try {
            serverThread = new ServerThread(port, this);
            serverThread.start();
            jTextArea.append("Server online\n");
            connectButton.setText("Disconnect");
            connected = true;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void disconnect() {
        serverThread.disconnect();
        serverThread = null;
        jTextArea.append("Server offline\n");
        connectButton.setText("Connect");
        connected = false;
    }

    public void processMessage(Message msg) {
        printMessage(msg);
        switch(msg.type()) {
            case JOIN:
                addUser(msg.getFrom());
                break;
            case LEAVE:
                removeUser(msg.getTo());
                break;
        }
    }

    /**
     * Wyświetla odpowiednio sformatowaną wiadomość w oknie.
     * @param msg wiadomosc do wyświetlenia w oknie
     */
    public void printMessage(Message msg) {
        switch (msg.type()) {
            case JOIN:
                jTextArea.append("Użyszkodnik " + msg.getFrom() + " dołączył do chatu\n");
                break;
            case LEAVE:
                jTextArea.append("Użytkownik " + msg.getTo() + " opuścił chat\n");
                break;
            case PRIVATE:
                jTextArea.append(msg.getFrom() + " -> " + msg.getTo() + ": " + msg.getMessage() + "\n");
                break;
            case PUBLIC:
                jTextArea.append(msg.getFrom() + ": " + msg.getMessage() + "\n");
                break;
            case UNKNOWN:
                jTextArea.append("ERROR Nieznany format wiadomości: " + msg.toString() + "\n");
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
        while (users.hasMoreElements())
            usersList.append(users.nextElement() + ",");
        return usersList.toString();
    }
}
