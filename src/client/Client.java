/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import clientserver.Message;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alimka
 */
public class Client extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private int port = 6666;
    private MsgClientInterface gui;
    
    private String txt;

    public Client(String host, MsgClientInterface gui) {
        try {
            this.gui = gui;
            InetAddress addr = InetAddress.getByName(null); // byl host
            socket = new Socket(addr, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                Message msg = (Message) in.readObject();
                gui.showMessage(msg);
            } catch (EOFException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void sendMessage(Message msg) throws IOException {
        out.writeObject(msg);
    }
}
