package group10.server.security;

public class JWTConstants {
    protected final static int EXPIRES_IN = 600_000_000; // in ms
    protected final static String SECRET_KEY = "CENG453rules!.";
    protected final static String AUTH_HEADER = "Authorization";
    protected final static String TOKEN_PREFIX = "Bearer ";
    protected final static String DECODE_ON_SIGN = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";
}
