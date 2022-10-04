package uk.co.paidsoftware.connectfour.exceptions;

public class PlayerCantMoveException extends Exception {
    private Exceptions exception;

    public PlayerCantMoveException(Exceptions exception) {
        super();
        this.exception = exception;
    }

    public PlayerCantMoveException(String message, Exceptions exception) {
        super(message);
        this.exception = exception;
    }

    public PlayerCantMoveException(String message, Throwable cause, Exceptions exception) {
        super(message, cause);
        this.exception = exception;
    }

    public Exceptions getException() {
        return exception;
    }
}
