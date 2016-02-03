import Commands.JavaWriterCommand;
import Commands.WriterCommand;
import Context.Context;
import Exceptions.ConfigException;
import Exceptions.FormatterException;
import Exceptions.StreamException;
import Handlers.*;
import InStream.InStream;
import LogicAutomat.Machine;
import OutStream.OutStream;
import Rules.CodeRules;
import Rules.JavaRules;
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
            spaceCounter = SPACE_COUNTER;
        } catch (StreamException streamException) {
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
        Context context = new Context();
        try {
            Machine handlersIoCMachine = new Machine();
            CodeRules codeRules = new JavaRules();

            while (!source.isEnd()) {
                context.setSymbol(source.readSymbol());
                Handler handler = handlersIoCMachine.getHandlerInstanceSymbol(context);
                handler.handle(context, destination, codeRules);
            }
        } catch (StreamException streamException) {
            throw new FormatterException(streamException);
        } catch (NullPointerException nullPointerException) {
            throw new FormatterException("nullPointerException");
        }
    }
}
