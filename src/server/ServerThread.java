package server;

import clientserver.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.LinkedList;

public class ServerThread extends Thread {

    private ServerSocket s;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private LinkedList<ClientThread> clients;

    ServerThread(int port) throws IOException {
        s = new ServerSocket(port);
    }

    @Override
    public void run() {
        while (true) {
            try {
                ClientThread client = new ClientThread(s.accept(), this);
                clients.add(client);
            } catch (IOException ex) {
            }
        }
    }

    public synchronized void processMessage(Message msg) {
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

    public void disconnect() {
    }

    private void sendToAll(Message msg) {
        for (ClientThread client : clients) {
            client.send(msg);
        }
    }

    private void sendToOne(Message msg) {
        for (ClientThread client : clients) {
            if (client.getNick().equals(msg.getTo())) {
                client.send(msg);
                break;
            }
        }
    }
}
