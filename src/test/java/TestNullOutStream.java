import Exceptions.FormatterException;
import junit.framework.TestCase;
import org.junit.Test;
import InStream.StringInStream;
import InStream.InStream;
import OutStream.OutStream;

/**
 * Created by eugenep on 04.07.14.
 */
public class TestNullOutStream {
    private OutStream destination;
    private InStream source;

    protected void setUp()throws Exception {
        destination =  null;
        source = new StringInStream("dfs{}dfs");
    }

    @Test(expected = FormatterException.class)
    public void testString()throws FormatterException {
            CodeFormatter codeFormatter = new CodeFormatter();
            codeFormatter.format(source, destination);
    }
}
