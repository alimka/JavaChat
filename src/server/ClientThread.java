package server;

import clientserver.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author delor
 */
public class ClientThread extends Thread {

    private String nick;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ServerThread server;

    /**
     * 
     * @param socket
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
        while (true) {
            try {
                Message message = (Message) in.readObject();
                server.processMessage(message);
            } catch (IOException ex) {
            } catch (ClassNotFoundException ex) {
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
    public synchronized void send(Message msg) {
        try {
            out.writeObject(msg);
        } catch (IOException ex) {
        }
    }
}
