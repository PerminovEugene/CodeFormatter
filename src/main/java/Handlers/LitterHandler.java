package Handlers;

import Commands.JavaWriterCommand;
import Commands.WriterCommand;
import Context.Context;
import Exceptions.WriterException;
import OutStream.OutStream;
import Rules.CodeRules;
import com.sun.xml.internal.ws.handler.HandlerException;

/**
 * Created by eugenep on 19.12.15.
 */
public class LitterHandler implements Handler {

    private WriterCommand writerCommand;

    public LitterHandler() {
        WriterCommand javaWriterCommand = new JavaWriterCommand();
        this.writerCommand = javaWriterCommand;
    }
    public LitterHandler(WriterCommand writerCommand){
        this.writerCommand = writerCommand;
    }

    public void handle (Context context, OutStream destination, CodeRules rules) throws HandlerException {
        try {
            if (context.getIsNewString() == true) {
                writerCommand.writeIndentsOnNewString(destination, rules, context);
            } else {
                if (context.getPastIsOperand() == true) {
                    writerCommand.writeIndent(destination, rules);
                }
            }
            writerCommand.writeSymbol(destination, context.getSymbol());
            context.setIsNewString(false);
            context.setPastIsOperand(false);
        } catch (WriterException e) {
            throw new HandlerException(e.Problem());
        }
    }
}
