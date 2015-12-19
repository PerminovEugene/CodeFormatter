package Handlers;

import OutStream.OutStream;
import com.sun.xml.internal.ws.handler.HandlerException;
import Rules.CodeRules;
import Context.Context;

/**
 * Created by eugenep on 19.12.15.
 */
public interface Handler {
    void handle (Context context, OutStream destination, CodeRules rules) throws HandlerException;
}
