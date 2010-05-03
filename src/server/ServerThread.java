package server;

import clientserver.Message;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bartłomiej Piech
 */
public class ServerThread extends Thread {

    private ServerSocket serverSocket;
    private LinkedList<ClientThread> clients = new LinkedList<ClientThread>();
    private final ServerGUI serverGUI;

    ServerThread(int port, ServerGUI serverGUI) throws IOException {
        serverSocket = new ServerSocket(port);
        this.serverGUI = serverGUI;
    }

    @Override
    public void run() {
        while (!interrupted()) {
            try {
                Socket socket = serverSocket.accept();
                ClientThread client = new ClientThread(socket, this);
                clients.add(client);
                client.start();
                System.out.println("Dołączył klient " + client.toString());
                Message msg = new Message();
                msg.setMessage(serverGUI.getUsersList());
                client.send(msg);
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
     * @param msg wiadomość do wysłania
     */
    public synchronized void processMessage(Message msg) {
        serverGUI.processMessage(msg);
        switch (msg.type()) {
            case JOIN:
                sendToAll(msg);
                break;
            case LEAVE:
                sendToAll(msg);
                break;
            case PRIVATE:
                sendToOne(msg);
                break;
            case PUBLIC:
                sendToAll(msg);
                break;
            case UNKNOWN:
                break;
        }
    }

    public void disconnect() {
        //
    }

    /**
     * Wysyła wiadomość do wszystkich klientów.
     * @param msg wiadomość do wysłania
     */
    private void sendToAll(Message msg) {
        for (ClientThread client : clients) {
            client.send(msg);
        }
    }

    /**
     * Wysyła wiadomość do jednego klienta.
     * @param msg wiadomość do wysłania
     */
    private void sendToOne(Message msg) {
        for (ClientThread client : clients) {
            if (client.getNick().equals(msg.getTo()) || client.getNick().equals(msg.getFrom())) {
                client.send(msg);
            }
        }
    }
}
