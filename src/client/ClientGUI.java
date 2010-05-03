package client;

import clientserver.Message;

/**
 *
 * @author Kamila Turek
 */
public interface ClientGUI {

    /**
     *
     * @param msg
     */
    public void showMessage(Message msg);

    public void showUsers(String users);

    public void addUser(String user);

    public void removeUser(String user);
}
