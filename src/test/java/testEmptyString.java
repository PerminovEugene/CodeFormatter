import InStream.InStream;
import OutStream.OutStream;
import OutStream.StringOutStream;
import InStream.StringInStream;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by eugenep on 04.07.14.
 */
public class testEmptyString extends TestCase {

    private OutStream destination;
    private InStream source;

    protected void setUp()throws Exception
    {
        destination =  new StringOutStream("v");
        source = new StringInStream("");
    }

    @Test
    public void testString()throws Exception
    {
        CodeFormatter codeFormatter = new CodeFormatter();
        codeFormatter.format(source,destination);
        assertEquals("", destination);
    }
    protected void tearDown() throws Exception {
        source.close();
        destination.close();
    }


}

