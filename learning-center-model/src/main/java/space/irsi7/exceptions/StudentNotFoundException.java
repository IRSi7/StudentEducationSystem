package space.irsi7.exceptions;

public class StudentNotFoundException extends Exception {
    public StudentNotFoundException(Exception cause) { super(cause); }
    public StudentNotFoundException(String message, Exception cause) { super(message, cause); }
}
