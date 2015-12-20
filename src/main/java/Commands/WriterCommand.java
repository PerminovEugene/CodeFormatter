package Commands;

import Context.Context;
import Exceptions.WriterException;
import OutStream.OutStream;
import Rules.CodeRules;

/**
 * Created by eugenep on 20.12.15.
 */
public interface WriterCommand {
    public void writeSymbol(OutStream outStream,int symbol) throws WriterException;
    public void writeIndentsOnNewString(OutStream outStream, CodeRules codeRules, Context context) throws WriterException;
    public void writeIndent(OutStream outStream, CodeRules codeRules) throws WriterException;
    public void writeTransferLine(OutStream outStream, CodeRules codeRules, Context context) throws WriterException;
}
