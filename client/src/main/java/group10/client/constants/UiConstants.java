package group10.client.constants;

import group10.client.utility.PropertiesLoader;
import javafx.scene.paint.Paint;

import java.util.Properties;

/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * Holds UI related constants
 */
public class UiConstants {

    /**
     * Folder path that FXML files are stored.
     */
    public static final String FXML_DIR_PATH = "/fxml/";
    /**
     * Path of login.fxml
     */
    public static final String LOGIN_FXML = FXML_DIR_PATH + "login.fxml";
    /**
     * Path of register.fxml
     */
    public static final String REGISTER_FXML = FXML_DIR_PATH + "register.fxml";
    /**
     * Path of home.fxml
     */
    public static final String MENU_FXML = FXML_DIR_PATH + "home.fxml";
    /**
     * Path of scoreboard.fxml
     */
    public static final String SCOREBOARD_FXML = FXML_DIR_PATH + "scoreboard.fxml";
    /**
     * Path of forgot.fxml
     */
    public static final String FORGOT_FXML = FXML_DIR_PATH + "forgot.fxml";
    /**
     * Path of game.fxml
     */
    public static final String GAME_FXML = FXML_DIR_PATH + "game.fxml";
    /**
     * Path of card back face image
     */
    public static final String CARD_BACK_IMAGE = "/static/card_full.png";
    /**
     * Screen Y coordinate of enemy cards.
     */
    public static final double ENEMY_CARD_Y = 0;
    /**
     * Screen X coordinate of the left most card at hand.
     */
    public static final double LEFTMOST_CARD_X = 81;
    /**
     * Screen Y coordinate of player cards at hand.
     */
    public static final double PLAYER_CARD_Y = 86;
    /**
     * Horizontal spacing for card at hands.
     */
    public static final double HORIZONTAL_CARD_SPACING = 174;
    /**
     * White color
     */
    public static final Paint WHITE = Paint.valueOf("WHITE");
    /**
     * Black color
     */
    public static final Paint BLACK = Paint.valueOf("BLACK");

    /**
     * Title of the window
     */
    private String WINDOW_TITLE;

    /**
     * Singleton instance for this class Used to retrieve WINDOW_TITLE.
     */
    private static UiConstants instance;

    /**
     * Creates and gets the singleton instance
     * Loads properties from application.yaml.
     * Then, loads title property to WINDOW_TITLE
     * @return Singleton instance.
     */
    public static UiConstants getInstance() {
        if (instance == null) {
            instance = new UiConstants();
            Properties properties = PropertiesLoader.getProperties();
            instance.WINDOW_TITLE = properties.getProperty("title");
        }
        return instance;
    }

    /**
     * Gets the WINDOW_TITLE
     * @return Title of window.
     */
    public String getWindowTitle() {
        return this.WINDOW_TITLE;
    }
}
