package group10.client.constants;

import group10.client.utility.PropertiesLoader;

import java.util.Properties;


public class UiConstants {

    public static final String LOGIN_FXML = "/fxml/login.fxml";
    public static final String REGISTER_FXML = "/fxml/register.fxml";
    public static final String MENU_FXML = "/fxml/home.fxml";
    public static final String SCOREBOARD_XML = "/fxml/scoreboard.fxml";

    private String WINDOW_TITLE;

    private static UiConstants instance;

    public static UiConstants getInstance() {
        if (instance == null) {
            instance = new UiConstants();
            Properties properties = PropertiesLoader.getProperties();
            instance.WINDOW_TITLE = properties.getProperty("title");
        }
        return instance;
    }

    public String getWindowTitle() {
        return this.WINDOW_TITLE;
    }
}
