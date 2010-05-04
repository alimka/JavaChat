package client;

import clientserver.Packet;

/**
 *
 * @author Kamila Turek
 */
public interface ClientGUI {

    /**
     *
     * @param pack
     */
    public void showMessage(Packet pack);

    public void showUsers(String users);

    public void addUser(String user);

    public void removeUser(String user);
}
