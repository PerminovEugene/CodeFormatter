
import Exceptions.FormatterException;
import Exceptions.StreamException;
import InStream.InStream;
import InStream.FileInStream;
import OutStream.FileOutStream;
import OutStream.OutStream;
import org.apache.log4j.Logger;
public class Main {

    /**
     *
     * @param args command line parameters, args[0] for Input, args[1] for Output
     */
    public static void main(String[] args) {
	// write your code here
        if  (args.length == 2)
        {
            startFormat(args[0],args[1]);
        }
    }

    public static void startFormat(String source, String dest)
    {
        CodeFormatter codeFormatter = new CodeFormatter();
        try {
            OutStream destinationSymbols = new FileOutStream(dest);
            InStream sourceSymbols = new FileInStream(source);
            codeFormatter.format(sourceSymbols, destinationSymbols);
            System.out.println(sourceSymbols);
        }
        catch (StreamException streamException)
        {
            Logger.getLogger("stream exception in Main, " + streamException.problem + ". ");
        }
        catch (FormatterException formatterException)
        {
            Logger.getLogger("Formatter exception in Main, " + formatterException.problem + ". ");
        }
    }

}
