package client;

import common.Packet;
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
 * @author Kamila Turek
 */
public class Client extends Thread {

    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ClientGUI gui;

    /**
     *
     * @param host
     * @param gui
     */
    public Client(String host, int port, ClientGUI gui) {
        try {
            this.gui = gui;
            InetAddress addr = InetAddress.getByName(host);
            socket = new Socket(addr, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void disconnect() {
        try {
            out.close();
            in.close();
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {

        try {
            while (true) {
                Packet pack = (Packet) in.readObject();
                switch (pack.type()) {
                    case JOIN:
                        gui.addUser(pack.from());
                        System.out.println("JOIN");
                        break;
                    case LEAVE:
                        gui.removeUser(pack.to());
                        System.out.println("LEAVE");
                        break;
                    case PRIVATE:
                        gui.showMessage(pack);
                        break;
                    case PUBLIC:
                        gui.showMessage(pack);
                        break;
                    case USERLIST:
                        gui.showUsers(pack.message());
                        break;
                    case DISCONNECT:
                        disconnect();
                        break;
                }
            }
        } catch (EOFException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param pack
     * @throws IOException
     */
    public synchronized void send(Packet pack) throws IOException {
        out.writeObject(pack);
    }
}
