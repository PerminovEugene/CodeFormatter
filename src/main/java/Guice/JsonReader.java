package Guice;
import java.io.File;
import java.io.FileReader;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
/**
 * Created by sevenbits on 03.02.16.
 */
public class JsonReader {

    private static final String JSON_FILE_PATH = "/home/sevenbits/Projects/University/CodeFormatter/Configs/ProjectClasses.json";
    private static final Logger logger = Logger.getLogger(JsonReader.class);

    public static Object getObjectOnPath(String keyPath) {

        try {
            String[] keys = keyPath.split("\\.");

            JSONParser parser = new JSONParser();

            FileReader fileReader = new FileReader(JSON_FILE_PATH);
            JSONObject jsonObject = (JSONObject)parser.parse(fileReader);

            for (String key : keys) {
                Object object = (Object)jsonObject.get(key);
                if (object instanceof JSONObject) {
                    jsonObject = (JSONObject)object;
                    continue;
                }
                return object;
            }
            return null;
        } catch (Exception e) {
            logger.error("Exception in json Reader", e);
        }
        return null;
    }
}
