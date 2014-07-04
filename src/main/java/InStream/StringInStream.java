package InStream;

import Exceptions.StreamException;
import org.apache.log4j.Logger;


/**
 * Created by eugenep on 02.07.14.
 */
public class StringInStream implements InStream {
    private String source;
    private int numberCurrentChar;

    /**
     * Create input stream
     * @param string
     */
    public StringInStream(String string)
    {
        source = string;
        numberCurrentChar = 0;
    }

    /**
     * Read one symbol from stream
     * @return symbol which read
     * @throws StreamException if read from end of string
     */
    public int readSymbol() throws StreamException
    {
        if (numberCurrentChar == source.length())
        {
            Logger.getLogger("Stream exception at read in FileInStream. ");
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
    public boolean isEnd()
    {
        return (numberCurrentChar == source.length());
    }

    /**
     * Closed stream.
     */
    public void close()
    {
        source = null;
    }
}
