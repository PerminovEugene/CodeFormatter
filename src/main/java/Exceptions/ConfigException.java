package Exceptions;

/**
 * Created by eugenep on 07.07.14.
 */
public class ConfigException extends Exception {
    public String problem;
    public ConfigException(final String message) {
        problem = message;
    }
}
