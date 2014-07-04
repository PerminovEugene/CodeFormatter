//import org.apache.log4j.Logger;

import Exceptions.StreamException;
import InStream.InStream;
import InStream.FileInStream;
import OutStream.FileOutStream;
import OutStream.OutStream;

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
        else
        {
            //NullTest startTesting();
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
            System.out.print(streamException.problem);
        }
    }

}
