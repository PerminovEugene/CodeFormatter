package Guice;
import InStream.InStream;
import OutStream.OutStream;
import com.google.inject.AbstractModule;
import org.apache.log4j.Logger;

/**
 * Created by sevenbits on 03.02.16.
 */
public class GuiceConfigurator extends AbstractModule{
    private static final Logger logger = Logger.getLogger(GuiceConfigurator.class);

    @Override
    protected void configure()
    {
        try {
            Class InStreamInterfaceProviderClass = Class.forName((String) JsonReader.getObjectOnPath("InStreamInterfaceProvider"));

            Class OutStreamInterfaceProviderClass = Class.forName((String) JsonReader.getObjectOnPath("OutStreamInterfaceProvider"));

            bind(InStream.class).toProvider(InStreamInterfaceProviderClass);
            bind(OutStream.class).toProvider(OutStreamInterfaceProviderClass);

        } catch (ClassNotFoundException e) {
            logger.error("Class not found", e);
        } catch (NullPointerException e) {
            logger.error("Null pointer", e);
        }
    }
}
