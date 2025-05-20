package Exceptions;

public class InvalidRollValueException extends RuntimeException {
    public InvalidRollValueException(String message) {
        super(message);
    }
}
