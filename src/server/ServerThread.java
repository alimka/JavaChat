package server;

import clientserver.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.util.LinkedList;

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
        while (true) {
            try {
                ClientThread client = new ClientThread(serverSocket.accept(), this);
                clients.add(client);
                client.start();
                System.out.println("Dołączył klient " + client.toString());
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
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
        serverGUI.printMessage(msg);
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
