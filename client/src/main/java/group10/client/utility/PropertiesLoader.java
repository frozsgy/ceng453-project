package group10.client.utility;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * TODO
 * JAVADOC
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Property loader class for UI constants.
 * */
public class PropertiesLoader {

    /**
     * Given a resource file name, loads it properties and returns it.
     * @param resourceFileName Name of the resource file.
     * @return Properties that were loaded from the file.
     * @throws IOException if input stream cannot be loaded to properties.
     */
    private Properties loadProperties(String resourceFileName) throws IOException {

        Properties properties = new Properties();
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(resourceFileName);
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            properties.load(inputStreamReader);
        }
        return properties;

    }


    private static PropertiesLoader instance;
    private static Properties properties;

    public static PropertiesLoader getInstance() {
        if (instance == null) {
            instance = new PropertiesLoader();
        }
        try {
            properties = instance.loadProperties("application.yml");
        } catch (IOException ignore) {
        }
        return instance;
    }

    public static Properties getProperties() {
        if (properties == null) {
            getInstance();
        }
        return properties;
    }
}
