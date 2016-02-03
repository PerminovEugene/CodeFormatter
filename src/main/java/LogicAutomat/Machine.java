package LogicAutomat;

import Context.Context;
import Guice.JsonReader;
import Handlers.Handler;
import org.apache.log4j.Logger;

/**
 * Created by sevenbits on 03.02.16.
 */
public class Machine {

    private static final Logger logger = Logger.getLogger(JsonReader.class);

    public Handler getHandlerInstanceSymbol(Context context) {
        String handlerPathInConfig = "JavaHandlers." + (char)context.getSymbol() + ".defaultPath";
        String handlerName = (String) JsonReader.getObjectOnPath(handlerPathInConfig);
        if (handlerName == null) { // then other symbols
            handlerPathInConfig = "JavaHandlers." + "default" + ".defaultPath";
            handlerName = (String) JsonReader.getObjectOnPath(handlerPathInConfig);
        }

        try {
            Class handlerClass = Class.forName(handlerName);
            return (Handler)handlerClass.newInstance();
        } catch (ClassNotFoundException e) {
            logger.error("ClassNotFoundException", e);
            return null;
        } catch (InstantiationException e) {
            logger.error("InstantiationException", e);
            return null;
        } catch (IllegalAccessException e) {
            logger.error("IllegalAccessException", e);
            return null;
        }

    }
}
