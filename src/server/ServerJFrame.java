package server;

import clientserver.Message;
import java.io.IOException;

public class ServerJFrame extends javax.swing.JFrame implements ServerInterface {

    private ServerThread server;
    private boolean connected = false;

    /** Creates new form ServerJFrame */
    public ServerJFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList = new javax.swing.JList();
        jButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Serwer");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jTextArea.setColumns(20);
        jTextArea.setRows(5);
        jScrollPane1.setViewportView(jTextArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jScrollPane2.setViewportView(jList);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.LINE_END);

        jButton.setText("Connect");
        jButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonActionPerformed(evt);
            }
        });
        getContentPane().add(jButton, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    private void jButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonActionPerformed
        if (!connected) {
            try {
            server = new ServerThread(port, this);
            server.start();
            jTextArea.append("Server online\n");
            jButton.setText("Disconnect");
            connected = true;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        } else {
            server.interrupt();
            server = null;
            jTextArea.append("Server offline\n");
            jButton.setText("Connect");
            connected = false;
        }
    }//GEN-LAST:event_jButtonActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ServerJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton;
    private javax.swing.JList jList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea;
    // End of variables declaration//GEN-END:variables
    private int port = 6666;

    public void printMessage(Message msg) {
        jTextArea.append(msg.getFrom() + " -> " + msg.getTo() + ": " + msg.getMessage() + "\n");
    }
}
