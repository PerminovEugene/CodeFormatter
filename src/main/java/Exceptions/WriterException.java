package Exceptions;

/**
 * Created by eugenep on 20.12.15.
 */
public class WriterException extends Exception{

    private final String problem;

    public String Problem() {
        return problem;
    }
    public WriterException(final String message) {
        problem = message;
    }
}
