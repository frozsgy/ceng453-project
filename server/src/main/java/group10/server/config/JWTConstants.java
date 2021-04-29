package group10.server.config;

public class JWTConstants {
    protected final static int EXPIRES_IN = 60_000; // in ms
    protected final static String SECRET_KEY = "CENG453rules!.";
    protected final static String AUTH_HEADER = "Authorization";
    protected final static String TOKEN_PREFIX = "Bearer ";
    protected final static String LOGIN_PATH = "/api/user/login";
    protected final static String REGISTER_PATH = "/api/user/register";
    protected final static String REQUEST_CODE_PATH = "/api/user/requestPwCode";
    protected final static String UPDATE_PW_PATH = "/api/user/updatePassword";

}
