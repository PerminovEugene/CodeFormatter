package OutStream;
import org.apache.log4j.Logger;

/**
 * Created by eugenep on 02.07.14.
 */
public class StringOutStream implements OutStream {
    public StringBuilder destination;
    private int numberCurrentChar;

    /**
     *
     * @param string name of out sting
     */
     public  StringOutStream(String string)
    {
        destination = new StringBuilder();
    }

    /**
     * write symbol in stream
     * @param symbol
     */
    public void writeSymbol(int symbol)
    {
        destination.append((char)symbol);
    }
    public void close()
    {
        destination = null;
    }
}
