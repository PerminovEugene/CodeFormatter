package OutStream;

import Exceptions.StreamException;

/**
 * Created by eugenep on 01.07.14.
 */
public interface OutStream {

    /**
     * Write one symbol in stream.
     * @param symbol which will write in stream
     * @throws StreamException if was fail at write in stream.
     */
    void writeSymbol(int symbol) throws StreamException;

    /**
     * Closed stream.
     * @throws StreamException if trouble in close stream
     */
    void close()throws StreamException;
}
