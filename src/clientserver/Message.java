/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clientserver;

import java.io.Serializable;

/**
 *
 * @author delor
 */
public class Message implements Serializable {

    private String from = null;
    private String to = null;
    private String message = null;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Message(String name) {
        from = name;
    }


}
