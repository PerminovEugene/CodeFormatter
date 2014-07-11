import Exceptions.FormatterException;
import junit.framework.TestCase;
import org.junit.Test;
import InStream.StringInStream;
import InStream.InStream;
import OutStream.OutStream;

/**
 * Created by eugenep on 04.07.14.
 */
public class testNullOutStream extends TestCase {
    private OutStream destination;
    private InStream source;

    protected void setUp()throws Exception {
        destination =  null;
        source = new StringInStream("dfs{}dfs");
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
