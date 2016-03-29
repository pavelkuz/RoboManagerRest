package kz.kuzovatov.pavel.utils;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static volatile PropertyManager propertyManagerInstance;
    private static Properties properties;
    private static final Logger log = Logger.getLogger(PropertyManager.class);

    public void configure(String propertyFilePath){
        properties = new Properties();
        synchronized (PropertyManager.class) {
            try {
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propertyFilePath);
                properties.load(inputStream);
                log.info("Properties configured successfully!");
            } catch (IOException e) {
                log.error("Error was occurred while properties was configured: " + e);
            }
        }
    }

    public static PropertyManager getInstance() {
        PropertyManager newInstance = propertyManagerInstance;
        if (newInstance == null) {
            synchronized (PropertyManager.class) {
                newInstance = propertyManagerInstance;
                if (newInstance == null) {
                    propertyManagerInstance = newInstance = new PropertyManager();
                }
            }
        }
        return propertyManagerInstance;
    }

    public String getProperty(String propertyName){
        return properties.getProperty(propertyName);
    }
}
