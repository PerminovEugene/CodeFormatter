import Exceptions.ConfigException;
import Exceptions.StreamException;
import InStream.FileInStream;
import InStream.InStream;
import org.apache.log4j.Logger;

/**
 * Created by eugenep on 07.07.14.
 */
public class CodeConfigurator {

    private static Logger logger = Logger.getLogger(CodeConfigurator.class);
    private InStream inStream;

    /**
     * read symbols out of Formatter_options.java.
     * if first symbol == -1 then use tabulation in format
     * else
     * @throws StreamException
     */
    public CodeConfigurator()throws StreamException, ConfigException
    {
        try {
            inStream = new FileInStream("Formatter_options.java");
        }
        catch (StreamException streamException){
            logger.error("Properties file not found");
            throw new StreamException("properties file not found");
        }
        int symbol = inStream.readSymbol();
        char spaceCounter = (char) symbol;
        symbol = Character.getNumericValue(spaceCounter);
        if (symbol >= -1)
        {
            spaceCount = symbol;
        }
        else
        {
            throw new ConfigException("Don't know this configuration");
        }
    }
    private int spaceCount;

    /**
     * take info about space or tabulation for Formatter
     * @return -1 if use tabulation. other its number of space;
     */
    public int takeSpaceCount()
    {
        return spaceCount;
    }

    public boolean usingTabulation()
    {
        return (spaceCount == -1);
    }

}
