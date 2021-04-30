package group10.server.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
/**
 * @author Alperen Caykus, Mustafa Ozan Alpay
 * JWT Utility class that generates JWT token.
 */
public class JWTUtil {
    /**
     * Static method that returns token string for the given username and userid
     * @param username username of the user that token belongs to.
     * @param userId userid of the user that token belongs to.
     * @return token string
     */
    public static String getJWTToken(String username, long userId) {

        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        Date now = new Date();
        JwtBuilder builder = Jwts
                .builder()
                .setId(Long.toString(userId))
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS512,
                        JWTConstants.SECRET_KEY.getBytes()
                );
        if (JWTConstants.EXPIRES_IN > 0) {
            Date expirationDate = new Date(System.currentTimeMillis() + JWTConstants.EXPIRES_IN);
            builder.setExpiration(expirationDate);
        }
        return builder.compact();
    }
}
