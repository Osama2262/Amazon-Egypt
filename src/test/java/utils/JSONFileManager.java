package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

public class JSONFileManager {
    private static final Logger log = LogManager.getLogger(JSONFileManager.class);
    public LinkedHashMap<String,Object> data;
    public JSONFileManager(String filepath){
        log.info("Loading JSON test data: {}", filepath);
        try {
            Type type = new TypeToken<LinkedHashMap<String,Object>>(){}.getType();
            data = new Gson().fromJson(new FileReader(filepath),type);
            log.info("JSON test data loaded successfully ({} keys)", data == null ? 0 : data.size());
        }
        catch (Exception e) {
            log.error("Failed to load JSON test data: {}", filepath, e);
            throw new IllegalStateException("Failed to load JSON test data: " + filepath, e);
        }
    }
    public Object getValue(String key){
        Object value = data == null ? null : data.get(key);
        if (value == null) { log.warn("JSON test-data key not found or has null value: {}", key); }
        else { log.debug("Retrieved JSON test-data key: {}", key); }
        return value;
    }
}
