package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigHandler {
    private static final Logger log = LogManager.getLogger(ConfigHandler.class);
    Properties properties;
    public ConfigHandler(String filePath) {
        properties = new Properties();
        log.info("Loading configuration file: {}", filePath);
        try {
            FileInputStream file = new FileInputStream(filePath);
            properties.load(file);
            log.info("Configuration loaded successfully: {}", filePath);
        }catch (Exception e) {
            log.error("Failed to load configuration file: {}", filePath, e);
            throw new IllegalStateException("Failed to load configuration file: " + filePath, e);
        }
    }

    public String getValue(String key)
    {
        String value = properties.getProperty(key);
        if (value == null) {
            log.warn("Configuration key not found: {}", key);
        } else {
            log.debug("Read configuration key: {}", key);
        }
        return value;
    }
}
