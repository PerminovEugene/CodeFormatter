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

    private void increaseNesting() {
        levelOfNesting++;
    }
    private void reduceNesting()throws FormatterException {
        levelOfNesting--;
        if (levelOfNesting < 0) {
            logger.error("Exception in file.java: } more then {. ");
            throw new FormatterException("} mor then {");
        }
    }

    /**
     * Things for format.
     */
    private boolean isNewString1 = false;
    private int pastSymbol1 = 0;
    private boolean pastIsOperand = false;
    private void writeIndent(final OutStream destination)
            throws FormatterException {
        try {
            int symbol = ' ';
            if (spaceCounter == -1) {
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
            logger.error("stream exception in Formatter when write indent. "
                    + streamException.problem + ". ");
            throw new FormatterException("stream exception write indent");
        }
    }
    private void writeTransferLine(final OutStream destination)
            throws FormatterException {
        try {
            int symbol = '\n';
            destination.writeSymbol(symbol);
        } catch (StreamException streamException) {
            logger.error("streamException in Formatter when go into new string."
                    + streamException.problem + ". ");
            throw new FormatterException("streamException write at new string");
        }
    }
    private void processingOpeningParenthesis(final OutStream destination)
            throws  FormatterException {
        try {
            if (!isNewString1) {
                writeTransferLine(destination);
            }
            isNewString1 = true;
            writeIndent(destination);
            destination.writeSymbol('{');
            increaseNesting();
            writeTransferLine(destination);
        } catch (StreamException streamException) {
            logger.error("Stream exception in out stream when write '{'. ");
            throw new FormatterException("stream exception in out stream");
        } catch (FormatterException formatterException) {
            logger.error("Formatter exception in format when write '{'. "
                    + formatterException.problem);
            throw new FormatterException("streamException at write {");
        }
    }
    private void processingClosingParenthesis(final OutStream destination)
            throws  FormatterException {
        try {
            if (!isNewString1) {
                writeTransferLine(destination);
            }
            reduceNesting();
            isNewString1 = true;
            writeIndent(destination);
            destination.writeSymbol('}');
            writeTransferLine(destination);
        } catch (FormatterException formatterException) {
            logger.error("Formatter exception in format when write '{'. "
                    + formatterException.problem);
            throw new FormatterException("stream exception write"
                    + " at new string");
        } catch (StreamException streamException) {
            logger.error("Stream exception in out stream when write '{'.");
            throw new FormatterException("streamException in"
                    + " out stream");
        }
    }
    private void processingDotAndComma(final OutStream destination)
            throws  FormatterException {
        try {
            if (isNewString1)  {
                writeIndent(destination);
            }
            isNewString1 = true;
            destination.writeSymbol(';');
            writeTransferLine(destination);
        } catch (FormatterException formatterException) {
            logger.error("Formatter exception in format when write ';'. "
                    + formatterException.problem);
            throw new FormatterException("stream exception write at new string");
        } catch (StreamException streamException) {
            logger.error("Stream exception in out stream when write ';'. ");
            throw new FormatterException("stream exception in out stream");
        }
    }
    private void processingSpace(OutStream destination)
            throws  FormatterException {
        try {
            if (!isNewString1 && pastSymbol1 != ' ') {
                destination.writeSymbol(' ');
            }
        } catch (StreamException streamException) {
            logger.error("Stream exception in out stream when write ' '. ");
            throw new FormatterException("stream exception in out stream");
        }
    }
    private void processingComa(final OutStream destination)
            throws  FormatterException {
        try {
            if (isNewString1) {
                writeIndent(destination);
            } if (pastSymbol1 != ' ') {
                destination.writeSymbol(' ');
            } destination.writeSymbol('(');
        } catch (StreamException streamException) {
            logger.error("Stream exception in out stream when write ',. ");
            throw new FormatterException("stream exception in out stream");
        }
    }
    private  void processingOperand(final OutStream destination,int symbol)
            throws FormatterException {
        try {
            if ((pastSymbol1 != ' ') && (pastSymbol1 != '=') && (pastSymbol1 != '-') && (pastSymbol1 != '+') &&
                    (pastSymbol1 != '*') && (pastSymbol1 != '/') && (pastSymbol1 != '%') && (pastSymbol1 != '!')) {
                destination.writeSymbol((int) ' ');
            }
            isNewString1 = false;
            destination.writeSymbol(symbol);
            pastIsOperand = true;
        } catch (StreamException streamException) {
            logger.error("Stream exception in out stream when write operand. ");
            throw new FormatterException("stream exception in out stream");
        }
    }
    private void processingNotSpecialSymbol(final OutStream destination,int symbol)
            throws  FormatterException {
        try {
            if (isNewString1) {
                writeIndent(destination);
                isNewString1 = false;
            } else {
                if (pastIsOperand == true) {
                    destination.writeSymbol(' ');
                    pastIsOperand = false;
                }
            }
            destination.writeSymbol(symbol);
        } catch (FormatterException formatterException) {
            logger.error("Formatter exception in format when write '{'. " + formatterException.problem);
            throw new FormatterException("stream exception write at new string");
        } catch (StreamException streamException) {
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
        public void format(final InStream source,OutStream destination)
                throws FormatterException {
            try {
                CodeConfigurator configurator = new CodeConfigurator();
                spaceCounter = configurator.takeSpaceCount();
            } catch (ConfigException configException) {
               // logger.error("error in CodeConfigurator");
               // throw new FormatterException(configException.problem);
               spaceCounter = 4;
            } catch (StreamException streamException) {
                logger.error(" exception in CodeConfigurator null pointer. ");
                throw new FormatterException(streamException.problem);
            }
            formatStart(source, destination);
        }
        public void formatStart(final InStream source, final OutStream destination)
                throws FormatterException {
        try {
            if (source.isEnd()) {
                logger.error("source stream was empty");
                throw new FormatterException("source stream was empty");
            }
        } catch (StreamException streamException) {
            logger.error("stream exception in Main, " + streamException.problem + ". ");
            throw new FormatterException(streamException.problem);
        } catch (NullPointerException nullPointException) {
            logger.error("Formatter exception in CodeFormatter null pointer. ");
            throw new FormatterException("nullPointerException");
        }
        int symbol = 0;
        levelOfNesting = 0;
        isNewString1 = false;
        pastIsOperand = false;
        try {
            while (!source.isEnd()) {
                pastSymbol1 = symbol;
                symbol = source.readSymbol();
                switch (symbol) {
                    case'{':
                        processingOpeningParenthesis(destination);
                        break;
                    case'}':
                        processingClosingParenthesis(destination);
                        break;
                    case';':
                        processingDotAndComma(destination);
                        break;
                    case ' ':
                        processingSpace(destination);
                        break;
                    case '\n':
                        break;
                    case ',':
                        processingComa(destination);
                        break;
                    case '*':
                    case '/':
                    case '-':
                    case '+':
                    case '=':
                        processingOperand(destination, symbol);
                        break;
                    default:
                        processingNotSpecialSymbol(destination, symbol);
                        break;
                }
            }
        } catch (StreamException streamException) {
            logger.error("stream exception in Formatter, " + streamException.problem + ". ");
            throw new FormatterException( "stream exception: "
                    + streamException.problem);
        } catch (FormatterException formatterException) {
            logger.error("stream exception in Formatter, " + formatterException.problem + ". ");
            throw new FormatterException( "formatter exception: "
                    + formatterException.problem);
        } catch (NullPointerException nullPointerException) {
             logger.error("null pointer exception in Formatter");
             throw new FormatterException("nullPointerException");
        }
    }


}
