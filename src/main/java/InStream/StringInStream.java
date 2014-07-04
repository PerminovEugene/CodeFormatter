package InStream;

import Exceptions.StreamException;


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
            throw new StreamException("cant read in out of range");
        }
        int symbol = source.charAt(numberCurrentChar);
        numberCurrentChar++;
        return symbol;
    }

    /**
     * For take info about end of string
     * @return true if now end of string, else return false
     */
    public boolean isEnd()
    {
        return numberCurrentChar == source.length();
    }

    /**
     *
     */
    public void close()
    {
        source = null;
    }
}