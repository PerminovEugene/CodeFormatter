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
public class SpaceHandler implements Handler{

    private WriterCommand writerCommand;

    public SpaceHandler(WriterCommand writerCommand){
        this.writerCommand = writerCommand;
    }

    public void handle (Context context, OutStream outStream, CodeRules rules) throws HandlerException {
        try {
            if (!context.getIsNewString() && context.getPastSymbol() != ' ') {
                writerCommand.writeSymbol(outStream, context.getSymbol());
            }
        }  catch (WriterException writerException) {
            throw new HandlerException(writerException.Problem());
        }
    }
}
