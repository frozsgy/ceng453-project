package group10.client.constants;

/**
 * Holds server REST endpoints.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class APIConstants {

    /**
     * API Root URL.
     */
    private final static String API = "http://localhost:8083/api";
    /**
     * User folder for server.
     */
    private final static String USER = API + "/user/";
    /**
     * Game folder for server.
     */
    private final static String GAME = API + "/game/";
    /**
     * Login path to send login requests
     */
    public final static String LOGIN_PATH = USER + "login";
    /**
     * Register path to send register requests.
     */
    public final static String REGISTER_PATH = USER + "register";
    /**
     * Request code path to send code request requests.
     */
    public final static String REQUEST_CODE_PATH = USER + "requestPwCode";
    /**
     * Update password path to send password update forms.
     */
    public final static String UPDATE_PW_PATH = USER + "updatePassword";
    /**
     * Scoreboard path to read scoreboard.
     */
    public final static String SCOREBOARD_PATH = GAME + "scoreboard";
    /**
     * New game path to start a new game.
     */
    public final static String NEW_GAME_PATH = GAME + "new";
    /**
     * Send score path to send score of a level.
     */
    public final static String SEND_SCORE_PATH = GAME + "next";

    /**
     * Endpoint to find opponent or get in the queue.
     */
    public final static String FIND_OPPONENT_PATH = GAME + "match";

    /**
     * Endpoint to dequeue from the queue.
     */
    public final static String DEQUEUE_PATH = FIND_OPPONENT_PATH + "/dequeue";

}
