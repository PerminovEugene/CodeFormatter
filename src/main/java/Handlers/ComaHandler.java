package Handlers;

import Commands.JavaWriterCommand;
import Commands.WriterCommand;
import Context.Context;
import Exceptions.WriterException;
import OutStream.OutStream;
import Rules.CodeRules;
import com.sun.xml.internal.ws.handler.HandlerException;

/**
 * Created by eugenep on 20.12.15.
 */
public class ComaHandler implements Handler {

    private WriterCommand writerCommand;

    public ComaHandler() {
        WriterCommand javaWriterCommand = new JavaWriterCommand();
        this.writerCommand = javaWriterCommand;
    }
    public ComaHandler(WriterCommand writerCommand){
        this.writerCommand = writerCommand;
    }

    public void handle (Context context, OutStream outStream, CodeRules rules) throws HandlerException {
        try {
            writerCommand.writeSymbol(outStream, context.getSymbol());
            if (context.getPastSymbol() != rules.getNearComaIndent()) {
               writerCommand.writeSymbol(outStream, rules.getNearComaIndent());
            }

        }  catch (WriterException writerException) {
            throw new HandlerException(writerException.Problem());
        }
    }
}
