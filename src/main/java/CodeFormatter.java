import Exceptions.ConfigException;
import Exceptions.FormatterException;
import Exceptions.StreamException;
import InStream.InStream;
import OutStream.OutStream;
import org.apache.log4j.Logger;

/**
 * Created by eugenep on 01.07.14.
 * Class for static code format.
 */

public class CodeFormatter {
    private static Logger logger = Logger.getLogger(CodeFormatter.class);

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
            logger.error("Exception in file.java: } more then {. ");
            throw new FormatterException("} mor then {");
        }
    }

    private boolean isNewString1 = false;
    private int pastSymbol1 = 0;
    private void writeIndent(OutStream destination) throws FormatterException
    {
        try {
            int symbol = ' ';
            if (spaceCounter == -1)
            {
                symbol = '\t';
                for (int i = 0; i < levelOfNesting; i++)
                {
                    destination.writeSymbol(symbol);
                }
            }
            else
            {
                for (int i = 0; i < spaceCounter * levelOfNesting; i++)
                {
                    destination.writeSymbol(symbol);
                }
            }
        }
        catch (StreamException streamException)
        {
            logger.error("stream exception in Formatter when write indent. " + streamException.problem + ". ");
            throw new FormatterException("stream exception write indent");
        }
    }
    private void writeTransferLine(OutStream destination) throws FormatterException
    {
        try {
            int symbol = '\n';
            destination.writeSymbol(symbol);
        }
        catch (StreamException streamException)
        {
            logger.error("stream exception in Formatter when go into new string. " + streamException.problem + ". ");
            throw new FormatterException("stream exception write at new string");
        }
    }
    private void processingOpeningParenthesis(OutStream destination)throws  FormatterException
    {
        try {
            if (!isNewString1)
            {
                writeTransferLine(destination);
            }
            isNewString1 = true;
            writeIndent(destination);
            destination.writeSymbol('{');
            increaseNesting();
            writeTransferLine(destination);
        }
        catch (StreamException streamException)
        {
            logger.error("Stream exception in out stream when write '{'. ");
            throw new FormatterException("stream exception in out stream");
        }
        catch (FormatterException formatterException)
        {
            logger.error("Formatter exception in format when write '{'. " + formatterException.problem);
            throw new FormatterException("stream exception write at new string");
        }
    }
    private void processingClosingParenthesis(OutStream destination)throws  FormatterException
    {
        try {
            if (!isNewString1)
            {
                writeTransferLine(destination);
            }
            reduceNesting();
            isNewString1 = true;
            writeIndent(destination);
            destination.writeSymbol('}');
            writeTransferLine(destination);
        }
        catch (FormatterException formatterException)
        {
            logger.error("Formatter exception in format when write '{'. " + formatterException.problem);
            throw new FormatterException("stream exception write at new string");
        }
        catch (StreamException streamException)
        {
            logger.error("Stream exception in out stream when write '{'. ");
            throw new FormatterException("stream exception in out stream");
        }
    }
    private void processingDotAndComma(OutStream destination)throws  FormatterException
    {
        try {
            if (isNewString1)
            {
                writeIndent(destination);
            }
            isNewString1 = true;
            destination.writeSymbol(';');
            writeTransferLine(destination);
        }
        catch (FormatterException formatterException)
        {
            logger.error("Formatter exception in format when write '{'. " + formatterException.problem);
            throw new FormatterException("stream exception write at new string");
        }
        catch (StreamException streamException)
        {
            logger.error("Stream exception in out stream when write '{'. ");
            throw new FormatterException("stream exception in out stream");
        }
    }
    private void processingSpace(OutStream destination)throws  FormatterException
    {
        try {
            if (!isNewString1 && pastSymbol1 != ' ')
            {
                destination.writeSymbol(' ');
            }
        }
        catch (StreamException streamException)
        {
            logger.error("Stream exception in out stream when write '{'. ");
            throw new FormatterException("stream exception in out stream");
        }
    }
    private void processingNotSpecialSymbol(OutStream destination,int symbol)throws  FormatterException
    {
        try {
            if (isNewString1)
            {
                writeIndent(destination);
            }
            isNewString1 = false;
            destination.writeSymbol(symbol);
        }
        catch (FormatterException formatterException)
        {
            logger.error("Formatter exception in format when write '{'. " + formatterException.problem);
            throw new FormatterException("stream exception write at new string");
        }
        catch (StreamException streamException)
        {
            logger.error("Stream exception in out stream when write '{'. ");
            throw new FormatterException("stream exception in out stream");
        }
    }
    /**
     * Format source stream. Removed extra space, added spaces what's need and format some operators.
     * @param source InStream for format
     * @param destination OutStream for format
     * @throws FormatterException all errors saved in FormatException.problem.
     */
        public void format1(InStream source,OutStream destination) throws FormatterException {
        try {
            if (source.isEnd()) {
                logger.error("source stream was empty");
                throw new FormatterException("source stream was empty");
            }
        }
        catch (StreamException streamException)
        {
            logger.error("stream exception in Main, " + streamException.problem + ". ");
            throw new FormatterException(streamException.problem);
        }
        catch (NullPointerException nullPointException)
        {
            logger.error("Formatter exception in CodeFormatter null pointer. ");
            throw new FormatterException("nullPointerException");
        }
        try {
            CodeConfigurator configurator = new CodeConfigurator();
            //if (!configurator.usingTabulation())
            // {
            spaceCounter = configurator.takeSpaceCount();
            char spaceCounter1 = (char) spaceCounter;
            spaceCounter = Character.getNumericValue(spaceCounter1);
            //}
        }
        catch (ConfigException configException) {
            logger.error("error in CodeConfigurator");
            throw new FormatterException(configException.problem);
        }
        catch (StreamException streamException)
        {
            logger.error(" exception in CodeConfigurator null pointer. ");
            throw new FormatterException(streamException.problem);
        }
        int pastSymbol;
        int symbol = 0;
        levelOfNesting = 0;
        isNewString1= false;
        try {
            while (!source.isEnd()) {
                pastSymbol1 = symbol;
                symbol = source.readSymbol();
                switch (symbol) {
                    case'{':
                    {
                        processingOpeningParenthesis(destination);
                        break;
                    }
                    case'}':
                    {
                        processingClosingParenthesis(destination);
                        break;
                    }
                    case';':
                    {
                        processingDotAndComma(destination);
                        break;
                    }
                    case ' ':
                    {
                        processingSpace(destination);
                        break;
                    }
                    case '\n':
                    {
                        break;
                    }
                    default:
                    {
                        processingNotSpecialSymbol(destination,symbol);
                        break;
                    }
                }
            }
        }
        catch (StreamException streamException)
        {
            logger.error("stream exception in Formatter, " + streamException.problem + ". ");
            throw new FormatterException( "stream exception: " + streamException.problem);
        }
        catch (FormatterException formatterException)
        {
            logger.error("stream exception in Formatter, " + formatterException.problem + ". ");
            throw new FormatterException( "formatter exception: " + formatterException.problem);
        }
        catch (NullPointerException nullPointerException)
        {
             logger.error("null pointer exception in Formatter");
             throw new FormatterException("nullPointerException");
        }
    }



    public void format(InStream source,OutStream destination) throws FormatterException {
        try {
            if (source.isEnd()) {
                logger.error("source stream was empty");
                throw new FormatterException("source stream was empty");
            }
        }
        catch (StreamException streamException)
        {
            logger.error("stream exception in Main, " + streamException.problem + ". ");
            throw new FormatterException(streamException.problem);
        }
        catch (NullPointerException nullPointException)
        {
            logger.error("Formatter exception in CodeFormatter null pointer. ");
            throw new FormatterException("nullPointerException");
        }
        try {
            CodeConfigurator configurator = new CodeConfigurator();
            //if (!configurator.usingTabulation())
           // {
            spaceCounter = configurator.takeSpaceCount();
            char spaceCounter1 = (char) spaceCounter;
            spaceCounter = Character.getNumericValue(spaceCounter1);
            //}
        }
        catch (ConfigException configException) {
            logger.error("error in CodeConfigurator");
            throw new FormatterException(configException.problem);
        }
        catch (StreamException streamException)
        {
            logger.error(" exception in CodeConfigurator null pointer. ");
            throw new FormatterException(streamException.problem);
        }
        int pastSymbol;
        int symbol = 0;
        levelOfNesting = 0;
        boolean isNewString = false;
        try{
            while (!source.isEnd()) {
                pastSymbol = symbol;
                symbol = source.readSymbol();
                switch (symbol) {
                    case '{':
                    {
                        if (!isNewString)
                        {
                            goNextString(destination);
                        }
                        isNewString = true;
                        destination.writeSymbol(symbol);
                        increaseNesting();
                        goNextString(destination);
                        break;
                    }
                    case '}':
                    {
                        reduceNesting();
                       // if (!isNewString)
                        //{
                            goNextString(destination);
                       // }
                        isNewString = true;
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
                    case ' ':
                    {
                        if (!isNewString) {
                            if (pastSymbol != ' ') {
                                destination.writeSymbol(symbol);
                            }
                        }
                        break;
                    }
                    case ',':
                    {
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
                       // pastSymbol = symbol;
                        //symbol = ' ';
                        break;
                    }
                    case '\n': {
                        break;
                    }
                    default:
                    {
                        isNewString = false;
                        if ((pastSymbol== '=') || (pastSymbol== '-') || (pastSymbol== '+') ||
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
            logger.error("stream exception in Formatter, " + streamException.problem + ". ");
            throw new FormatterException( "stream exception: " + streamException.problem);
        }
        catch (FormatterException formatterException)
        {
            logger.error("stream exception in Formatter, " + formatterException.problem + ". ");
            throw new FormatterException( "formatter exception: " + formatterException.problem);
        }
        catch (NullPointerException nullPointerException)
        {
            logger.error("null pointer exception in Formatter");
            throw new FormatterException("nullPointerException");
        }
    }

    private void goNextString(OutStream destination)throws FormatterException
    {
        int symbol = '\n';

        try {
            destination.writeSymbol(symbol);
            symbol = ' ';
            if (spaceCounter == -1)
            {
                symbol = '\t';
                for (int i = 0; i < levelOfNesting; i++)
                {
                    destination.writeSymbol(symbol);
                }
            }
            else
            {
                for (int i = 0; i < spaceCounter * levelOfNesting; i++)
                {
                    destination.writeSymbol(symbol);
                }
            }
        }
        catch (StreamException streamException)
        {
            logger.error("stream exception in Formatter when go into new string. " + streamException.problem + ". ");
            throw new FormatterException("stream exception write at new string");
        }
    }

}
