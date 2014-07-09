package OutStream;
import org.apache.log4j.Logger;

/**
 * Created by eugenep on 02.07.14.
 */
public class StringOutStream implements OutStream {
    private StringBuilder destination;
    private int numberCurrentChar;

    /**
     *
     * @param string name of out sting
     */
     public  StringOutStream(final String string) {
        destination = new StringBuilder();
    }

    /**
     *  Write symbol in stream.
     * @param symbol what we write in stream
     */
    public final void writeSymbol(final int symbol) {
        destination.append((char) symbol);
    }
    public final void close() {
        destination = null;
    }
}
