import Exceptions.FormatterException;
import InStream.InStream;
import OutStream.OutStream;
import OutStream.StringOutStream;
import InStream.StringInStream;
import junit.framework.TestCase;
import org.junit.Test;
/**
 *  Tested null in InStream. Must be exception.
 * Created by eugenep on 04.07.14.
 */
public class testNullInStream extends TestCase{

    private OutStream destination;
    private InStream source;

    protected void setUp()throws Exception {
        destination =  new StringOutStream();
        source = null;
    }

    @Test
    public void testString()throws FormatterException {
        try {
            CodeFormatter codeFormatter = new CodeFormatter();
            codeFormatter.format(source, destination);
            fail("must be exception");
        } catch (FormatterException exception) {
            // good
        }
    }

}