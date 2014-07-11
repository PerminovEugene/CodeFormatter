package  Exceptions;

/**
 * Created by eugenep on 02.07.14.
 */
public class FormatterException extends Exception {

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
    public FormatterException(final String message) {
        problem = message;
    }
}
