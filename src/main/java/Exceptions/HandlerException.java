package Exceptions;

/**
 * Created by sevenbits on 01.03.16.
 */
public class HandlerException {

    /**
     *  String for save info about exception.
     */
    private final String problem;

    /**
     *  Get exception info.
     */
    public String Problem() {
        return problem;
    }
    /**
     * Saved parameter in private var "problem.
     * @param message info about exception
     */
    public HandlerException(final String message) {
        problem = message;
    }
    public HandlerException (final StreamException ex)
    {
        problem = ex.Problem();
    }
}
