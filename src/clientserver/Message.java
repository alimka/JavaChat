package clientserver;

import java.io.Serializable;

/**
 *
 * @author delor
 */
public class Message implements Serializable {

    public static enum MessageType {

        JOIN, LEAVE, PRIVATE, PUBLIC, UNKNOWN
    };
    private String from = null;
    private String to = null;
    private String message = null;

    /**
     *
     * @return
     */
    public String getFrom() {
        return from;
    }

    /**
     *
     * @param from
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     *
     * @return
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public String getTo() {
        return to;
    }

    /**
     *
     * @param to
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     *
     * @param name
     */
    public Message(String name) {
        from = name;
    }

    @Override
    public String toString() {
        return "From: " + from + ", To: " + to + ", Msg: " + message;
    }

    public MessageType type() {
        if (from != null && to == null && message == null) {
            return MessageType.JOIN;
        }
        if (from == null && to != null && message == null) {
            return MessageType.LEAVE;
        }
        if (from != null && to == null && message != null) {
            return MessageType.PUBLIC;
        }
        if (from != null && to != null && message != null) {
            return MessageType.PRIVATE;
        }
        return MessageType.UNKNOWN;
    }
}
