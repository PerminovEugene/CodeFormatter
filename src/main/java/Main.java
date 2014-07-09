
import Exceptions.FormatterException;
import Exceptions.StreamException;
import InStream.InStream;
import InStream.FileInStream;
import OutStream.FileOutStream;
import OutStream.OutStream;
import org.apache.log4j.Logger;
public class Main {

    private static Logger logger = Logger.getLogger(Main.class);
    /**
     *
     * @param args command line parameters, args[0]
     *             for Input, args[1] for Output
     */
    public static void main(final String[] args) {
	// write your code here
        if  (args.length == 2)  {
            startFormat(args[0], args[1]);
        }
    }

    public static void startFormat(final String source, final String dest) {
        CodeFormatter codeFormatter = new CodeFormatter();
        try {
            OutStream destinationSymbols = new FileOutStream(dest);
            InStream sourceSymbols = new FileInStream(source);
            codeFormatter.format(sourceSymbols, destinationSymbols);
        } catch (StreamException streamException) {
            logger.error("stream exception in Main, "
                    + streamException.problem + ". ");
        } catch (FormatterException formatterException)  {
            logger.error("Formatter exception in Main, "
                    + formatterException.problem + ". ");
        }
    }

}
