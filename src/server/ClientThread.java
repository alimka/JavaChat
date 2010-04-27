package server;

import clientserver.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread extends Thread {

    private String nick;
    private Socket s;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ServerThread server;

    public ClientThread(Socket s, ServerThread server) {
        this.s = s;
        this.server = server;
        try {
            in = new ObjectInputStream(s.getInputStream());
            out = new ObjectOutputStream(s.getOutputStream());
        } catch (IOException ex) {
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

    public String getNick() {
        return nick;
    }

    public synchronized void send(Message msg) {
        try {
            out.writeObject(msg);
        } catch (IOException ex) {
        }
    }
}
