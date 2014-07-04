package  Exceptions;

/**
 * Created by eugenep on 02.07.14.
 */
public class StreamException extends Exception {

    /**
     * info about trouble
     */
    public String problem;

    /**
     * saved parameter in private var "problem"
     * @param whatsHappened info about exception
     */
    public StreamException(String whatsHappened)
    {
        problem = whatsHappened;
    }

}
