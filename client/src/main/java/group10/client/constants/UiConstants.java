package group10.client.constants;

import group10.client.utility.PropertiesLoader;
import javafx.scene.paint.Paint;

import java.util.Properties;


public class UiConstants {

    public static final String FXML_DIR_PATH = "/fxml/";
    public static final String LOGIN_FXML = FXML_DIR_PATH + "login.fxml";
    public static final String REGISTER_FXML = FXML_DIR_PATH + "register.fxml";
    public static final String MENU_FXML = FXML_DIR_PATH + "home.fxml";
    public static final String SCOREBOARD_FXML = FXML_DIR_PATH + "scoreboard.fxml";
    public static final String DUMMY_PAGE_FXML = FXML_DIR_PATH + "ui.fxml";
    public static final String FORGOT_FXML = FXML_DIR_PATH + "forgot.fxml";
    public static final String GAME_FXML = FXML_DIR_PATH + "game.fxml";
    public static final String CARD_BACK_IMAGE = "/static/card_full.png";
    public static final double ENEMY_CARD_Y = 0;
    public static final double LEFTMOST_CARD_X = 81;
    public static final double PLAYER_CARD_Y = 86;
    public static final double HORIZONTAL_CARD_SPACING = 174;
    public static final Paint WHITE = Paint.valueOf("WHITE");
    public static final Paint BLACK = Paint.valueOf("BLACK");

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
