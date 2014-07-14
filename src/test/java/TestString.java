import InStream.InStream;
import OutStream.OutStream;
import InStream.StringInStream;
import OutStream.StringOutStream;
import junit.framework.TestCase;
import org.junit.Test;
//import org.apache.log4j.Logger;
/**
 *  Tested easy string;
 * Created by eugenep on 04.07.14.
 */
public class TestString extends TestCase {
    private StringOutStream destination;
    private InStream source;

    protected void setUp()throws Exception {
        destination = new StringOutStream();
        source = new StringInStream("{s}");
    }
    @Test
    public void testEmpty()throws Exception {
        CodeFormatter codeFormatter = new CodeFormatter();
        codeFormatter.format(source, destination);
        assertEquals("{\n    s\n}\n", destination.getSting());

    }
    protected void tearDown() throws Exception {
        source.close();
        destination.close();
    }

}

