package OutStream;

import Exceptions.StreamException;
import org.apache.log4j.Logger;

/**
 * Created by eugenep on 01.07.14.
 */
public interface OutStream {

    /**
     * Write one symbol in stream
     * @param symbol which will write in stream
     */
    void writeSymbol(int symbol) throws StreamException;

    /**
     * closed stream
     * @throws StreamException if trouble in close stream
     */
    void close()throws StreamException;
}
