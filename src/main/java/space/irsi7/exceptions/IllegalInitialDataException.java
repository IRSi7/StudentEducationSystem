package space.irsi7.exceptions;

public class IllegalInitialDataException extends RuntimeException {
    public IllegalInitialDataException(String message) { super(message); }
    public IllegalInitialDataException(String message, Throwable cause) { super(message, cause); }
}

