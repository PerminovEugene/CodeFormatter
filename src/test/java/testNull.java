import junit.framework.TestCase;
import org.junit.Test;
import InStream.FileInStream;
import InStream.InStream;
import OutStream.OutStream;

/**
 * Created by eugenep on 04.07.14.
 */
public class testNull extends TestCase{
        private OutStream destination;
        private InStream source;

        protected void setUp()throws Exception
        {
            destination = null;
            source = new FileInStream("FileWorkerSource.java");
        }
        @Test
        public void testEmpty()throws Exception
        {
            CodeFormatter codeFormatter = new CodeFormatter();
            codeFormatter.format(source,destination);
            assertEquals(null,destination);
        }
        protected void tearDown() throws Exception {
            source.close();
            destination.close();
        }
}
