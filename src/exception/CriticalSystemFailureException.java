package src.exception;

public class CriticalSystemFailureException extends RuntimeException {

    public CriticalSystemFailureException(String message) {
        super(message);
    }

    public CriticalSystemFailureException(String message, Throwable cause) {
        super(message, cause);
    }

}
