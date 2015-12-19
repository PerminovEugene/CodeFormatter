package Handlers;

import Context.Context;
import Exceptions.FormatterException;
import Exceptions.StreamException;
import OutStream.OutStream;
import Rules.CodeRules;
import com.sun.xml.internal.ws.handler.HandlerException;

/**
 * Created by eugenep on 19.12.15.
 */
public class LitterHandler implements Handler {

    public void handle (Context context, OutStream destination, CodeRules rules) throws HandlerException {
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
            throw new HandlerException(formatterException);
        } catch (StreamException streamException) {
            throw new HandlerException(streamException);
        }
    }
}
