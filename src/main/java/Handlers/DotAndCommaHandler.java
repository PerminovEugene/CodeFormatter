package Handlers;

import Commands.WriterCommand;
import Context.Context;
import Exceptions.WriterException;
import OutStream.OutStream;
import Rules.CodeRules;
import com.sun.xml.internal.ws.handler.HandlerException;

/**
 * Created by eugenep on 20.12.15.
 */
public class DotAndCommaHandler implements Handler {

    private WriterCommand writerCommand;

    public DotAndCommaHandler(WriterCommand writerCommand){
        this.writerCommand = writerCommand;
    }

    public void handle (Context context, OutStream outStream, CodeRules rules) throws HandlerException {
        try {
            if (context.getIsNewString())  {
                writerCommand.writeIndentsOnNewString(outStream, rules, context);
            }
            writerCommand.writeSymbol(outStream, context.getSymbol());
            writerCommand.writeTransferLine(outStream, rules, context);
            context.setIsNewString( true );
        } catch (WriterException writerException) {
            throw new HandlerException(writerException.Problem());
        }
    }
}
