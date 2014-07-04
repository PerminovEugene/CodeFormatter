import Exceptions.FormatterException;
import Exceptions.StreamException;
import InStream.InStream;
import OutStream.OutStream;
import org.apache.log4j.Logger;
import sun.awt.Symbol;

/**
 * Created by eugenep on 01.07.14.
 * Class for static code refactor.
 */


public class CodeFormatter {

    private int levelOfNesting = 0;
    private int spaceCounter;

    private void increaseNesting()
    {
        levelOfNesting++;
    }
    private void reduceNesting()throws FormatterException {
        levelOfNesting--;
        if(levelOfNesting < 0)
        {
            throw new FormatterException("} mor then {");
        }
    }

    /**
     * Formatted source stream. Removed extra spaces. Did only one space after ,
     * @param source stream with source symbols
     * @param destination stream for result
     *
     */
    private StringBuilder buffer;
    private void writeInBuffer(int symbol)
    {
        buffer.append((char)symbol);
    }

    /**
     * Siphon from buffer at destination. After that buffer been empty.
     * @param destination stream which we write buffer
     */
    private void throwFromBufferInOutStream(OutStream destination)throws FormatterException
    {
        for (int i = 0; i < buffer.length(); i++)
        {
            int symbol = (int)buffer.charAt(i);
            try {
                destination.writeSymbol((int) symbol);
            }
            catch(StreamException streamException)
            {
                throw new FormatterException("write to streamOut from buffer error");
            }
        }

    }

    /**
     * Format
     * @param source InStream for format
     * @param destination OutStream for format
     * @throws FormatterException all errors saved in FormatException.problem.
     */
    public void format(InStream source,OutStream destination) throws FormatterException {

        try {
            if (source.isEnd()) throw new FormatterException("source stream was empty");
        }
        catch (StreamException streamException)
        {
            Logger.getLogger("stream exception in Main, " + streamException.problem + ". ");
            throw new FormatterException(streamException.problem);
        }
        catch (NullPointerException nullPointException)
        {
            Logger.getLogger("Formatter exception in CodeFormatter null pointer. ");
            throw new FormatterException("nullPointerException");
        }
        spaceCounter = 4;
        buffer = new StringBuilder();
        int pastSymbol;
        int symbol=0;
        boolean isNewString = false;
        try{
            while (!source.isEnd()) {
                pastSymbol = symbol;
                symbol = source.readSymbol();
                switch (symbol) {
                    case '{': {
                        goNextString(destination);
                        isNewString = true;
                        destination.writeSymbol(symbol);
                        increaseNesting();
                        goNextString(destination);
                        break;
                    }
                    case '}': {
                        reduceNesting();
                        isNewString = true;
                        goNextString(destination);
                        destination.writeSymbol(symbol);
                        goNextString(destination);
                        break;
                    }
                    case ';': {
                        isNewString = true;
                        destination.writeSymbol(symbol);
                        goNextString(destination);
                        break;
                    }
                    case ' ': {
                        if (!isNewString) {
                            if (pastSymbol != ' ') {
                                destination.writeSymbol(symbol);
                            }
                        }
                        break;
                    }

                    case ',': {
                            destination.writeSymbol(symbol);
                            destination.writeSymbol(' ');
                            pastSymbol = symbol;
                            symbol = ' ';
                        break;
                    }

                    case '*':
                    case '/':
                    case '-':
                    case '+':
                    case '=':
                    {
                        if ((pastSymbol != ' ') && (pastSymbol!= '=') && (pastSymbol!= '-') && (pastSymbol!= '+') &&
                                (pastSymbol!= '*') && (pastSymbol!= '/'))
                        {
                            destination.writeSymbol((int)' ');
                        }
                        destination.writeSymbol(symbol);
                        //destination.writeSymbol((int)' ');
                        pastSymbol = symbol;
                        //symbol = ' ';
                        break;
                    }

                    case '\n': {
                        break;
                    }

                    /*case '/':
                    {
                        if (pastSymbol == '/')
                        {
                            while (source.isEnd() != false || symbol != '\n') {
                                symbol = source.readSymbol();
                                destination.writeSymbol((int)' ');
                                pastSymbol = symbol;
                            }
                        }
                        else
                        {
                            if ((pastSymbol != ' ') && (pastSymbol!= '=') && (pastSymbol!= '-') && (pastSymbol!= '+') &&
                                    (pastSymbol!= '*') && (pastSymbol!= '/'))
                            {
                                destination.writeSymbol((int)' ');
                            }
                            destination.writeSymbol(symbol);
                            //destination.writeSymbol((int)' ');
                            pastSymbol = symbol;
                            //symbol = ' ';
                            break;
                        }
                    }*/
                    default: {
                        isNewString = false;
                        if ((pastSymbol == ' ') || (pastSymbol== '=') || (pastSymbol== '-') || (pastSymbol== '+') ||
                                (pastSymbol== '*') || (pastSymbol== '/'))
                        {
                            destination.writeSymbol(' ');
                            pastSymbol = ' ';
                        }
                        destination.writeSymbol(symbol);
                        break;
                    }
                }
            }
        }
        catch (StreamException streamException)
        {
            Logger.getLogger("stream exception in Formatter, " + streamException.problem + ". ");
            throw new FormatterException( "stream exception: " + streamException.problem);
        }
        catch (FormatterException formatterException)
        {
            Logger.getLogger("stream exception in Formatter, " + formatterException.problem + ". ");
            throw new FormatterException( "formatter exception: " + formatterException.problem);
        }
        catch (NullPointerException nullPointerException)
        {
            Logger.getLogger("null pointer exception in Formatter");
            throw new FormatterException("nullPointerException");
        }
    }

    private void goNextString(OutStream destination)throws FormatterException
    {
        int symbol = '\n';
        try {
            destination.writeSymbol(symbol);
            symbol = ' ';
            for (int i = 0; i < spaceCounter * levelOfNesting; i++) {
                destination.writeSymbol(symbol);
            }
        }
        catch (StreamException streamException)
        {
            Logger.getLogger("stream exception in Formatter when go into new string. " + streamException.problem + ". ");
            throw new FormatterException("stream exception write at new string");
        }

    }

}
