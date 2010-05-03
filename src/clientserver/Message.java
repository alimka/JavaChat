package clientserver;

import java.io.Serializable;

/**
 *
 * @author delor
 */
public class Message implements Serializable {

    public static enum MessageType {

        JOIN, LEAVE, PRIVATE, PUBLIC, DISCONNECT, USERLIST, UNKNOWN
    }
    private String from = null;
    private String to = null;
    private String message = null;

    public Message() {
    }

    public Message(String from) {
        this.from = from;
    }

    public Message(String from, String to) {
        this.from = from;
        this.to = to;
    }

    /**
     * Zwraca nazwę nadawcy wiadomości.
     * @return adresat
     */
    public String getFrom() {
        return from;
    }

    /**
     * Ustawia nazwę nadawcy wiadomości.
     * @param from adresat
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Zwraca treść wiadomości.
     * @return treść wiadomości
     */
    public String getMessage() {
        return message;
    }

    /**
     * Ustawia treść wiadomości.
     * @param message treść wiadomości
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Zwraca nazwę adresata wiadomości.
     * @return nazwa adresata wiadomości
     */
    public String getTo() {
        return to;
    }

    /**
     * Ustawia nazwę adresata wiadomości.
     * @param to nazwa adresata wiadomości
     */
    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "From: " + from + ", To: " + to + ", Msg: " + message;
    }

    /**
     * Zwraca typ wiadomości.
     * @return typ wiadomości
     */
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
        if (from == null && to == null && message == null) {
            return MessageType.DISCONNECT;
        }
        if (from == null && to == null && message != null) {
            return MessageType.USERLIST;
        }
        return MessageType.UNKNOWN;
    }
}
