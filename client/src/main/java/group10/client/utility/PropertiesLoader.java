package group10.client.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Property loader class for UI constants.
 */
public class PropertiesLoader {

    /**
     * Static PropertiesLoader instance.
     */
    private static PropertiesLoader instance;
    /**
     * Static Properties instance that holds properties.
     */
    private static Properties properties;

    /**
     * Loads properties from application.yml file and returns them.
     *
     * @return Properties that were loaded from the file.
     * @throws IOException if input stream cannot be loaded to properties.
     */
    private Properties loadProperties() throws IOException {
        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("application.yml");
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            properties.load(inputStreamReader);
        }
        return properties;
    }

    /**
     * Creates a singleton PropertiesLoader instance if not created before.
     *
     * @return PropertiesLoader instance.
     */
    public static PropertiesLoader getInstance() {
        if (instance == null) {
            instance = new PropertiesLoader();
        }
        try {
            properties = instance.loadProperties();
        } catch (IOException ignore) {
        }
        return instance;
    }

    /**
     * Creates a singleton Properties instance if not created before.
     *
     * @return Properties instance.
     */
    public static Properties getProperties() {
        if (properties == null) {
            getInstance();
        }
        return properties;
    }
}
