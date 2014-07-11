import junit.framework.TestSuite;
import org.junit.Test;

/**
 *  Start all tests.
 * Created by eugenep on 04.07.14.
 */
public class testAllTests extends TestSuite {
    @Test
    public void testAllTest() {
        testNullInStream test1 = new testNullInStream();
        testEmptyString test2 = new testEmptyString();
        testNullInStream test3 = new testNullInStream();
        testNullOutStream test4 = new testNullOutStream();
        testString test5 = new testString();
    }


}
