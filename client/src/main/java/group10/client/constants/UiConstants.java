package group10.client.constants;

import group10.client.utility.PropertiesLoader;

import java.util.Properties;


public class UiConstants {

    public static final String FXML_DIR_PATH = "/fxml/";
    public static final String LOGIN_FXML = FXML_DIR_PATH + "login.fxml";
    public static final String REGISTER_FXML = FXML_DIR_PATH + "register.fxml";
    public static final String MENU_FXML = FXML_DIR_PATH + "home.fxml";
    public static final String SCOREBOARD_XML = FXML_DIR_PATH + "scoreboard.fxml";
    public static final String DUMMY_PAGE_XML = FXML_DIR_PATH + "ui.fxml";

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
