package server;

import clientserver.Packet;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bartłomiej Piech
 */
public class ClientThread extends Thread {

    private String nick;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ServerThread server;

    /**
     * 
     * @param socket socket wykorzystywany do komunikacji z klientem
     * @param server
     */
    public ClientThread(Socket socket, ServerThread server) {
        this.socket = socket;
        this.server = server;
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            while (!interrupted()) {
                Packet pack = (Packet) in.readObject();
                System.out.println("odebrano wiadomosc");
                nick = pack.from();
                server.processPacket(pack);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                out.close();
                in.close();
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ClientThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Zwraca nazwę klenta.
     * @return nazwę danego klienta
     */
    public String getNick() {
        return nick;
    }

    /**
     * Wysyła wiadomość do klienta.
     * @param msg wiadomość do wysłania
     */
    public synchronized void send(Packet pack) {
        try {
            out.writeObject(pack);
        } catch (IOException ex) {
        }
    }
}
