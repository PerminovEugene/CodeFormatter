package  Exceptions;

/**
 * Created by eugenep on 02.07.14.
 */
public class FormatterException extends Exception {

    /**
     * info about trouble
     */
    public String problem;
    /**
     * saved parameter in private var "problem"
     * @param message info about exception
     */
    public FormatterException(String message)//throwable
    {
        problem = message;
    }
}
