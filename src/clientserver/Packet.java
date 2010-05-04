package clientserver;

import java.io.Serializable;

/**
 *
 * @author delor
 */
public class Packet implements Serializable {

    public static enum Type {

        JOIN, LEAVE, PRIVATE, PUBLIC, DISCONNECT, USERLIST, UNKNOWN
    }
    private String _from = null;
    private String _to = null;
    private String _message = null;

    public Packet() {
    }

    public Packet(String from) {
        this._from = from;
    }

    public Packet(String from, String to) {
        this._from = from;
        this._to = to;
    }

    /**
     * Zwraca nazwę nadawcy wiadomości.
     * @return adresat
     */
    public String from() {
        return _from;
    }

    /**
     * Ustawia nazwę nadawcy wiadomości.
     * @param _from adresat
     */
    public void setFrom(String from) {
        this._from = from;
    }

    /**
     * Zwraca treść wiadomości.
     * @return treść wiadomości
     */
    public String message() {
        return _message;
    }

    /**
     * Ustawia treść wiadomości.
     * @param _message treść wiadomości
     */
    public void setMessage(String message) {
        this._message = message;
    }

    /**
     * Zwraca nazwę adresata wiadomości.
     * @return nazwa adresata wiadomości
     */
    public String to() {
        return _to;
    }

    /**
     * Ustawia nazwę adresata wiadomości.
     * @param _to nazwa adresata wiadomości
     */
    public void setTo(String to) {
        this._to = to;
    }

    @Override
    public String toString() {
        return "From: " + _from + ", To: " + _to + ", Msg: " + _message;
    }

    /**
     * Zwraca typ wiadomości.
     * @return typ wiadomości
     */
    public Type type() {
        if (_from != null && _to == null && _message == null) {
            return Type.JOIN;
        }
        if (_from == null && _to != null && _message == null) {
            return Type.LEAVE;
        }
        if (_from != null && _to == null && _message != null) {
            return Type.PUBLIC;
        }
        if (_from != null && _to != null && _message != null) {
            return Type.PRIVATE;
        }
        if (_from == null && _to == null && _message == null) {
            return Type.DISCONNECT;
        }
        if (_from == null && _to == null && _message != null) {
            return Type.USERLIST;
        }
        return Type.UNKNOWN;
    }
}
