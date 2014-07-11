package  Exceptions;

/**
 * Created by eugenep on 02.07.14.
 */
public class StreamException extends Exception {

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
     * saved parameter in private var "problem"
     * @param whatsHappened info about exception
     */
    public StreamException(final String whatsHappened) {
        problem = whatsHappened;
    }

}
