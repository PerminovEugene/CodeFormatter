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

class CodeFormatter {
    private static final Logger logger = Logger.getLogger(CodeFormatter.class);
    private static final int SPACE_COUNTER = 4;
    //private int levelOfNesting = 0;
    //private int spaceCounter;

    /**
     * Format source stream. Removed extra space, added spaces
     * what's need and format some operators.
     * @param source InStream for format
     * @param destination OutStream for format
     * @throws FormatterException all errors saved
     * in FormatException.problem.
     */

    public final void format(
            final InStream source, final OutStream destination
    ) throws FormatterException {
        int symbol = ' ';
        int spaceCounter;
        try {
            CodeConfigurator configurator = new CodeConfigurator();
            symbol = configurator.getSymbolForNewString();
            spaceCounter = configurator.getSpaceCount();
        } catch (ConfigException configException) {
            // logger.error("error in CodeConfigurator");
            //throw new FormatterException(configException.problem);
            //throw new FormatterException(configException.getMessage());
            spaceCounter = SPACE_COUNTER;
        } catch (StreamException streamException) {
           // logger.error(" exception in CodeConfigurator null pointer. ");
            throw new FormatterException(streamException.Problem());
        }
        formatStart(source, destination, symbol, spaceCounter);
    }

    final void formatStart(
            final InStream source, final OutStream destination,
            final int symbolForNewString, final int spaceCounter
    ) throws FormatterException {
        try {
            if (source.isEnd()) {
                //logger.error("source stream was empty");
                throw new FormatterException("source stream was empty");
            }
        } catch (StreamException streamException) {
            //logger.error("stream exception in Main, "
            //       + streamException.Problem() + ". ");
            throw new FormatterException(streamException);
        } catch (NullPointerException nullPointException) {
            //logger.error("Formatter exception in CodeFormatter null pointer. ");
            throw new FormatterException("nullPointerException");
        }
        int symbol = 0;
        int levelOfNesting = 0;
        boolean  isNewString1 = false;
        boolean pastIsOperand = false;
        int pastSymbol;
        try {
            while (!source.isEnd()) {
                pastSymbol = symbol;
                symbol = source.readSymbol();
                logger.debug("readed " + (char) symbol);
                switch (symbol) {
                    case'{':
                        processingOpeningParenthesis(destination, symbolForNewString,
                                spaceCounter, levelOfNesting, isNewString1);
                        isNewString1 = true;
                        levelOfNesting++;
                        break;
                    case'}':
                        processingClosingParenthesis(destination, symbolForNewString, spaceCounter,
                                levelOfNesting, isNewString1);
                        levelOfNesting--;
                        isNewString1 = true;
                        break;
                    case';':
                        processingDotAndComma(destination, symbolForNewString, spaceCounter,
                                levelOfNesting, isNewString1);
                        isNewString1 = true;
                        break;
                    case ' ':
                        processingSpace(destination, isNewString1, pastSymbol);
                        break;
                    case '\n':
                        break;
                    case ',':
                        processingComa(destination, symbolForNewString,
                                spaceCounter, levelOfNesting, isNewString1, pastSymbol);
                        break;
                    case '*':
                    case '/':
                    case '-':
                    case '+':
                    case '=':
                        processingOperand(destination, symbol, pastSymbol);
                        isNewString1 = false;
                        pastIsOperand = true;
                        break;
                    default:
                        processingNotSpecialSymbol(
                                destination, symbol, symbolForNewString, spaceCounter, levelOfNesting,
                                isNewString1, pastIsOperand);
                        isNewString1 = false;
                        pastIsOperand = false;
                        break;
                }
            }
        } catch (StreamException streamException) {
            //logger.error("stream exception in Formatter, "
            //        + streamException.Problem() + ". ");
            throw new FormatterException(streamException);
        } catch (FormatterException formatterException) {
            //logger.error("stream exception in Formatter, "
            //        + formatterException.Problem() + ". ");
            throw new FormatterException("formatter exception: "
                    + formatterException.Problem());
        } catch (NullPointerException nullPointerException) {
            //logger.error("null pointer exception in Formatter");
            throw new FormatterException("nullPointerException");
        }
    }

    /**
     * Things for format.
     */
   // private boolean isNewString1 = false;
    //private int pastSymbol1 = 0;
    //private boolean pastIsOperand = false;

    private void writeIndent(
            final OutStream destination, final int symbolForNewString,
            final int spaceCounter, final int levelOfNesting
    )
            throws FormatterException {
        try {
            int symbol = ' ';
            if (symbolForNewString == '\t') {
                symbol = '\t';
                for (int i = 0; i < levelOfNesting; i++) {
                    destination.writeSymbol(symbol);
                }
            } else {
                for (int i = 0; i < spaceCounter * levelOfNesting; i++) {
                    destination.writeSymbol(symbol);
                }
            }
        } catch (StreamException streamException) {
            //logger.error("stream exception in Formatter when write indent. "
            //       + streamException.Problem() + ". ");
            throw new FormatterException(streamException);
        }
    }
    private void writeTransferLine(final OutStream destination)
            throws FormatterException {
        try {
            int symbol = '\n';
            destination.writeSymbol(symbol);
        } catch (StreamException streamException) {
            //logger.error("streamException in Formatter when go into new string."
            //        + streamException.Problem() + ". ");
            throw new FormatterException(streamException);
        }
    }
    private void processingOpeningParenthesis(
            final OutStream destination, final int symbolForNewString, final int spaceCounter,
            int levelOfNesting, final boolean isNewString1
    ) throws  FormatterException {
        try {
            if (!isNewString1) {
                writeTransferLine(destination);
            }
            writeIndent(destination, symbolForNewString, spaceCounter, levelOfNesting);
            destination.writeSymbol('{');
           // increaseNesting(levelOfNesting);
            levelOfNesting++;
            logger.error(levelOfNesting);
            writeTransferLine(destination);
        } catch (StreamException streamException) {
            //logger.error("Stream exception in out stream when write '{'. ");
            throw new FormatterException(streamException);
        } catch (FormatterException formatterException) {
           // logger.error("Formatter exception in format when write '{'. "
            //        + formatterException.Problem());
            throw new FormatterException("streamException at write {");
        }
    }
    private void processingClosingParenthesis(
            final OutStream destination, final int symbolForNewString,  final int spaceCounter,
            int levelOfNesting, final boolean isNewString1
    ) throws  FormatterException {
        try {
            if (!isNewString1) {
                writeTransferLine(destination);
            }
            //reduceNesting(levelOfNesting);
            levelOfNesting--;
            if (levelOfNesting < 0) {
                logger.error(levelOfNesting);
                throw new FormatterException("} more then {");
            }
            writeIndent(destination, symbolForNewString, spaceCounter, levelOfNesting);
            destination.writeSymbol('}');
            writeTransferLine(destination);
        } catch (FormatterException formatterException) {
            logger.error("Formatter exception in format when write '{'. "
                    + formatterException.Problem());
            throw new FormatterException("stream exception write"
                    + " at new string");
        } catch (StreamException streamException) {
           // logger.error("Stream exception in out stream when write '{'.");
            throw new FormatterException(streamException);
        }
    }
    private void processingDotAndComma(
            final OutStream destination, final int symbolForNewString, final int spaceCounter,
            final int levelOfNesting, final boolean isNewString1
    )
            throws  FormatterException {
        try {
            if (isNewString1)  {
                writeIndent(destination, symbolForNewString, spaceCounter, levelOfNesting);
            }
            destination.writeSymbol(';');
            writeTransferLine(destination);
        } catch (FormatterException formatterException) {
            logger.error("Formatter exception in format when write ';'. "
                    + formatterException.Problem());
            throw new FormatterException("stream exception:"
                    + " write at new string");
        } catch (StreamException streamException) {
            //logger.error("Stream exception in out stream when write ';'. ");
            throw new FormatterException(streamException);
        }
    }
    private void processingSpace(final OutStream destination, final boolean isNewString1, final int pastSymbol)
            throws  FormatterException {
        try {
            if (!isNewString1 && pastSymbol != ' ') {
                destination.writeSymbol(' ');
            }
        } catch (StreamException streamException) {
            //logger.error("Stream exception in out stream when write ' '. ");
            throw new FormatterException(streamException);
        }
    }
    private void processingComa(
            final OutStream destination, final int symbolForNewString, final int spaceCounter,
            final int levelOfNesting, final boolean isNewString1, final int pastSymbol
    )
            throws  FormatterException {
        try {
            if (isNewString1) {
                writeIndent(destination, symbolForNewString, spaceCounter, levelOfNesting);
            }
            if (pastSymbol != ' ') {
                destination.writeSymbol(' ');
            }
            destination.writeSymbol('(');
        } catch (StreamException streamException) {
            //logger.error("Stream exception in out stream when write ',. ");
            throw new FormatterException(streamException);
        }
    }
    private  void processingOperand(
            final OutStream destination, final int symbol, final int pastSymbol
    ) throws FormatterException {
        try {
            if ((pastSymbol != ' ')
                    && (pastSymbol != '=')
                    && (pastSymbol != '-')
                    && (pastSymbol != '+')
                    && (pastSymbol != '*')
                    && (pastSymbol != '/')
                    && (pastSymbol != '%')
                    && (pastSymbol != '!')) {
                destination.writeSymbol((int) ' ');
            }
            destination.writeSymbol(symbol);
        } catch (StreamException streamException) {
            //logger.error("Stream exception in out stream when write operand. ");
            throw new FormatterException(streamException);
        }
    }
    private void processingNotSpecialSymbol(
            final OutStream destination, final int symbol, final int symbolForNewString, final int spaceCounter,
            final int levelOfNesting, final boolean isNewString1, final boolean pastIsOperand
    )
            throws  FormatterException {
        try {
            if (isNewString1) {
                writeIndent(destination, symbolForNewString, spaceCounter, levelOfNesting);
            } else {
                if (pastIsOperand) {
                    destination.writeSymbol(' ');
                }
            }
            destination.writeSymbol(symbol);
        } catch (FormatterException formatterException) {
            logger.error("Formatter exception in format when write '{'. "
                    + formatterException.Problem());
            throw new FormatterException("stream exception write "
                    + "at new string");
        } catch (StreamException streamException) {
            //logger.error("Stream exception in out stream when write '{'. ");
            throw new FormatterException(streamException);
        }
    }



}
