package server;

import clientserver.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author delor
 */
public class ServerThread extends Thread {

    private ServerSocket serverSocket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private LinkedList<ClientThread> clients = new LinkedList<ClientThread>();
    private final ServerInterface serverGUI;

    ServerThread(int port, ServerInterface serverGUI) throws IOException {
        serverSocket = new ServerSocket(port);
        this.serverGUI = serverGUI;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                ClientThread client = new ClientThread(serverSocket.accept(), this);
                clients.add(client);
                client.start();
                System.out.println("Dołączył klient " + client.toString());
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }

        for (ClientThread client : clients) {
            client.interrupt();
        }

        try {
            serverSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param msg
     */
    public synchronized void processMessage(Message msg) {
        serverGUI.printMessage(msg);
        if (msg.getTo() == null) {
            if (msg.getMessage() == null) {
                // TODO przemyśleć jak zorganizować zmianę nicka
            } else {
                sendToAll(msg);
            }
        } else {
            sendToOne(msg);
        }
    }

    /**
     *
     */
    public void disconnect() {
    }

    private void sendToAll(Message msg) {
        for (ClientThread client : clients) {
            client.send(msg);
        }
    }

    private void sendToOne(Message msg) {
        for (ClientThread client : clients) {
            if (client.getNick().equals(msg.getTo()) || client.getNick().equals(msg.getFrom())) {
                client.send(msg);
            }
        }
    }
}
