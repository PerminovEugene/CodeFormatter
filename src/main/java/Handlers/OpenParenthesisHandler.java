package Handlers;

import Commands.WriterCommand;
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
public class OpenParenthesisHandler implements Handler {

    private WriterCommand writerCommand;

    public OpenParenthesisHandler(WriterCommand writerCommand){
        this.writerCommand = writerCommand;
    }

    public void handle (Context context, OutStream outStream, CodeRules rules) throws HandlerException {
        try {
            if (!context.getIsNewString()) {
                writerCommand.writeTransferLine(outStream, rules, context);
            }
            writerCommand.writeIndentsOnNewString(outStream, rules, context);
            writerCommand.writeSymbol(outStream, rules.getOpenParenthesisSymbol());
            context.setIsNewString(true);
            context.upLevelOfNesting();
            writerCommand.writeTransferLine(outStream, rules, context);
        } catch (WriterException writerException) {
            throw new HandlerException(writerException.Problem());
        }
    }
}
