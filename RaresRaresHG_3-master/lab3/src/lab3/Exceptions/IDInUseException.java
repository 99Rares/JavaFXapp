package lab3.Exceptions;

public class IDInUseException extends RuntimeException {
    public IDInUseException(String message) {
        super(message);
    }
}
