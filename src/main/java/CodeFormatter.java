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
//        int symbol = 0;
        int pastSymbol;
        Context context = new Context();
        try {
            while (!source.isEnd()) {
//                pastSymbol = symbol;
                context.setSymbol(source.readSymbol());
                switch (context.getSymbol()) {
                    case'{':
                        processingOpeningParenthesis(context, destination, symbolForNewString, spaceCounter);
                        break;
                    case'}':
                        processingClosingParenthesis(context, destination, symbolForNewString, spaceCounter);
                        break;
                    case';':
                        processingDotAndComma(context, destination, symbolForNewString, spaceCounter);
                        break;
                    case ' ':
                        processingSpace(destination, context);
                        break;
                    case '\n':
                        break;
                    case ',':
                        processingComa(context, destination, symbolForNewString, spaceCounter);
                        break;
                    case '*':
                    case '/':
                    case '-':
                    case '+':
                    case '=':
                        processingOperand(destination, context);
                        break;
                    default:
                        processingNotSpecialSymbol(context, destination, symbolForNewString, spaceCounter);
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

    private void writeIndent(
            final OutStream destination, final int symbolForNewString,
            final int spaceCounter, final Context context
    )
            throws FormatterException {
        try {
            int symbol = ' ';
            if (symbolForNewString == '\t') {
                symbol = '\t';
                for (int i = 0; i < context.getLevelOfNesting(); i++) {
                    destination.writeSymbol(symbol);
                }
            } else {
                for (int i = 0; i < spaceCounter * context.getLevelOfNesting(); i++) {
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
            Context context, final OutStream destination, final int symbolForNewString, final int spaceCounter
    ) throws  FormatterException {
        try {
            if (context.getIsNewString() == false) {
                writeTransferLine(destination);
            }
            writeIndent(destination, symbolForNewString, spaceCounter, context);
            destination.writeSymbol('{');
            context.setIsNewString(true);
            context.upLevelOfNesting();
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
            Context context, final OutStream destination, final int symbolForNewString,  final int spaceCounter
    ) throws  FormatterException {
        try {
            if (context.getIsNewString() == false) {
                writeTransferLine(destination);
            }
            context.downLevelOfNesting();
            if (context.getLevelOfNesting() < 0) {
                logger.error(context.getLevelOfNesting() + " this level of nesting! He must be more!");
                throw new FormatterException("} more then {");
            }
            writeIndent(destination, symbolForNewString, spaceCounter, context);
            destination.writeSymbol('}');
            writeTransferLine(destination);
            context.setIsNewString( true );
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
            Context context,final OutStream destination, final int symbolForNewString, final int spaceCounter
    )
            throws  FormatterException {
        try {
            if (context.getIsNewString() == true)  {
                writeIndent(destination, symbolForNewString, spaceCounter, context);
            }
            destination.writeSymbol(';');
            writeTransferLine(destination);
            context.setIsNewString( true );
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
    private void processingSpace(final OutStream destination, Context context)
            throws  FormatterException {
        try {
            if (context.getIsNewString()== false && context.getPastSymbol() != ' ') {
                destination.writeSymbol(' ');
            }
        } catch (StreamException streamException) {
            //logger.error("Stream exception in out stream when write ' '. ");
            throw new FormatterException(streamException);
        }
    }
    private void processingComa(
            Context context,final OutStream destination, final int symbolForNewString, final int spaceCounter
    )
            throws  FormatterException {
        try {
            if (context.getIsNewString() == true) {
                writeIndent(destination, symbolForNewString, spaceCounter, context);
            }
            if (context.getPastSymbol() != ' ') {
                destination.writeSymbol(' ');
            }
            destination.writeSymbol('(');
        } catch (StreamException streamException) {
            //logger.error("Stream exception in out stream when write ',. ");
            throw new FormatterException(streamException);
        }
    }
    private  void processingOperand(
            final OutStream destination, Context context
    ) throws FormatterException {
        try {
            int pastSymbol = context.getPastSymbol();
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
            destination.writeSymbol(context.getSymbol());
            context.setIsNewString( false );
            context.setPastIsOperand( true );
        } catch (StreamException streamException) {
            //logger.error("Stream exception in out stream when write operand. ");
            throw new FormatterException(streamException);
        }
    }
    private void processingNotSpecialSymbol(
            Context context,final OutStream destination, final int symbolForNewString, final int spaceCounter
    )
            throws  FormatterException {
        try {
            if (context.getIsNewString() == true) {
                writeIndent(destination, symbolForNewString, spaceCounter, context);
            } else {
                if (context.getPastIsOperand() == true) {
                    destination.writeSymbol(' ');
                }
            }
            destination.writeSymbol(context.getSymbol());
            context.setIsNewString(false);
            context.setPastIsOperand( false );
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
