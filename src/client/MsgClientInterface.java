/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import clientserver.Message;
import java.util.Vector;

/**
 *
 * @author alimka
 */
public interface MsgClientInterface {

    /**
     *
     * @param msg
     */
    public void showMessage(Message msg);
    public void showUsers(Vector<String> clientNicks);
    public void addUser(String userNick);
    public void removeUser(String userNick);

}
