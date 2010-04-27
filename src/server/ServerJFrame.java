package server;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerJFrame extends javax.swing.JFrame {

    private ServerThread server;

    /** Creates new form ServerJFrame */
    public ServerJFrame() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        connectMI = new javax.swing.JMenuItem();
        disconnectMI = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.LINE_START);

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        connectMI.setText("Connect");
        connectMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectMIActionPerformed(evt);
            }
        });
        jMenu1.add(connectMI);

        disconnectMI.setText("Disconnect");
        disconnectMI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectMIActionPerformed(evt);
            }
        });
        jMenu1.add(disconnectMI);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectMIActionPerformed
        try {
            server = new ServerThread(port);
            server.start();
        } catch (IOException ex) {
        }
    }//GEN-LAST:event_connectMIActionPerformed

    private void disconnectMIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectMIActionPerformed
        server.disconnect();
    }//GEN-LAST:event_disconnectMIActionPerformed

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new ServerJFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem connectMI;
    private javax.swing.JMenuItem disconnectMI;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
    private int port = 6666;
}
