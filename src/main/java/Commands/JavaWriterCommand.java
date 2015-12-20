package Commands;

import Context.Context;
import Exceptions.FormatterException;
import Exceptions.StreamException;
import Exceptions.WriterException;
import OutStream.OutStream;
import Rules.CodeRules;
import com.sun.xml.internal.ws.handler.HandlerException;

/**
 * Created by eugenep on 20.12.15.
 */
public class JavaWriterCommand implements WriterCommand {
    public void writeSymbol(OutStream outStream,int symbol) throws WriterException{
        try {
            outStream.writeSymbol(symbol);
        } catch (StreamException streamException) {
            throw new WriterException(streamException.Problem());
        }
    }
    public void writeIndentsOnNewString(OutStream outStream, CodeRules codeRules, Context context)
            throws WriterException {
        try {
            for (int i = 0; i < codeRules.getIndentCounter() * context.getLevelOfNesting(); i++) {
                outStream.writeSymbol(codeRules.getIndentSymbol());
            }
        } catch (StreamException streamException) {
            throw new WriterException(streamException.Problem());
        }
    }
    public void writeIndent(OutStream outStream, CodeRules codeRules) throws WriterException{
        try {
            outStream.writeSymbol(codeRules.getIndentSymbol());
        } catch (StreamException streamException) {
            throw new WriterException(streamException.Problem());
        }
    }

    public void writeTransferLine(OutStream outStream, CodeRules codeRules, Context context) throws WriterException{
        try {
            outStream.writeSymbol(codeRules.getTransferLineSymbol());
        } catch (StreamException streamException) {
            throw new WriterException(streamException.Problem());
        }
    }

}
