package Exceptions;

/**
 * Created by eugenep on 07.07.14.
 */
public class ConfigException extends Exception {

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
    public ConfigException(final String message) {
        problem = message;
    }
}
