package space.irsi7.exceptions;

import java.io.IOException;
import java.io.UncheckedIOException;

//TODO: Наследоваться от Exception
public class IllegalInitialDataException extends RuntimeException {
    public IllegalInitialDataException(String message) { super(message); }
}

