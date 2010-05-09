package server;

import common.Packet;
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
                Packet pack = new Packet();
                pack.setMessage(serverGUI.getUsersList());
                client.send(pack);
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
     * @param pack wiadomość do wysłania
     */
    public synchronized void processPacket(Packet pack) {
        serverGUI.processPacket(pack);
        switch (pack.type()) {
            case JOIN:
                sendToAll(pack);
                break;
            case LEAVE:
                sendToAll(pack);
                break;
            case PRIVATE:
                sendToOne(pack);
                break;
            case PUBLIC:
                sendToAll(pack);
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
     * @param pack wiadomość do wysłania
     */
    private void sendToAll(Packet pack) {
        for (ClientThread client : clients) {
            client.send(pack);
        }
    }

    /**
     * Wysyła wiadomość do jednego klienta.
     * @param pack wiadomość do wysłania
     */
    private void sendToOne(Packet pack) {
        for (ClientThread client : clients) {
            if (client.getNick().equals(pack.to()) || client.getNick().equals(pack.from())) {
                client.send(pack);
            }
        }
    }
}
