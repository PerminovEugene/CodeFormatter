package InStream;

import Exceptions.StreamException;

/**
 * Created by eugenep on 01.07.14.
 */
public interface InStream {

    /**
     *  Read one symbol from stream.
     * @return symbol which read
     * @throws StreamException if stream closed before read
     */
    int readSymbol() throws StreamException;

    /**
     *  Close stream.
     * @throws StreamException stream was closed or not exist
     */
    void close() throws StreamException;

    /**
     *  For take info about end of string.
     * @return true if now end of string, else return false
     * @throws StreamException if error at time when we close file.
     */
    boolean isEnd()throws StreamException;
}
