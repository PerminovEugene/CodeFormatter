package  Exceptions;

/**
 * Created by eugenep on 02.07.14.
 */
public class FormatterException extends Exception {

    /**
     *  Info about trouble.
     */
    public String problem;
    /**
     * Saved parameter in private var "problem.
     * @param message info about exception
     */
    public FormatterException(final String message) {
        problem = message;
    }
}
