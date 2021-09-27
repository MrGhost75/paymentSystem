package model.exception;

public class DataBaseException extends RuntimeException {

    public DataBaseException() {
    }

    public DataBaseException(String message) {
        super(message);
    }

    public DataBaseException(Exception e) {
        super(e);
    }

    public DataBaseException(String message, Exception e) {
        super(message, e);
    }
}
