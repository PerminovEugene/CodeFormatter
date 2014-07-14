import Exceptions.ConfigException;
import Exceptions.StreamException;
import InStream.FileInStream;
import InStream.InStream;
import org.apache.log4j.Logger;

/**
 * Created by eugenep on 07.07.14.
 */
class CodeConfigurator {

    protected static final Logger logger = Logger.getLogger(CodeConfigurator.class);

    private int spaceCount;
    private int symbolForNewString;

    /**
     * read symbols out of Formatter_options.java.
     * if first symbol == -1 then use tabulation in format
     * else
     *
     * @throws StreamException            if was fail at time of working  in stream.
     * @throws Exceptions.ConfigException if error in property file for project.
     */
    public CodeConfigurator() throws StreamException, ConfigException {
        InStream inStream;
        try {
            inStream = new FileInStream("Formatter_options.java");
        } catch (StreamException streamException) {
            logger.error("Properties file not found");
            throw new StreamException("properties file not found");
        }

        int symbol = inStream.readSymbol();
        char spaceCounter = (char) symbol;
        spaceCount = Character.getNumericValue(spaceCounter);
        if (symbol <= -1) {
            throw new ConfigException("Don't know this configuration");
        }

        symbol = inStream.readSymbol();
        spaceCounter = (char) symbol;
        symbolForNewString = Character.getNumericValue(spaceCounter);
    }

    /**
     * take info about space or tabulation for Formatter.
     *
     * @return -1 if use tabulation. other its number of space;
     */
    public final int getSpaceCount() {
        return spaceCount;
    }

    public final int getSymbolForNewString() {
        return symbolForNewString;
    }

}
