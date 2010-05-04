package server;

import clientserver.Packet;

/**
 *
 * @author Bartłomiej Piech
 */
public interface ServerGUI {

    public void processPacket(Packet pack);

    public void printMessage(Packet pack);

    public String getUsersList();

    public void addUser(String user);

    public void removeUser(String user);
}
