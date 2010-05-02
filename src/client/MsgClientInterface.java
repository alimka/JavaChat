/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package client;

import clientserver.Message;
import java.util.LinkedList;

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
    //public void showUsers(LinkedList<String> clientNicks);

}
