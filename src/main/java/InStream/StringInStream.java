package InStream;

import Exceptions.StreamException;
import org.apache.log4j.Logger;


/**
 * Created by eugenep on 02.07.14.
 */
public class StringInStream implements InStream {
    private static final Logger logger = Logger.getLogger(StringInStream.class);

    private String source;
    private int numberCurrentChar;

    /**
     *  Create input stream.
     * @param string for create stream.
     */
    public StringInStream(final String string) {
        source = string;
        numberCurrentChar = 0;
    }

    /**
     *  Read one symbol from stream.
     * @return symbol which read.
     * @throws StreamException if read from end of string.
     */
    public final int readSymbol() throws StreamException {
        if (numberCurrentChar == source.length()) {
            logger.error("Stream exception at read in FileInStream. ");
            throw new StreamException("Cant read in out of range. ");
        }
        int symbol = source.charAt(numberCurrentChar);
        numberCurrentChar++;
        return symbol;
    }

    /**
     * For take info about end of string.
     * @return true if now end of string, else return false
     */
    public final boolean isEnd() {
        return (numberCurrentChar == source.length());
    }

    /**
     * Closed stream.
     */
    public final void close() {
        source = null;
    }
}
