package group10.client.constants;

public class ServerFolders {
    private final static String USER = "/user/";
    private final static String GAME = "/game/";
    public final static String LOGIN_PATH = USER + "login";
    public final static String REGISTER_PATH = USER + "register";
    public final static String REQUEST_CODE_PATH = USER + "requestPwCode";
    public final static String UPDATE_PW_PATH = USER + "updatePassword";
    public final static String SCOREBOARD_PATH = GAME + "scoreboard";
    public final static String NEW_GAME_PATH = GAME + "new";
    public final static String SEND_SCORE_PATH = GAME + "next";

}
