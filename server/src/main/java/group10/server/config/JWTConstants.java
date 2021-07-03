package group10.server.config;

/**
 * JWT Helper class that contains constants that is used by
 * JWT authentication system.
 *
 * @author Alperen Caykus, Mustafa Ozan Alpay
 */
public class JWTConstants {
    /**
     * Constant that shows how long the token will be valid
     */
    protected final static int EXPIRES_IN = 600_000; // in ms
    /**
     * Constant secret key that tokens are generated from.
     */
    protected final static String SECRET_KEY = "CENG453rules!.";
    /**
     * Constant that carries Authorization header text in HTTP request.
     */
    protected final static String AUTH_HEADER = "Authorization";
    /**
     * Constant that is present before the token in the HTTP header.
     */
    protected final static String TOKEN_PREFIX = "Bearer ";
    /**
     * Constant login REST API endpoint that will be exempt from token authentication.
     */
    protected final static String LOGIN_PATH = "/api/user/login";
    /**
     * Constant register REST API endpoint that will be exempt from token authentication.
     */
    protected final static String REGISTER_PATH = "/api/user/register";
    /**
     * Constant request password reset code REST API endpoint that will be exempt from token authentication.
     */
    protected final static String REQUEST_CODE_PATH = "/api/user/requestPwCode";
    /**
     * Constant update password REST API endpoint that will be exempt from token authentication.
     */
    protected final static String UPDATE_PW_PATH = "/api/user/updatePassword";
}
