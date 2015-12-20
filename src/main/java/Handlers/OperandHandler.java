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
public class OperandHandler implements Handler {

    private WriterCommand writerCommand;

    public OperandHandler(WriterCommand writerCommand){
        this.writerCommand = writerCommand;
    }

    public void handle (Context context, OutStream outStream, CodeRules rules) throws HandlerException {
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
                writerCommand.writeIndent(outStream,rules);
            }
            writerCommand.writeSymbol(outStream, context.getSymbol());
            context.setIsNewString( false );
            context.setPastIsOperand( true );
        }  catch (WriterException writerException) {
            throw new HandlerException(writerException.Problem());
        }
    }
}
