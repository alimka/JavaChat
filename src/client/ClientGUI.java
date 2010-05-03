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

    public void showUsers(String usersList);

    public void addUser(String userNick);

    public void removeUser(String userNick);
}
