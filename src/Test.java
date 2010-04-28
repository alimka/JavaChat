
import client.ClientChatFrame;
import server.ServerJFrame;

public class Test {

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                ServerJFrame server = new ServerJFrame();
                server.setLocation(0, 0);
                server.setVisible(true);
                ClientChatFrame clientA = new ClientChatFrame();
                clientA.setSize(server.getSize());
                clientA.setLocation(0, server.getHeight());
                clientA.setVisible(true);
                ClientChatFrame clientB = new ClientChatFrame();
                clientB.setSize(server.getSize());
                clientB.setLocation(0, (int) clientA.getLocation().getY() + clientA.getHeight());
                clientB.setVisible(true);
            }
        });
    }
}
