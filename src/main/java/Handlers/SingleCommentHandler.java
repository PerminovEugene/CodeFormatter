package Handlers;

import Commands.JavaWriterCommand;
import Commands.WriterCommand;
import Context.Context;
import Exceptions.WriterException;
import OutStream.OutStream;
import Rules.CodeRules;
import com.sun.xml.internal.ws.handler.HandlerException;

/**
 * Created by sevenbits on 05.06.16.
 */
public class SingleCommentHandler implements Handler {
    private WriterCommand writerCommand;

    public SingleCommentHandler() {
        WriterCommand javaWriterCommand = new JavaWriterCommand();
        this.writerCommand = javaWriterCommand;
    }
    public SingleCommentHandler(WriterCommand writerCommand){
        this.writerCommand = writerCommand;
    }

    public void handle (Context context, OutStream destination, CodeRules rules) throws HandlerException {
        try {
            writerCommand.writeSymbol(destination, context.getSymbol());
            context.setIsNewString(false);
            context.setPastIsOperand(false);
        } catch (WriterException e) {
            throw new HandlerException(e.Problem());
        }
    }
}
