import InStream.InStream;
import OutStream.OutStream;
import InStream.StringInStream;
import OutStream.StringOutStream;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by eugenep on 04.07.14.
 */
public class testString extends TestCase{
    private OutStream destination;
    private InStream source;

    protected void setUp()throws Exception
    {
        destination = new StringOutStream("q");
        source = new StringInStream("{{s}}");
    }
    @Test
    public void testEmpty()throws Exception
    {
        CodeFormatter codeFormatter = new CodeFormatter();
        codeFormatter.format(source,destination);
        OutStream goodDestinationResult = new StringOutStream("{/n    {/n        s/n        }/n    }");
        assertEquals(goodDestinationResult,destination);
    }
    protected void tearDown() throws Exception {
        source.close();
        destination.close();
    }

}

