package server;

import clientserver.Message;

/**
 *
 * @author Bartłomiej Piech
 */
public interface ServerGUI {

    public void processMessage(Message msg);

    public void printMessage(Message msg);

    public String getUsersList();

    public void addUser(String user);

    public void removeUser(String user);
}
