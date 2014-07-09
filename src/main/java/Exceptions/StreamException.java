package  Exceptions;

/**
 * Created by eugenep on 02.07.14.
 */
public class StreamException extends Exception {

    /**
     *  Info about trouble.
     */
    public String problem;

    /**
     * saved parameter in private var "problem"
     * @param whatsHappened info about exception
     */
    public StreamException(final String whatsHappened) {
        problem = whatsHappened;
    }

}
